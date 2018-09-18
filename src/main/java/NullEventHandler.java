import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NullEventHandler extends EventHandler{

    public NullEventHandler(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
    }

    @Override
    public void handleEvent() throws InvalidEntityException {
        //Do nothing
    }

}

