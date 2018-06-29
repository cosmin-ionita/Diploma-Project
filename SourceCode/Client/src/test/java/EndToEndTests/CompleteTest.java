package EndToEndTests;

import Commands.IndexNowCommand;
import Commands.StatusCommand;
import Enums.IndexJobStatus;
import Utils.Utils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import Utils.*;

public class CompleteTest {

    private String getJobStatus() {
        StatusCommand statusCommand = new StatusCommand();

        /* Redirect stdout to catch the status here */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;

        System.setOut(ps);

        statusCommand.execute();

        /* Redirect stdout back to System.out */
        System.out.flush();
        System.setOut(old);

        String status = baos.toString();

        return status.split(":")[5].split(" ")[1];
    }

    @Test
    public void executeTest() {
        IndexNowCommand indexNowcommand = new IndexNowCommand();

        if(getJobStatus().equals(IndexJobStatus.FINISHED.toString())) {
            Utils.uploadTestArchive();

            Thread indexNowThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    indexNowcommand.execute();  /* This blocks until the command is executed */
                }
            });

            Utils.wait(10000);   /* Wait for the parser */

            indexNowThread.start();

            Thread statusMonitorThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    /* This blocks until the job has started successfully */
                    while(!getJobStatus().equals(IndexJobStatus.RUNNING.toString()))
                        Utils.wait(1000);

                    /* This blocks until the job is not finalized */
                    while(!getJobStatus().equals(IndexJobStatus.FINISHED.toString()))
                        Utils.wait(1000);
                }
            });

            Utils.wait(5000);

            statusMonitorThread.start();

            try {

                indexNowThread.join();
                statusMonitorThread.join();

            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }

            /* Perform some queries and make sure the results are correct */
        }
    }
}
