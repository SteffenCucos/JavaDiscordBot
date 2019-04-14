package EventHandlers;

public abstract class EventHandler implements GenericEventHandler {

    public MessageEvent messageEvent;

    public void handleEvent() throws InvalidEntityException {
        if(this.messageEvent.event == null) {
            throw new InvalidEntityException("null event!");
        }
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

        return "We received a message from " + author + ": " + message;
    }
}
