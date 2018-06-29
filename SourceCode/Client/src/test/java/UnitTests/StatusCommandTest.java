package UnitTests;

import Commands.StatusCommand;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StatusCommandTest {

    @Test
    public void testStatus() {
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

        assert !status.equals("Error");
    }
}
