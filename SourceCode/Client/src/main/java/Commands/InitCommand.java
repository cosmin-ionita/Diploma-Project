package Commands;

import Exceptions.IncorrectParameterException;
import Interfaces.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InitCommand implements Command {

    public void setParams(String[] params) throws IncorrectParameterException {}

    public void execute() {

        try {
            Process p = Runtime.getRuntime().exec("/bin/bash ./out/artifacts/Client_jar/script.sh");

            System.out.println("loGrep was successfully initialized!");

        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
