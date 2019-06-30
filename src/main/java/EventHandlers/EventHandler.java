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
    	this.messageEvent.event.getChannel().sendFile(file).queue();
    }

    public void sendMessage(String message){
    	if(message != null && message.length() > 0){
            this.messageEvent.event.getChannel().sendMessage(message).queue();
            System.out.println(this.toString());
    	}
    }

    @Override
    public String toString(){
        String message = messageEvent.messageDisplay;
        String author = messageEvent.author.getName();
        return "Received a message from " + author + ": " + message;
    }
}
