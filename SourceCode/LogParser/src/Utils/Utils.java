package Utils;

import Models.SolrDataModel;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static List<String> archiveTypes = new ArrayList<>();
    
    static {
        archiveTypes.add("tar");
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
    }

    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static void createDirectory(String path) {
        File directory = new File(path);

        directory.mkdir();
    }

    public static boolean isArchive(String name) {
        if(!name.contains("."))
            return false;

        if(!archiveTypes.contains(name.split("\\.")[1])) {
            System.out.println(archiveTypes);
            System.out.println("NAME = " + name);

            return false;
        }

        return true;
    }

    public static boolean isDirectory(Path path) {
        File file = path.toFile();

        return file.isDirectory();
    }

    public static boolean isDirectory(String path) {
        File file = new File(path);

        return file.isDirectory();
    }

    public static void cleanup(File file) {
        file.delete();

        File directory = file.getParentFile();

        if(directory.listFiles().length == 0)
            directory.delete();
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

    public static boolean checkInput(String watchDirectory, String destinationDirectory) {
        String[] option1_tokens = watchDirectory.split("=");
        String[] option2_tokens = destinationDirectory.split("=");

        if(option1_tokens.length != 2 || option2_tokens.length != 2)
            return false;

        if(!option1_tokens[0].equals("--watch-directory") && !option1_tokens[0].equals("--w"))
            return false;
        if(!option2_tokens[0].equals("--destination-directory") && !option2_tokens[0].equals("--d"))
            return false;

        if(!isDirectory(option1_tokens[1]) || !isDirectory(option2_tokens[1]))
            return false;

        return true;
    }
}
