import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MessageEvent  {
    public MessageReceivedEvent event;
    public String messageRaw;
    public String messageDisplay;
    /*
    message[0] = "@SteffenBot"
    message[1] = "Some command"
    message[2 ...] = command params
    */
    public String[] message;
    public String commandString;
    public String pointedAt;
    public User author;

    public MessageEvent(MessageReceivedEvent event){
        this.event = event;
        parseEvent();
    }

    private void parseEvent(){
        if(this.event == null){
            return;
        }
        this.author = event.getAuthor();
        this.messageRaw = event.getMessage().getContentRaw();
        this.messageDisplay = event.getMessage().getContentDisplay();
        this.message = event.getMessage().getContentDisplay().split(" ");
        this.pointedAt = message[0];
        if(this.message.length > 1){
            this.commandString = message[1];
        } else {
            this.commandString = "";
        }
    }
}
