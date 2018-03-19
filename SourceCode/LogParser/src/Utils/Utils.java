import Models.SolrDataModel;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
    }

    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static boolean checkModel(SolrDataModel model) {

        if(     model.getDate()           != null &&
                model.getHostName()       != null &&
                model.getJobId()          != null &&
                model.getLogLevel()       != null &&
                model.getMessage()        != null &&
                model.getSourceFileName() != null &&
                model.getTime()           != null)

            return true;

        return false;
    }

    public static void updateHostname(SolrDataModel model) {
        try{
            InetAddress host = InetAddress.getLocalHost();
            model.setHostName(host.getHostName());
        }
        catch (UnknownHostException exception) {
            exception.printStackTrace();
        }
    }

    public static void updateSourceFileName(SolrDataModel model, File file) {
            model.setSourceFileName(file.getName());
    }
}
