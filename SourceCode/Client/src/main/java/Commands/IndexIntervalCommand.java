package Commands;

import APIs.HadoopDriverAPI;
import Exceptions.IncorrectParameterException;
import Interfaces.Command;

public class IndexIntervalCommand implements Command {

    private String[] params;

    public void setParams(String[] params) throws IncorrectParameterException {
       /* TODO check params here */

       this.params = params;
    }

    public void execute() {
        String message = HadoopDriverAPI.setIndexInterval(params[0]);

        System.out.println(message);
    }
}
