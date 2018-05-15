package Utils;

import Gui.GuiMgr;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

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

    public static Options buildOptions() {
        Options options = new Options();

        Option queryOption = Option.builder(StringsMapping.queryShort)
                .required(true)
                .hasArg(true)
                .hasArgs()
                .longOpt(StringsMapping.queryLong)
                .desc(StringsMapping.queryDescription)
                .build();

        Option timeOption = Option.builder(StringsMapping.timeIntervalShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(2)
                .longOpt(StringsMapping.timeIntervalLong)
                .desc(StringsMapping.timeIntervalDescription)
                .build();

        Option dateOption = Option.builder(StringsMapping.dateIntervalShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(2)
                .longOpt(StringsMapping.dateIntervalLong)
                .desc(StringsMapping.dateIntervalDescription)
                .build();

        Option orderByDate = Option.builder(StringsMapping.orderByDateShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(1)
                .longOpt(StringsMapping.orderByDateLong)
                .desc(StringsMapping.orderByDateDescription)
                .build();

        Option orderByTime = Option.builder(StringsMapping.orderByTimeShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(1)
                .longOpt(StringsMapping.orderByTimeLong)
                .desc(StringsMapping.orderByTimeDescription)
                .build();

        Option export = Option.builder(StringsMapping.exportShort)
                .required(false)
                .hasArg(true)
                .numberOfArgs(1)
                .longOpt(StringsMapping.exportLong)
                .desc(StringsMapping.exportDescription)
                .build();

        Option gui = Option.builder(StringsMapping.guiShort)
                .required(false)
                .hasArg(false)
                .numberOfArgs(0)
                .longOpt(StringsMapping.guiLong)
                .desc(StringsMapping.guiDescription)
                .build();

        options.addOption(queryOption);
        options.addOption(timeOption);
        options.addOption(dateOption);
        options.addOption(orderByDate);
        options.addOption(orderByTime);
        options.addOption(export);
        options.addOption(gui);

        return options;
    }
}
