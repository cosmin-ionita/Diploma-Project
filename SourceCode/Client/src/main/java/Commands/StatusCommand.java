package Commands;

import APIs.HadoopDriverAPI;
import Exceptions.IncorrectParameterException;
import Interfaces.Command;

public class StatusCommand implements Command {

    public void setParams(String[] params) throws IncorrectParameterException {}

    public void execute() {
        String message = HadoopDriverAPI.getStatus();

        System.out.println(message);
    }
}
