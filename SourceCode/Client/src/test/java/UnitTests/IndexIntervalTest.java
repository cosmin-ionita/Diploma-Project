package UnitTests;

import Commands.IndexIntervalCommand;
import Commands.StatusCommand;
import Exceptions.IncorrectParameterException;
import Utils.Utils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class IndexIntervalTest {

    private String getIndexInterval() {
        StatusCommand statusCommand = new StatusCommand();

        /* Redirect stdout to catch the status here */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;

        System.setOut(ps);

        statusCommand.execute();

        /* Redirect stdout back to System.out */
        System.out.flush();
        System.setOut(old);

        String status = baos.toString();

        return status.split(":")[1].split(" ")[1];
    }

    @Test
    public void testIndexInterval() {
        String interval = "1h";

        IndexIntervalCommand command = new IndexIntervalCommand();

        try {
            command.setParams(new String[] {interval});

            command.execute();

            Utils.wait(2000);

            assert getIndexInterval().equals(interval);

        } catch (IncorrectParameterException exception) {
            exception.printStackTrace();
        }
    }
}
