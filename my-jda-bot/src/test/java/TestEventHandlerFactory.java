import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.junit.Test;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.mockito.Mockito;
import static org.junit.Assert.assertTrue;

public class TestEventHandlerFactory {

    EventHandlerFactory handlerFactory = new EventHandlerFactory();

    @Test
    public void TestPingCommand(){

        String author = "TestName";
        String[] messages = new String[]{"@" + Main.BOT_NAME + " !Ping",
                                         "@" + Main.BOT_NAME + " !ping",
                                         "@" + Main.BOT_NAME + " !pInG"};

        for(String message: messages){
            MessageReceivedEvent event = createEvent(author,message);
            verifyGetEventHandler(EventHandlerFactory.PONG_STRING,event,"We received a message from " + author + ": " + message);
        }

        MessageReceivedEvent event = createEvent(author,"@SteffenBot Ping");//forgot the "!" char
        verifyGetEventHandler(EventHandlerFactory.UNKNOWN_COMMAND,event,"We received a message from " + author + ": " + "@" + Main.BOT_NAME + " Ping");
    }

    @Test
    public void TestHelpCommand(){
        String author = "TestName";
        String[] messages = new String[]{"@" + Main.BOT_NAME + " !Help",
                                         "@" + Main.BOT_NAME + " !help",
                                         "@" + Main.BOT_NAME + " !hElP"};

        for(String message: messages){
            MessageReceivedEvent event = createEvent(author,message);
            verifyGetEventHandler(EventHandlerFactory.HELP_STRING,event,"We received a message from " + author + ": " + message);
        }

        MessageReceivedEvent event = createEvent(author,"@SteffenBot Help");//forgot the "!" char
        verifyGetEventHandler(EventHandlerFactory.UNKNOWN_COMMAND,event,"We received a message from " + author + ": " + "@" + Main.BOT_NAME + " Help");
    }

    @Test
    public void TestNullEvent(){
        try{
            handlerFactory.getEventHandler(null);
            assertTrue(false);
        } catch (InvalidEntityException e) {
            assertTrue(true);//should throw on null
        }
    }

    @Test
    public void TestFormatMessage(){
        String[] messages = {"@" + Main.BOT_NAME + " !Ping",
                             "@" + Main.BOT_NAME + "!Ping",
                             "@" + Main.BOT_NAME + " ",
                             "@" + Main.BOT_NAME + " !Ping     a    b    c"};
        String[] expected = {"!Ping",
                             "!Ping",
                             "",
                             "!Ping a b c"};

        for(int i = 0; i< Math.max(messages.length, expected.length); i++){
            String got = handlerFactory.formatMessage(messages[i]);
            assertTrue(got.equals(expected[i]));
        }
    }

    @Test
    public void TestMessageDirectedAtBot(){
        String[] trueMessages = {"@" + Main.BOT_NAME + " ",
                                 "@" + Main.BOT_NAME + "",
                                 "@" + Main.BOT_NAME,
                                 "@" + Main.BOT_NAME + "!Ping",
                                 "@" + Main.BOT_NAME + " !Ping",
                                 "@SteffenBot",};

        String[] falseMessages = {"@" + Main.BOT_NAME.toLowerCase(),
                                  "@" + Main.BOT_NAME.toUpperCase(),
                                  Main.BOT_NAME,
                                  ""};
        for(String s: trueMessages){
            assertTrue(handlerFactory.messageDirectedAtBot(s));
        }
        for(String s: falseMessages){
            assertTrue(!handlerFactory.messageDirectedAtBot(s));
        }
    }

    public MessageReceivedEvent createEvent(String userName, String content){
        Message message = Mockito.mock(Message.class);
        User user = Mockito.mock(User.class);
        Mockito.when(user.getName()).thenReturn(userName);
        Mockito.when(message.getAuthor()).thenReturn(user);
        Mockito.when(message.getContentDisplay()).thenReturn(content);

        return new MessageReceivedEvent(null,0,message);
    }

    public void verifyGetEventHandler(String defaultString, MessageReceivedEvent event, String toString){
        try {
            EventHandler handler = handlerFactory.getEventHandler(event);
            verifyDefaultHandler(handler,defaultString,event,toString);
        } catch (Exception e){
            assertTrue(false);
        }
    }

    public void verifyDefaultHandler(EventHandler handler, String defaultString, MessageReceivedEvent event, String toString){
        assertTrue(handler instanceof DefaultEventHandler);//it is an instance of DefaultEventHandler
        DefaultEventHandler castedHandler = (DefaultEventHandler)handler;
        assertTrue(castedHandler.defaultString.equals(defaultString));
        verifyGenericHandler(handler,event,toString);
    }

    public void verifyGenericHandler(EventHandler handler, MessageReceivedEvent event, String toString){
        assertTrue(handler.event.equals(event));//it passes the event into the handler
        assertTrue(handler.toString().equals(toString));//it gave it the right author and message
    }
}
