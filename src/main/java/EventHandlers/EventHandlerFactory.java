import EventHandlers.EventHandler;
import EventHandlers.HelpEventHandler;
import EventHandlers.NullEventHandler;
import EventHandlers.WikiEventHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.util.HashMap;
import java.util.Map;

public class EventHandlerFactory {
    public static final int NAME_LENGTH = Main.BOT_NAME.length();
    public static final String PONG_STRING = "Pong!";
    public static final String UNKNOWN_COMMAND = "Unknown command.";
    public static final String HELP_STRING = "Available Commands:\n!Help\n!Ping\n!Wiki";

    public enum COMMAND {
        PING,
        HELP,
        UNKNOWN,
        WIKI,
        NULL
    }

    public static final Map<String, COMMAND> commandMap = new HashMap<String, COMMAND>() {{
       put("!ping",COMMAND.PING);
       put("!help",COMMAND.HELP);
       put("!wiki",COMMAND.WIKI);
       put("",COMMAND.UNKNOWN);
    }};

    public EventHandlerFactory(){}

    public EventHandler getEventHandler(MessageReceivedEvent event) throws InvalidEntityException {

        if(event == null) {
            throw new InvalidEntityException("null event!");
        }

        MessageEvent messageEvent = new MessageEvent(event);
        COMMAND command = validateMessageEvent(messageEvent);

        switch (command) {
            case PING:
                return new DefaultEventHandler(messageEvent, PONG_STRING);
            case UNKNOWN:
                return new DefaultEventHandler(messageEvent, UNKNOWN_COMMAND);
            case HELP:
                return new HelpEventHandler(messageEvent);
            case WIKI:
                return new WikiEventHandler(messageEvent);
            case NULL:
                return new NullEventHandler(messageEvent);
            default:
                return new NullEventHandler(messageEvent);
        }
    }

    public COMMAND validateMessageEvent(MessageEvent event) {
        String receiver = event.message[0];
        String commandString = event.commandString;
        if (!messageDirectedAtBot(receiver)) { return COMMAND.NULL; }
        if(commandString.equals("")) { return COMMAND.UNKNOWN; }//message[] looks like this {"@BotName"}

        COMMAND command = commandMap.getOrDefault(commandString.toLowerCase(),COMMAND.UNKNOWN);
        return command;
    }

    public boolean messageDirectedAtBot(String message){
        if(message.length() < NAME_LENGTH + 1){ return false; }
        return message.equals("@"+Main.BOT_NAME);
    }
}
