package Monitors;

import Enums.IndexJobStatus;
import HDFS_Watcher.Status;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Currently OUT of service [possible removal]
 */

public class JobMonitor {

    private static class ReportComparator implements Comparator<ApplicationReport> {
        @Override
        public int compare(ApplicationReport report1, ApplicationReport report2) {
            int id1 = report1.getApplicationId().getId();
            int id2 = report2.getApplicationId().getId();

            return Integer.compare(id1, id2);
        }
    }

    private static void sleep(int milis) {
        try {
            Thread.sleep(milis);
        }catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    private static ApplicationId getIndexJobAppId(YarnClient client) {
        ApplicationId result = null;

        try {

            while(true) {

                Thread.sleep(500);

                List<ApplicationReport> reports = client.getApplications();

                Collections.sort(reports, new ReportComparator());
                Collections.reverse(reports);

                YarnApplicationState state = reports.get(0).getYarnApplicationState();

                if(state.equals(YarnApplicationState.FINISHED))
                    ;
                else {
                    result = reports.get(0).getApplicationId();
                    break;
                }
            }
        }
        catch (InterruptedException | IOException | YarnException exception) {
            exception.printStackTrace();
        }

        return result;
    }

    public static void startMonitoring() {

        System.out.println("Entering monitoring job...");

        Thread t  = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    YarnClient client = YarnClient.createYarnClient();

                    Configuration conf = new YarnConfiguration();

                    client.init(conf);

                    client.start();

                    ApplicationId id = getIndexJobAppId(client);

                    while(true) {

                        sleep(1000);

                        ApplicationReport report = client.getApplicationReport(id);

                        if(report.getYarnApplicationState().toString().equals(IndexJobStatus.FINISHED.toString())) {
                            System.out.println("Setting index status to FINISHED, breaking...");
                            Status.setIndexJobStatus(IndexJobStatus.FINISHED);
                            break;
                        }
                    }
                }
                catch (YarnException | IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        t.start();
    }
}
