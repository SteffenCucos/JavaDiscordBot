package EventHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public List<String> commandArgs;
    public List<String> message;
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
        this.message = new ArrayList<String>(Arrays.asList(event.getMessage().getContentDisplay().trim().replaceAll(" +", " ").split(" ")));
        
        this.commandArgs = this.message.subList(2, message.size());
        
        this.pointedAt = message.get(0);
        if(this.message.size() > 1){
            this.commandString = message.get(1);
        } else {
            this.commandString = "";
        }
    }
}
