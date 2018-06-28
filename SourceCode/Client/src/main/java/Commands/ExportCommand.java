package Commands;

import Enums.OutputMode;
import Interfaces.Command;
import Utils.ExportStatus;

public class ExportCommand implements Command {

    String[] params;

    public void setParams(String[] params){
        if(params.length == 1) {
            this.params = params;
        }
    }

    public void execute() {
        ExportStatus.fileName = params[0];
        //ExportStatus.format =

        ExportStatus.outputMode = OutputMode.FILE;
    }
}
