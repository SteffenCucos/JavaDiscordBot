package EventHandlers;

import Exceptions.InvalidEntityException;

public class NullEventHandler extends AbstractEventHandler {

    public NullEventHandler(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
    }

    @Override
    public void handleEvent() throws InvalidEntityException {
        //Do nothing
    }

}

