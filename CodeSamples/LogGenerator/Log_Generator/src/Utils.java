import java.io.File;

public class Utils {

    public static boolean isFile(String filePath) {
        File file = new File(filePath);

        return file.isFile();
    }

    public static boolean isDirectory(String path) {
        File file = new File(path);

        return file.isDirectory();
    }

    public static boolean checkInput(String inputFile, String outputDirectory) {
        String[] option1_tokens = inputFile.split("=");
        String[] option2_tokens = outputDirectory.split("=");

        if(option1_tokens.length != 2 || option2_tokens.length != 2)
            return false;

        if(!option1_tokens[0].equals("--input-file"))
            return false;
        if(!option2_tokens[0].equals("--destination-directory"))
            return false;

        if(!isFile(option1_tokens[1]) || !isDirectory(option2_tokens[1]))
            return false;

        return true;
    }
}
