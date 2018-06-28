import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ParseEngine.*;

import Models.S3Model;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import flexjson.JSONDeserializer;

public class WatchEngine {

    private static String destinationDirectory = "";

    private static String queueName = "ioni-sqs-demo";

    private static ExecutorService executor = Executors.newFixedThreadPool(16);

    private static String workDirPath = "/home/ec2-user/workDir/binaries/parserWorkDir/";

    private static void processMessage(Message message) throws IOException {

        AmazonS3 client = AmazonS3ClientBuilder.defaultClient();

        JSONDeserializer deserializer = new JSONDeserializer().use(null, S3Model.class);

        S3Model deserializedModel = (S3Model)deserializer.deserialize(message.getBody(), S3Model.class);

        executor.submit(() -> {
            try {

                int count;
                byte data[] = new byte[2048];

                String key = deserializedModel.Records.get(0).s3.object.key;
                String bucketName = deserializedModel.Records.get(0).s3.bucket.name;

                System.out.println("Key = " + key);
                System.out.println("Bucket Name = " + bucketName);

                S3Object fullObject = client.getObject(bucketName, key);

                S3ObjectInputStream stream = fullObject.getObjectContent();

                String archiveName = workDirPath + key;

                System.out.println("Processing archive: " + archiveName);

                FileOutputStream outputStream = new FileOutputStream(archiveName);

                while ((count = stream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                }

                outputStream.close();
                stream.close();

                Path archivePath = new File(archiveName).toPath();

                ParseEngine.parseArchive(archivePath, destinationDirectory);

            }catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        /* TODO Fix this */

        /*if(args.length != 2 || !Utils.checkInput(args[0], args[1])) {
            Logger.out("Usage: java -jar Diploma-Project.jar --watch-directory=/path/to/in/dir --destination-directory=/path/to/out/dir");
            return;
        }*/

        WatchEngine.destinationDirectory = args[0].split("=")[1];

        //WatchEngine.destinationDirectory = args[1].split("=")[1];

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        while(true) {

            List<Message> messages = sqs.receiveMessage(queueName).getMessages();

            for(Message message : messages) {
                processMessage(message);
            }

            for(Message message : messages) {
                sqs.deleteMessage(queueName, message.getReceiptHandle());
            }
        }
    }
}
