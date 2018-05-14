package Utils;

import Gui.GuiMgr;

public class Utils {

    public static void showGUI() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                javafx.application.Application.launch(GuiMgr.class);
            }
        });

        t.start();
    }

    public static void printHelp() {
        System.out.println(StringsMapping.help);
    }

    public static boolean isQuery(String[] args) {
        return args[0].equals(StringsMapping.queryShort) || args[0].equals(StringsMapping.queryLong) && args.length > 2;
    }

    public static boolean isCommand(String[] args) {
        return args[0].equals(StringsMapping.commandShort)  || args[0].equals(StringsMapping.commandLong) && args.length > 2;
    }

    public static boolean isGUI(String[] args) {
        return args[0].equals(StringsMapping.gui);
    }
}
