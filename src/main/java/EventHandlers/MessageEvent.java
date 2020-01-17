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

    public MessageEvent(MessageReceivedEvent event, boolean locked){
        this.event = event;
        if(locked) {
        	//Ignore the botname and command, we already know it
        	parseEvent(2);
        } else {
            parseEvent(0);
        }
    }

    private void parseEvent(int offset){
        if(event == null){
            return;
        }
        author = event.getAuthor();
        messageRaw = event.getMessage().getContentRaw();
        messageDisplay = event.getMessage().getContentDisplay();
        message = new ArrayList<String>(Arrays.asList(event.getMessage().getContentDisplay().trim().replaceAll(" +", " ").split(" ")));
        
        if(message.size() > 1 - offset) {
        	commandArgs = message.subList(2 - offset, message.size());
        }
        
        if(offset == 0) {
        	pointedAt = message.get(0);
        }
        
        if(message.size() > 1 && offset == 0){
            commandString = message.get(1);
        } else {
            commandString = "";
        }
    }
}
