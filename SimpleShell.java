import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleShell
{
    public static void main(String[] args) throws java.io.IOException
    {
        String commandLine; // what command we input for the script shell
        ArrayList<String> history = new ArrayList<>() ; //to manage the history for task 3
        ProcessBuilder builder = new ProcessBuilder() ; //ProcessBuilder instance initialization
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in)); // reader to read from user inputs
        List<String> commands ; // ProcessBuilder's method command() requires variable arguments and it must be in lists 
        List<String> hiscommands = new ArrayList<>(); // history part requires this to choose between current or commands on history 
        boolean runhistory = false ; // to choose between history commands or current commands
        String parent = System.getProperty("user.dir") ; // we use it to get current working path
        while (true)
        {
            System.out.print("jsh>");
            commandLine = console.readLine();
            history.add(commandLine) ; //adding inputs to history arraylist to keep track of the command inputs
            if (commandLine.equals("")) continue;
            else if(commandLine.equalsIgnoreCase("exit")) break ;
            else if(commandLine.equalsIgnoreCase("history")){ //pdf page 4 task 1
                int i = 0 ;
                for (String a:
                        history) {
                    System.out.println(i++ + " "  + a); //printing all history
                }
            }
            else if(commandLine.equalsIgnoreCase("!!")){ //pdf page 4 task 2
                try{
                    runhistory = true ;
                    System.out.println(history.get(history.size()-2));
                    hiscommands = Arrays.asList(history.get(history.size()-2).split(" ")) ; 
                }catch (NullPointerException e){
                    System.out.println("No history found");
                }
            }
            else if(commandLine.charAt(0)=='!' && commandLine.charAt(1)!='!'){ //pdf page 4 task 3
                try{
                    runhistory = true ;
                    hiscommands = Arrays.asList(history.get(commandLine.charAt(1) - '0').split(" ")) ;
                }catch (NullPointerException e){
                    System.out.println("No history found");
                }
            }
            else {
            }
            commands = Arrays.asList(commandLine.split(" ")) ;

            //task 2 starts from here
            if(commands.get(0).equalsIgnoreCase("cd")){
                if(commands.size()==1){
                    builder.directory(new File(parent)) ;
                }
                else{
                    builder.directory(new File(commands.get(1))) ;
                }
            }

            //and ends here

            //selecting between history command or current commands
            
            if(runhistory){
                builder.command(hiscommands) ;
                runhistory = false ;
            }
            else{
                builder.command(commands) ;
            }

            //task 1 starts here

            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(builder.start().getInputStream())) ;
                String output = "" ;
                String temp ;
                while((temp = reader.readLine())!=null){
                    output += temp ;
                    output += System.getProperty("line.separator") ;
                }
                System.out.print(output);
            }catch (IOException e){
                if(commandLine.equalsIgnoreCase("history")){

                }
                else{
                    System.out.println("Invalid command");
                }
            }

        }
    }
}