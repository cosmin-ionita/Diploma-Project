package Commands;

import Utils.*;
import Interfaces.Command;

public class GuiCommand implements Command{

    public void setParams(String[] params){ }

    public void execute() {
        Utils.showGUI();
    }
}
