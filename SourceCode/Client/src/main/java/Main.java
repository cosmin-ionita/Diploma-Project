import Utils.StringsMapping;
import Utils.Utils;
import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;

/**
 *
 *  user@host:$> loGrep [-q field1=value1 field2=value2 field3=value3]
 *                      [-ti time1 time2]
 *                      [-di date1 date2]
 *                      [-obd asc/desc]
 *                      [-obt asc/desc]
 *                      [-e file.txt]
 *
 *  user@host:$> loGrep [--query field1=value1 field2=value2 field3=value3]
 *                      [--time-interval time1 time2]
 *                      [--date-interval date1 date2]
 *                      [--order-by-date asc/desc]
 *                      [--order-by-time asc/desc]
 *                      [--export file.txt]
 *
 *  user@host:$> loGrep [-c status]
 *                      [-c --index-interval 1h]
 *                      [-c --data-retention-policy delete]
 *
 *
 *
 */

public class Main {

    private static void printHelper(Options options) {
        HelpFormatter formatter = new HelpFormatter();

        formatter.setWidth(500);
        formatter.printHelp( "loGrep", options );
    }

    public static void main(String args[]) {
        Options options = Utils.buildOptions();

        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine line = parser.parse(options, args);

            if(line.hasOption(StringsMapping.queryShort)) {

            }

            if(line.hasOption(StringsMapping.dateIntervalShort)) {

            }

            if(line.hasOption(StringsMapping.timeIntervalShort)) {

            }


            printHelper(options);
        }
        catch( ParseException exp ) {
            System.err.println("Parsing failed. Reason: " + exp.getMessage());
        }
    }
}
