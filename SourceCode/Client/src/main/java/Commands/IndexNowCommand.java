package Commands;

import APIs.HadoopDriverAPI;
import Enums.IndexJobStatus;
import Exceptions.IncorrectParameterException;
import Interfaces.Command;

public class IndexNowCommand implements Command {

    public void setParams(String[] params) throws IncorrectParameterException {}

    public void execute() {

        String response = HadoopDriverAPI.triggerIndexJob();

        if(response.equals(IndexJobStatus.RUNNING.toString())) {

            System.out.println("Waiting for the index job to be completed...");

            while(true) {
                String status = HadoopDriverAPI.getIndexJobStatus();

                if(status.equals(IndexJobStatus.FINISHED.toString()))
                    break;

                try {
                    Thread.sleep(2000);
                }catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

            System.out.println("Index job completed!");
        }
    }
}
