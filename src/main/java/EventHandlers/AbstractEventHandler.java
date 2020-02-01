package EventHandlers;

import java.io.File;

import Exceptions.InvalidEntityException;

public abstract class AbstractEventHandler implements EventHandler {
	
	public static final String CODE_BLOCK_IDENTIFIER = "```";
	public static final int MAX_RAW_MESSAGE_LENGTH = 2000;
	public static final int MAX_FORMATTED_MESSAGE_LENGTH = MAX_RAW_MESSAGE_LENGTH - 2*CODE_BLOCK_IDENTIFIER.length();
	
    public MessageEvent messageEvent;

    public void handleEvent() throws InvalidEntityException {
        if(this.messageEvent.event == null) {
            throw new InvalidEntityException("null event!");
        }
    }
    
    public void sendFile(File file) {
    	try {
        	this.messageEvent.event.getChannel().sendFile(file).queue();
    	} catch(Exception e) {
    		sendMessage(e.getMessage());
    	}
    }
    
    public void sendMessage(String message) {
    	sendMessage(message, MessageFormat.PLAIN_TEXT);
    }
    
    public void sendMessage(String message, MessageFormat format) {
    	if(message != null && message.length() > 0) {
    		message = formatMessage(message, format);
    		this.messageEvent.event.getChannel().sendMessage(message).queue();
            System.out.println(this.toString());
    	}
    }
    
    public static String formatMessage(String message, MessageFormat format) {
    	if(format == MessageFormat.CODE_BLOCK) {
    		message = message.substring(0, Math.min(MAX_FORMATTED_MESSAGE_LENGTH, message.length()));
			message = CODE_BLOCK_IDENTIFIER + message + CODE_BLOCK_IDENTIFIER;
		}  else {
			message.substring(0, Math.min(MAX_RAW_MESSAGE_LENGTH, message.length()));
		}
    	return message;
    }
    
    @Override
    public String toString() {
        String message = messageEvent.messageDisplay;
        String author = messageEvent.author.getName();
        return "Received a message from " + author + ": " + message;
    }
}
