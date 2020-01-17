package EventHandlers;

public class FailedLockEventHandler extends AbstractEventHandler {
	
	public FailedLockEventHandler(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
	}
	
	@Override
	public void handleEvent() {
		String lockTarget = EventHandlerFactory.getLockTarget(messageEvent).toString();
		sendMessage("Lock on command: " + lockTarget + " failed");
	}
}
