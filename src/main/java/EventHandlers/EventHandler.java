package EventHandlers;

import java.io.File;

public abstract class EventHandler implements GenericEventHandler {

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

    public void sendMessage(String message){
    	if(message != null && message.length() > 0){
            this.messageEvent.event.getChannel().sendMessage(message).queue();
            System.out.println(this.toString());
    	}
    }
    
    public static String formatMessage(String message) {
    	String formatted = message.substring(0, Math.min(1994, message.length()));
    	return "```" + formatted + "```";
    }
    
    public boolean supportsLock() {
    	return false;
    }
    
    @Override
    public String toString(){
        String message = messageEvent.messageDisplay;
        String author = messageEvent.author.getName();
        return "Received a message from " + author + ": " + message;
    }
}
