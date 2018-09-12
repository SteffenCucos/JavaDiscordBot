import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DefaultEventListener extends ListenerAdapter {


    private EventHandlerFactory eventHandlerFactory = new EventHandlerFactory();

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        try {
            EventHandler eventHandler = eventHandlerFactory.getEventHandler(event);
            if(!event.getAuthor().getName().equals("SteffenBot")){
                    eventHandler.handleEvent();
            }
        } catch (InvalidEntityException e){

        }
    }
}
