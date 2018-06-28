package API;

import Enums.IndexJobStatus;
import HDFS_Watcher.Status;
import HDFS_Watcher.Watcher;
import Models.Response;
import Utils.StringsMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("status")
    public Response status() {
        return new Response(Status.getGeneralStatus());
    }

    @RequestMapping("index_interval")
    public Response index_interval(@RequestParam(value="interval") String interval) {
        Status.setIndexInterval(interval);

        boolean result = Watcher.updateIndexInterval();

        return new Response(StringsMapping.indexIntervalResponse(result, interval));
    }

    @RequestMapping("trigger_index_job")
    public Response trigger_index_job() {

        if(Status.getIndexJobStatus().equals(IndexJobStatus.RUNNING)) {
            return new Response(IndexJobStatus.RUNNING.toString());

        } else {

            boolean jobStarted = Watcher.triggerIndexJob();

            if(jobStarted) {
                Status.setIndexJobStatus(IndexJobStatus.RUNNING);
                return new Response(IndexJobStatus.RUNNING.toString());
            }
            else {
                Status.setIndexJobStatus(IndexJobStatus.FINISHED);
                return new Response(IndexJobStatus.FINISHED.toString());
            }
        }
    }

    @RequestMapping("index_status")
    public Response index_status() {
        return new Response(Status.getIndexJobStatus().toString());
    }
}