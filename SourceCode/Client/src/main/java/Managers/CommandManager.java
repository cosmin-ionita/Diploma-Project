package Managers;


import Models.Command;

public class CommandManager {

    private static CommandManager instance;

    private CommandManager() {
        instance = new CommandManager();
    }

    public static CommandManager getInstance() {
        return instance;
    }

   public Command buildCommand(String args[]) {
        Command command = new Command();



        return command;
   }
}
