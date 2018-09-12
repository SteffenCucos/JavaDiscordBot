import net.dv8tion.jda.core.events.message.MessageReceivedEvent;


public class DefaultEventHandler extends EventHandler {

    String defaultString;

    public DefaultEventHandler(MessageReceivedEvent event, String defaultString) {
        this.event = event;
        this.defaultString = defaultString;
    }

    @Override
    public void handleEvent() throws InvalidEntityException {
        super.handleEvent();
        super.sendMessage(defaultString);
    }

}
