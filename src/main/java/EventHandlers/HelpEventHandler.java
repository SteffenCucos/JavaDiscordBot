package EventHandlers;

import java.util.HashMap;
import java.util.Map;

public class HelpEventHandler extends EventHandler {

    public static final String HelpPingString = "'!Ping' will prompt the bot to respond with 'Pong!'.";
    public static final String HelpWikiString = "'!Wiki' followed by a topic will prompt the bot to search for and paste into chat the most relevant Wikipedia article to the topic.";
    public static final String HelpHelpString = "'!Help' followed by another valid command will prompt the bot to give you instructions on how to use that command.\n'!Help' on its own will give a list of all available commands.";
    public static final String HelpPythonString = "!Python can be used in 3 modes.\n" 
    											+ "!Python run file.py arg1 arg2 ... argN will run the program file.py with the supplied arguments.\n"
    											+ "!Python create file.py ``` CODE ``` will create a named file containing the python code contained in the code block.\n"
    											+ "!Python ``` CODE ``` will run the code in the code block as if it is being run in the interpreter";
    public static final String HelpCommandString = "!Command allows you to interact with the native file system the bot is running on using the commands 'dir', 'cd', 'cp' and 'cat'.\n"
    											+ "!Command dir will display the contents of the current directory.\n"
    											+ "!Command cd dirName will change directory to a valid directory from the current directory. Valid dirNames include '.', '..', or a sub directory of the current directory.\n"
    											+ "!Command cp fileName will upload the chosen file to the chat.\n"
    											+ "!Command cat fileName will read out the contents of the chosen file to the chat.";
    public static final String HelpBasicString = "Available Commands:\n!Help\n!Ping\n!Wiki\n!Python\n!Command";

    public static final Map<String, String> HelpMap = new HashMap<String, String>() {{
        put("!ping", HelpPingString );
        put("!help", HelpHelpString);
        put("!wiki", HelpWikiString);
        put("!python", HelpPythonString);
        put("!command", HelpCommandString);
        put("", HelpBasicString);
    }};

    public HelpEventHandler(MessageEvent messageEvent){
        this.messageEvent = messageEvent;
    }

    @Override
    public void handleEvent() throws InvalidEntityException {
        String[] args = parseHelpCommand();//args[0] will be the command
        String helpMessage = HelpMap.getOrDefault(args[0].toLowerCase(), HelpBasicString);
        sendMessage(helpMessage);
    }

    public String[] parseHelpCommand(){
        int numArgs = messageEvent.message.length;
        if(numArgs == 2){ return new String[] {""}; }

        String[] args = new String[numArgs];
        for (int i = 2; i < numArgs; i++) {
            args[i-2] = messageEvent.message[i];
        }
        return args;
    }
}
