package HDFS_Watch;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSInotifyEventInputStream;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import org.apache.hadoop.hdfs.inotify.Event;
import org.apache.hadoop.hdfs.inotify.MissingEventsException;
import org.apache.hadoop.hdfs.inotify.EventBatch;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HDFSWatcher {

    private static final int indexFilesCount = 100;

    public static void main( String[] args ) throws IOException, InterruptedException, MissingEventsException
    {
        System.out.println("Running hdfs watcher...");

        List<String> currentlyReceivedFiles = new ArrayList<String>();

        HdfsAdmin admin = new HdfsAdmin( URI.create( args[0] ), new Configuration() );

        DFSInotifyEventInputStream eventStream = admin.getInotifyEventStream();

        while( true ) {

            EventBatch events = eventStream.take();

            for( Event event : events.getEvents() ) {

                switch( event.getEventType() ) {

                    case RENAME:
                        Event.RenameEvent renameEvent = (Event.RenameEvent) event;
                        currentlyReceivedFiles.add(args[0] + renameEvent.getDstPath());

                        if(currentlyReceivedFiles.size() == indexFilesCount)
                            IndexLauncher.launchIndexJob(currentlyReceivedFiles);

                        break;

                    default:
                        break;
                }
            }
        }
    }
}
