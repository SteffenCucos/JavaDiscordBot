package EventHandlers;

public class UnlockEventHandler extends AbstractEventHandler {

	public UnlockEventHandler(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
	}
	
	@Override
	public void handleEvent() {
		sendMessage("Bot is now unlocked");
	}
}