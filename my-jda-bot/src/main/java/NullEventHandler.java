import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NullEventHandler extends EventHandler{

    public NullEventHandler(MessageReceivedEvent event) {
        this.event = event;
    }

    @Override
    public void handleEvent() throws InvalidEntityException {
        //Do nothing
    }

}

