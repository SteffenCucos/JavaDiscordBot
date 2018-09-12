import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class EventHandler implements GenericEventHandler {

    public MessageReceivedEvent event;

    public  void handleEvent() throws InvalidEntityException{
        if(this.event == null) {
            throw new InvalidEntityException("null event!");
        }
    }

    public void sendMessage(String message){
        this.event.getChannel().sendMessage(message).queue();
        System.out.println(this.toString());
    }

    @Override
    public String toString(){
        String message = event.getMessage().getContentDisplay();
        String author = event.getAuthor().getName();

        return "We received a message from " + author + ": " + message;
    }
}
