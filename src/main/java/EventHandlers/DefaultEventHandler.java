package EventHandlers;

import Exceptions.InvalidEntityException;

public class DefaultEventHandler extends AbstractEventHandler {

    public String defaultString;

    public DefaultEventHandler(MessageEvent messageEvent, String defaultString) {
        this.messageEvent = messageEvent;
        this.defaultString = defaultString;
    }

    @Override
    public void handleEvent() throws InvalidEntityException {
        super.handleEvent();
        sendMessage(defaultString);
    }

}
