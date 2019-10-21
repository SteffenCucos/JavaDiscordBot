package EventHandlers;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import Main.Main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EventHandlerFactory {
    public static final int NAME_LENGTH = Main.BOT_NAME.length();
    public static final String PONG_STRING = "Pong!";
    public static final String UNKNOWN_COMMAND = "Unknown command.";
    
    public static File directory = new File(".");
    public static final File STARTPATH = new File(".");
    private COMMAND lockCommand = null;
    
    public enum COMMAND {
        PING,
        HELP,
        UNKNOWN,
        WIKI,
    	PYTHON,
    	COMMAND,
    	LOCK,
    	UNLOCK,
        NULL
    }

    public static final Map<String, COMMAND> commandMap = new HashMap<String, COMMAND>() {{
       put("!ping",COMMAND.PING);
       put("!help",COMMAND.HELP);
       put("!wiki",COMMAND.WIKI);
       put("!python", COMMAND.PYTHON);
       put("!command", COMMAND.COMMAND);
       put("!lock", COMMAND.LOCK);
       put("!unlock", COMMAND.UNLOCK);
       put("",COMMAND.UNKNOWN);
    }};

    public EventHandlerFactory(){}

    public EventHandler getEventHandler(MessageReceivedEvent event) throws InvalidEntityException {
        if(event == null) {
            throw new InvalidEntityException("null event!");
        } 
        
    	MessageEvent messageEvent = new MessageEvent(event, false);
        COMMAND command = validateMessageEvent(messageEvent);
        EventHandler handler = getEventHandler(command, messageEvent); 
        
        if(handler instanceof SuccessLockEventHandler) {
        	lockCommand = getLockTarget(messageEvent);
        } else if (handler instanceof UnlockEventHandler) {
        	lockCommand = null;
        } else if(lockCommand != null) {
        	return getEventHandler(lockCommand, new MessageEvent(event, true));
        } 
              
        return handler;
    }
    
    public EventHandler getEventHandler(COMMAND command, MessageEvent messageEvent) {
        switch (command) {
	        case PING:   	return new DefaultEventHandler(messageEvent, PONG_STRING);
	        case UNKNOWN:	return new DefaultEventHandler(messageEvent, UNKNOWN_COMMAND);
	        case HELP:   	return new HelpEventHandler(messageEvent);
	        case WIKI:   	return new WikiEventHandler(messageEvent);
	        case PYTHON: 	return new PythonEventHandler(messageEvent);
	        case COMMAND:	return new CommandEventHandler(messageEvent);
	        case LOCK: {
	        	COMMAND lockTarget = getLockTarget(messageEvent);
	        	EventHandler lockTargetHandler = getEventHandler(lockTarget, messageEvent);
	        	if(lockTargetHandler.supportsLock()) {
	        		return new SuccessLockEventHandler(messageEvent);
	        	} 
	        	return new FailedLockEventHandler(messageEvent);
	        }
	        case UNLOCK:	return new UnlockEventHandler(messageEvent);
	        case NULL:   	return new NullEventHandler(messageEvent);
	        default:     	return new NullEventHandler(messageEvent);
	    }
    }

    public COMMAND validateMessageEvent(MessageEvent event) {
        String receiver = event.pointedAt;
        String commandString = event.commandString;
        if(event.message.get(0).toLowerCase().equals("!unlock")) { return COMMAND.UNLOCK; }
        if (!messageDirectedAtBot(receiver)) { return COMMAND.NULL; }
        if(commandString.equals("")) { return COMMAND.UNKNOWN; }//message[] looks like this {"@BotName"}

        COMMAND command = commandMap.getOrDefault(commandString.toLowerCase(),COMMAND.UNKNOWN);
        return command;
    }
    
    public static COMMAND getLockTarget(MessageEvent event) {
        if(event.commandArgs.size() < 1) { return COMMAND.NULL; }
        String lockTargetString = "!" + event.commandArgs.get(0);
        COMMAND command = commandMap.getOrDefault(lockTargetString.toLowerCase(),COMMAND.UNKNOWN);
        return command;
    }
    

    public boolean messageDirectedAtBot(String message){
        if(message.length() < NAME_LENGTH + 1){ return false; }
        return message.equals("@"+ Main.BOT_NAME);
    }
}
