import Commands.ExportCommand;
import Commands.IndexNowCommand;
import Enums.OutputMode;
import Exceptions.IncorrectParameterException;
import Executors.Executor;
import Factories.CommandFactory;
import Interfaces.Command;
import Interfaces.Query;
import Utils.ExportStatus;
import Utils.StringsMapping;
import Utils.Utils;
import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;
import org.apache.commons.math3.analysis.function.Exp;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 *  user@host:$> loGrep [-q field1=value1 field2=value2 field3=value3]
 *                      [-ti time1 time2]
 *                      [-di date1 date2]
 *                      [-obt asc/desc]
 *                      [-e file.txt]
 *                      [-f]
 *                      [-in]

 *
 *  user@host:$> loGrep [--query field1=value1 field2=value2 field3=value3]
 *                      [--time-interval time1 time2]
 *                      [--date-interval date1 date2]
 *                      [--order-by-timestamp asc/desc]
 *                      [--export file.txt]
 *                      [--fields]
 *                      [--index-now]
 *
 *                      [--status]
 *                      [--index-interval 1h]
 *
 */

public class Main {

    private static class OptionComparator implements Comparator<Option> {
        public int compare(Option option1, Option option2) {

            if(option1.getOpt().equals(StringsMapping.queryShort))
                return 1;

            return 0;
        }
    }

    private static boolean containsExportCommand(List<Command> commands) {
        for(int i = 0; i < commands.size(); i++) {
            if(commands.get(i) instanceof ExportCommand)
                return true;
        }

        return false;
    }

    private static boolean containsIndexNowCommand(List<Command> commands) {
        for(int i = 0; i < commands.size(); i++) {
            if(commands.get(i) instanceof IndexNowCommand)
                return true;
        }

        return false;
    }

    private static void printHelper(Options options) {
        HelpFormatter formatter = new HelpFormatter();

        formatter.setOptionComparator(new OptionComparator());
        formatter.setWidth(80);
        formatter.printHelp( "loGrep", options );
    }

    private static void handleResults() throws IOException{

        if(ExportStatus.outputMode.equals(OutputMode.STDOUT)) {
            while(true) {
                String results = Executor.getMoreResults();

                if(results == null)
                    break;

                System.out.println(results);
                System.out.println("\nDisplay the next batch of results? (ENTER): ");
                System.in.read();
            }
        } else if(ExportStatus.outputMode.equals(OutputMode.FILE)) {

            boolean exportAllResults = false;

            FileWriter writer = new FileWriter(ExportStatus.fileName);

            while(true) {
                String results = Executor.getMoreResults();

                if(results == null)
                    break;

                writer.write(results);

                if(!exportAllResults) {
                    System.out.println("\nOne batch of results was exported. Do you want to export the next batch of results (Y)? Export all the results to the file (A)?: ");

                    char c = (char)System.in.read();

                    while(c != 'Y' && c != 'A')
                        System.out.println("You need to type either \'Y\' or \'A\'");


                    if(c == 'A')
                        exportAllResults = true;
                }
            }

            writer.close();
        }
    }

    private static void accumulateQueries(List<Query> queries, CommandLine line, CommandFactory factory) throws IncorrectParameterException {
        if(line.hasOption(StringsMapping.queryShort)) {
            Query query = factory.getQuery(StringsMapping.queryShort);

            query.setParams(line.getOptionValues(StringsMapping.queryShort));
            queries.add(query);
        }

        if(line.hasOption(StringsMapping.dateIntervalShort)) {
            Query query = factory.getQuery(StringsMapping.dateIntervalShort);

            query.setParams(line.getOptionValues(StringsMapping.dateIntervalShort));
            queries.add(query);
        }

        if(line.hasOption(StringsMapping.timeIntervalShort)) {
            Query query = factory.getQuery(StringsMapping.timeIntervalShort);

            query.setParams(line.getOptionValues(StringsMapping.timeIntervalShort));
            queries.add(query);
        }

        if(line.hasOption(StringsMapping.orderByTimeStampShort)) {
            Query query = factory.getQuery(StringsMapping.orderByTimeStampShort);

            query.setParams(line.getOptionValues(StringsMapping.orderByTimeStampShort));
            queries.add(query);
        }
    }

    private static void accumulateCommands(List<Command> commands, CommandLine line, CommandFactory factory) throws IncorrectParameterException {
        if(line.hasOption(StringsMapping.fieldsShort)) {
            Command command = factory.getCommand(StringsMapping.fieldsShort);
            commands.add(command);
        }

        if(line.hasOption(StringsMapping.statusShort)) {
            Command command = factory.getCommand(StringsMapping.statusShort);
            commands.add(command);
        }

        if(line.hasOption(StringsMapping.indexIntervalShort)) {
            Command command = factory.getCommand(StringsMapping.indexIntervalShort);

            command.setParams(line.getOptionValues(StringsMapping.indexIntervalShort));
            commands.add(command);
        }

        if(line.hasOption(StringsMapping.indexNowShort)){
            Command command = factory.getCommand(StringsMapping.indexNowShort);
            commands.add(command);
        }

        if(line.hasOption(StringsMapping.guiShort)) {
            Command command = factory.getCommand(StringsMapping.guiShort);
            commands.add(command);
        }

        if(line.hasOption(StringsMapping.initCommand)) {
            Command command = factory.getCommand(StringsMapping.initCommand);
            commands.add(command);
        }

        if(line.hasOption(StringsMapping.exportShort)) {
            Command command = factory.getCommand(StringsMapping.exportShort);
            command.setParams(line.getOptionValues(StringsMapping.exportShort));
            commands.add(command);
        }
    }

    public static void main(String args[]) {
        Options options = Utils.buildOptions();

        List<Query> queries = new ArrayList<>();
        List<Command> commands = new ArrayList<>();

        CommandFactory factory = new CommandFactory();
        CommandLineParser parser = new DefaultParser();

        ExportStatus.outputMode = OutputMode.STDOUT;

        try {
            CommandLine line = parser.parse(options, args);

            accumulateQueries(queries, line, factory);

            accumulateCommands(commands, line, factory);

            if(commands.size() > 0) {
                Executor.executeCommands(commands);

                if(containsExportCommand(commands) || containsIndexNowCommand(commands)) {
                    if(queries.size() > 0) {
                        Executor.executeQueries(queries);
                        handleResults();
                    }
                }
            }

            else if(queries.size() > 0) {
                Executor.executeQueries(queries);
                handleResults();
            }
            else
                printHelper(options);
        }
        catch(ParseException | IncorrectParameterException | HttpSolrClient.RemoteSolrException | SolrServerException | IOException exception) {
            System.out.println(exception.getMessage());

            printHelper(options);
        }
    }
}
