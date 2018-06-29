package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    private final static Random random = new Random();
    private final static RandomString randomString = new RandomString();

    private final static String[] logLevels = {"INFO", "WARN", "ERROR", "FATAL"};

    private final static List<String> words = new ArrayList<>();

    /**
     * This initializes the words list with all the words in the specified dictionary
     */
    static {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/ec2-user/workDir/words.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This generates a log message (10 random words separated by space)
     * @return - the generated message
     */
    private static String generateMessage() {
        String result = "";

        for(int i = 0; i < 10; i++) {
            result += words.get(random.nextInt(words.size() - 1)) + " ";
        }

        return result;
    }

    /**
     * This generates a log line, composed of timestamp, loglevel and message
     * @return
     */
    public static String generateFileLine() {

        int year = random.nextInt(19) + 2000;     /* Range: [2000, 2018] */
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(31) + 1;

        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        int second = random.nextInt(60);
        int milisecond = random.nextInt(999);

        String logLevel = logLevels[random.nextInt(4)];

        String message = generateMessage();

        String H = (hour / 10 == 0) ? "0" + hour : hour + "";
        String M = (minute / 10 == 0) ? "0" + minute : minute + "";
        String S = (second / 10 == 0) ? "0" + second : second + "";;

        String result = year + "-" + month + "-" + day + "T" + H + ":" + M + ":" + S + "." + milisecond + "Z " + logLevel + " " + message + "\n";

        return result;
    }
}
