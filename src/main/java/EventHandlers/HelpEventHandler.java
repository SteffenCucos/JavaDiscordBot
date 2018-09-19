package EventHandlers;

import java.util.HashMap;
import java.util.Map;

public class HelpEventHandler extends EventHandler {

    public static final String HelpPingString = "'!Ping' will prompt the bot to respond with 'Pong!'.";
    public static final String HelpWikiString = "Asking the bot '!Wiki' followed by a topic will prompt the bot to search for and paste into chat the most relevant Wikipedia article to the topic.";
    public static final String HelpHelpString = "'!Help' followed by another valid command will prompt the bot to give you instructions on how to use that command.\n'!Help' on its own will give a list of all available commands.";
    public static final String HelpBasicString = "Available Commands:\n!Help\n!Ping\n!Wiki";

    public static final Map<String, String> HelpMap = new HashMap<String, String>() {{
        put("!ping", HelpPingString );
        put("!help", HelpHelpString);
        put("!wiki", HelpWikiString);
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
