package EventHandlers;

public class SuccessLockEventHandler extends EventHandler {
	
	public SuccessLockEventHandler(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
	}
	
	@Override
	public void handleEvent() {
		String lockTarget = EventHandlerFactory.getLockTarget(messageEvent).toString();
		sendMessage("Now locked on command: " + lockTarget);
	}
}
