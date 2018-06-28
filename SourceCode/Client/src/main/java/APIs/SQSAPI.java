package APIs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;

import java.util.List;

public class SQSAPI {

    private static String QUEUE_NAME="ioni-sqs-test";

    public static void GetMessage() {
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        while(true) {
            List<Message> messages = sqs.receiveMessage(QUEUE_NAME).getMessages();

            if(messages != null)
                if(messages.get(0).toString().split("\"").length > 75)
                    System.out.println("Time : " + messages.get(0).toString().split("\"")[17] + "Message: " +  messages.get(0).toString().split("\"")[75]);
        }
    }
}
