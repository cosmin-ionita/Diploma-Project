import java.io.*;

public class Logger {

    private static File file = new File("/Users/ioni/log_output.txt");

    public static void out(String message) {
        System.out.println(message);

//        message += "\n";
//
//        try{
//            FileOutputStream stream = new FileOutputStream(file);
//
//            stream.write(message.getBytes());
//
//            stream.close();
//
//        }catch (IOException exception) {
//            exception.printStackTrace();
//        }

    }
}
