package EventHandlers;

public abstract class LockableEventHandler extends AbstractEventHandler {
	@Override
    public boolean supportsLock() {
    	return true;
    }
}
