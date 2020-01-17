package Main;

import EventHandlers.AbstractEventHandler;
import EventHandlers.EventHandlerFactory;
import Exceptions.InvalidEntityException;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class DefaultEventListener extends ListenerAdapter {


    private EventHandlerFactory eventHandlerFactory = new EventHandlerFactory();

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        try {
            if(!event.getAuthor().getName().equals(Main.BOT_NAME)){
                AbstractEventHandler eventHandler = eventHandlerFactory.getEventHandler(event);
                eventHandler.handleEvent();
            }
        } catch (InvalidEntityException e){

        }
    }
}
