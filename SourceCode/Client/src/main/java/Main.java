import Utils.StringsMapping;

public class Main {

    private static void printHelp() {
        System.out.println(StringsMapping.help);
    }

    private static void showGui() {
        GuiManager manager = new GuiManager();
        manager.launchGui();
    }

    public static void main(String args[]) {

        if(args[0].equals(StringsMapping.queryShort) || args[0].equals(StringsMapping.queryLong) && args.length > 2) {
            QueryManager manager = QueryManager.getInstance();
        }
        else if(args[0].equals(StringsMapping.commandShort)  || args[0].equals(StringsMapping.commandLong)) {

        } else if(args[0].equals(StringsMapping.gui)) {
            showGui();
        } else {
            printHelp();
        }
    }
}
