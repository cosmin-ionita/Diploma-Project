package HDFS_Watcher;

import Enums.IndexJobStatus;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSInotifyEventInputStream;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import org.apache.hadoop.hdfs.inotify.Event;
import org.apache.hadoop.hdfs.inotify.EventBatch;
import org.apache.hadoop.hdfs.inotify.MissingEventsException;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Watcher {

    private final static Object mutex = new Object();
    private final static String pathPrefix = "/flumeData/FlumeData.";

    private static final int hourInMiliseconds = 3600000;
    private static final int minuteInMiliseconds = 60000;

    private static Timer indexJobTimer = new Timer();

    private static List<String> currentlyReceivedFiles = new ArrayList<String>();

    /**
     *  This static initializer will set the default index interval to 1 minute
     *  (by default the system works in batch mode)
     */
    static {
        Status.setIndexInterval("1m");
        updateIndexInterval();
    }

    /**
     *  This method is called by the Main class right after the SpringBoot app is
     *  powered. It spawns a new thread that monitors all files that reach a specific
     *  HDFS directory.
     */
    public static void launchWatcher() {
        hadoopFilesMonitor();
    }

    /**
     *  This method will launch the script that powers on the index job
     *
     * @return - true if the job was lunched successfully, false otherwise
     */
    private static boolean launchIndexJob() {

        boolean result = false;

        synchronized (mutex) {
            try {
                result = IndexLauncher.launchIndexJob(currentlyReceivedFiles);
                currentlyReceivedFiles.clear();

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return result;
    }

    /**
     * Wrapper for the launchIndexJob method that is considered to be internal to this class
     *
     * @return returns the result of the launchIndexJob() call
     */
    public static boolean triggerIndexJob() {
        return launchIndexJob();
    }

    /**
     *  Utility method that converts a string representation of an interval to a value in miliseconds
     *
     * @param interval - the indexing interval. Possible values: 1m, 10m, 7h, 10h, (X[m | h]) etc.
     * @return - returns the value of the interval in miliseconds
     */
    private static int getIntervalInMiliseconds(String interval) {

        int result = 0;
        char token = interval.charAt(interval.length() - 1);

        if(token == 'h') {
            interval = interval.substring(0, interval.length() - 1);

            result = Integer.parseInt(interval) * hourInMiliseconds;

        } else if(token == 'm') {
            interval = interval.substring(0, interval.length() - 1);

            result = Integer.parseInt(interval) * minuteInMiliseconds;
        }

        return result;
    }

    /**
     *  This method will update the interval of the scheduler that launches the index job periodically
     */
    public static boolean updateIndexInterval() {
        final String interval = Status.getIndexInterval();

        int intervalInMiliseconds = getIntervalInMiliseconds(interval);

        System.out.println("Attempting to set the indexing interval to: " + intervalInMiliseconds + " miliseconds ");

        indexJobTimer.cancel();
        indexJobTimer.purge();

        indexJobTimer = new Timer();

        indexJobTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                System.out.println("Attempting to launch a new index job...");

                /* If there is no index job running, we launch it */
                if(Status.getIndexJobStatus().equals(IndexJobStatus.FINISHED)) {
                    boolean launchingStatus = launchIndexJob();

                    if(launchingStatus)
                        Status.setIndexJobStatus(IndexJobStatus.RUNNING);
                }
            }
        }, intervalInMiliseconds, intervalInMiliseconds);

        return true;
    }

    /**
     *  This method will monitor the state of the bash process that is associated to the index job.
     *  When the process has exited with the code 0, then the job was completed successfully
     *
     * @param process - the process that is associated to the bash script that launches the job
     */
    public static void watchIndexJob(Process process){

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    process.waitFor();
                }catch (InterruptedException exception) {
                    exception.printStackTrace();
                }

                System.out.println("Exit value = " + process.exitValue());

                if(process.exitValue() == 0) {
                    Status.setIndexJobStatus(IndexJobStatus.FINISHED);
                    System.out.println("Setting the local timestamp");
                    Status.setLastIndexTimestamp(LocalDateTime.now().toString());
                }
            }
        });

        t.start();
    }

    /**
     * This method powers a thread that monitors all files that reach a particular HDFS directory
     * (/flumeData) and accumulates the full qualified path of those files (the HDFS URI) in a list in a
     * thread safe way.
     */
    private static void hadoopFilesMonitor() {

        final String uri = "hdfs://va6-ioni-qe-1-ksmaster-1";

        Thread t = new Thread(() -> {

            try {
                System.out.println("Starting HDFS Watcher with URI = " + uri);

                HdfsAdmin admin = new HdfsAdmin( URI.create( uri ), new Configuration() );

                DFSInotifyEventInputStream eventStream = admin.getInotifyEventStream();

                System.out.println("Starting watching the hadoop files...");

                while(true) {

                    EventBatch events = eventStream.take();

                    for( Event event : events.getEvents() ) {

                        switch( event.getEventType() ) {

                            case RENAME:
                                Event.RenameEvent renameEvent = (Event.RenameEvent) event;

                                String fileName = uri + renameEvent.getDstPath();

                                if(renameEvent.getDstPath().startsWith(pathPrefix)) {
                                    synchronized (mutex) {
                                        currentlyReceivedFiles.add(fileName);
                                    }
                                }

                                break;

                            default:
                                break;
                        }
                    }
                }
            } catch (IOException | MissingEventsException | InterruptedException exception) {
                exception.printStackTrace();
                System.out.println("Retrying to power up the thread...");
                hadoopFilesMonitor();
            }
        });

        t.start();
    }
}
