package EventHandlers;

public interface EventHandler {
	enum MessageFormat {
		CODE_BLOCK,
		PLAIN_TEXT;
	}
	
    void handleEvent() throws Exception;
    void sendMessage(String message);
    void sendMessage(String message, MessageFormat format);
    boolean supportsLock();
}
