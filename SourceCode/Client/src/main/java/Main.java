import Executors.CommandExecutor;
import Executors.QueryExecutor;
import Managers.CommandManager;
import Managers.QueryManager;
import Models.Command;
import Models.Query;
import Utils.Utils;

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
 */

public class Main {


    public static void main(String args[]) {

        if(Utils.isQuery(args)) {
            QueryManager manager = QueryManager.getInstance();

            Query query = manager.buildQuery(args);

            QueryExecutor.executeQuery(query);

        }
        else if(Utils.isCommand(args)) {
            CommandManager manager = CommandManager.getInstance();

            Command command = manager.buildCommand(args);

            CommandExecutor.executeCommand(command);

        } else if(Utils.isGUI(args)) {
            Utils.showGUI();
        } else {
            Utils.printHelp();
        }
    }
}
