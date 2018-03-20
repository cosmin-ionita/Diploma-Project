import Models.SolrDataModel;

/**
 * This class provides a set of public methods each one corresponding to a parser template. All the methods implemented
 * here will be tried one by one until a line is correctly parsed into a model.
 *
 * All those template methods should start with "parser_"
 */
public class ParserTemplates {

    public static void parser_log4jTemplate(String line, SolrDataModel model) {

        try {

            String[] tokens = line.split(" ");

            if(tokens.length < 7) {
                model = null;
                return;
            }

            model.setDate(tokens[0]);
            model.setTime(tokens[1]);
            model.setLogLevel(tokens[3]);

            if(tokens[6].split("=").length < 2)
                return;

            model.setJobId(tokens[6].split("=")[1]);

            /* Here we get the index right after the job id */
            int messageStartIndex = line.indexOf(tokens[6]) + tokens[6].length() + 3;

            model.setMessage(line.substring(messageStartIndex));

            return;

        } catch (Exception exception) {
            Logger.out("ERROR LINE = " + line);
            //model = null;
        }
    }

    /*public static void anotherTemplate(String line, SolrDataModel model) {

    }*/
}
