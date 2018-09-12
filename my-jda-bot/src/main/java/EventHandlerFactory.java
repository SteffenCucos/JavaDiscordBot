import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.util.HashMap;
import java.util.Map;

public class EventHandlerFactory {
    public static final int NAME_LENGTH = Main.BOT_NAME.length();
    public static final String PONG_STRING = "Pong!";
    public static final String UNKNOWN_COMMAND = "Unknown command.";
    public static final String HELP_STRING = "Help_Goes_Here";

    public enum COMMAND {
        PING,
        HELP,
        UNKNOWN,
        NULL
    }

    public static final Map<String, COMMAND> commandMap = new HashMap<String, COMMAND>() {{
       put("!ping",COMMAND.PING);
       put("!help",COMMAND.HELP);
       put("",COMMAND.UNKNOWN);
    }};

    public EventHandlerFactory(){}

    public EventHandler getEventHandler(MessageReceivedEvent event) throws InvalidEntityException {

        if(event == null) {
            throw new InvalidEntityException("null event!");
        }

        COMMAND command = parseMessageForCommand(event);

        switch (command) {
            case PING:
                return new DefaultEventHandler(event, PONG_STRING);
            case HELP:
                return new DefaultEventHandler(event, HELP_STRING);
            case UNKNOWN:
                return new DefaultEventHandler(event, UNKNOWN_COMMAND);
            case NULL:
                return new NullEventHandler(event);
            default:
                return new NullEventHandler(event);
        }
    }

    public COMMAND parseMessageForCommand(MessageReceivedEvent event){
        String message = event.getMessage().getContentDisplay();

        //Must start with @name
        if(!messageDirectedAtBot(message)){ return COMMAND.NULL; }
        message = formatMessage(message);
        COMMAND command = commandMap.getOrDefault(message.toLowerCase(),COMMAND.UNKNOWN);

        return command;
    }

    public boolean messageDirectedAtBot(String message){
        if(message.length() < NAME_LENGTH + 1){ return false; }
        return message.substring(0,NAME_LENGTH + 1).equals("@"+Main.BOT_NAME);
    }

    public String formatMessage(String message){
        message = message.substring(NAME_LENGTH + 1);//cut off the @bot
        return message.trim().replaceAll("\\s{2,}", " ");
    }
}
