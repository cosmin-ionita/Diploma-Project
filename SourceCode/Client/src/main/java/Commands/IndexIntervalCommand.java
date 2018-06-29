package Commands;

import APIs.HadoopDriverAPI;
import Exceptions.IncorrectParameterException;
import Interfaces.Command;

public class IndexIntervalCommand implements Command {

    private String[] params;

    public void setParams(String[] params) throws IncorrectParameterException {

       if(params.length == 1) {
           if(params[0].contains("m")) {
               int value = Integer.parseInt(params[0].split("m")[0]);

               if(value < 1 || value > 59)
                   throw new IncorrectParameterException("Incorrect number of minutes. It should be in the interval [1,59]");
           } else if(params[0].contains("h")) {
               int value = Integer.parseInt(params[0].split("h")[0]);

               if(value < 1 || value > 12)
                   throw new IncorrectParameterException("Incorrect number of hours. It should be in the interval [1,12]");
           } else {
               throw new IncorrectParameterException("Incorrect parameter");
           }
       } else {
           throw new IncorrectParameterException("Incorrect number of parameters");
       }

       this.params = params;
    }

    public void execute() {
        String message = HadoopDriverAPI.setIndexInterval(params[0]);

        System.out.println(message);
    }
}
