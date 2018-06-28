package Utils;

import Enums.ArchiveTypes;
import Models.SolrDataModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.*;

public class Utils {

    private final static List<String> archiveTypes = new ArrayList<>();

    static {
        List<ArchiveTypes> enumValues = Arrays.asList(ArchiveTypes.values());

        for(ArchiveTypes value : enumValues) {
            archiveTypes.add(value.name());
        }
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
    }

    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.indexOf("."));
    }

    public static void createDirectory(String path) {
        File directory = new File(path);

        try {
            Files.createDirectory(directory.toPath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFileClosed(File file) {

        try {
            String line;

            Process lsofProcess = new ProcessBuilder(new String[]{"lsof", "|", "grep", file.getAbsolutePath()}).start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(lsofProcess.getInputStream()));

            while((line=reader.readLine())!=null) {
                if(line.contains(file.getAbsolutePath())) {
                    reader.close();
                    lsofProcess.destroy();

                    return false;
                }
            }

            reader.close();
            lsofProcess.destroy();

        } catch(Exception exception) {
            exception.printStackTrace();
        }

        return true;
    }

    public static boolean isArchive(String name) {
        if(!name.contains("."))
            return false;

        if (!archiveTypes.contains(name.split("\\.")[1].toUpperCase()))
            return false;

        return true;
    }

    private static boolean isDirectory(String path) {
        File file = new File(path);

        return file.isDirectory();
    }

    public static void cleanup(File file) {
        file.delete();

        File directory = file.getParentFile();

        if(directory != null && directory.listFiles().length == 0)
            directory.delete();
    }

    public static boolean checkModel(SolrDataModel model) {

        if(     model.getTimeStamp()           != null &&
                //model.getHostName()       != null &&
                //model.getJobId()          != null &&
                model.getLogLevel()       != null &&
                model.getMessage()        != null)
                //model.getSourceFileName() != null &&
           return true;

        return false;
    }

    public static void updateSourceFileName(SolrDataModel model, File file) {
            model.setSourceFileName(file.getName());
    }

    public static void updateLineNumber(SolrDataModel model, int lineNumber) {
        model.setLineNumber(Integer.toString(lineNumber));
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
