package EventHandlers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;

public class CommandEventHandler extends EventHandler {
	
	enum TYPE {
		 CD,
		 DIR,
		 CP,
		 CAT,
		 NULL
	}
	
	public TYPE type = TYPE.NULL;
	
	public CommandEventHandler(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
        String[] message = messageEvent.message;
        switch(message[2]) {
	        case "cd":
	        	type = TYPE.CD; break;
	        case "dir":
	        	type = TYPE.DIR; break;
	        case "cp":
	        	type = TYPE.CP; break;
	        case "cat":
	        	type = TYPE.CAT; break;
	        default:
	        	type = TYPE.NULL;
        }
	}
	
	@Override
	public void handleEvent() {
		switch(type) {
			case CD:
				String newDir = cd();
				sendMessage(newDir);
				break;
			case DIR:
				String dir = getDirString();
				sendMessage(dir);
				break;
			case CP:
				Entry<File, String> toSend = cp();
				if(toSend.getKey() == null) {
					sendMessage(toSend.getValue());
				} else {
					sendFile(toSend.getKey());
				}
				break;
			case CAT:
				String cat = getCatString();
				sendMessage(cat);
				break;
			default:
				break;
		}
	}
	
	private Entry<File, String> cp() {
		String copy = messageEvent.message[3];
		try {
			String path = EventHandlerFactory.directory.getCanonicalPath() + "/" + copy;
			File file = new File(path);
			if(file.isFile()) {
				return new AbstractMap.SimpleEntry<File, String>(file, "all ok");
			}
			return new AbstractMap.SimpleEntry<File, String>(null, "File " + copy + " is not a file");
		} catch (Exception e ) {
			return new AbstractMap.SimpleEntry<File, String>(null, "File " + copy + " is not a file");
		}
	}

	private String cd() {
		String dest = messageEvent.message[3];
		try {
			if(dest.equals("%STARTPATH%")) {//resets the directory to where it started
				EventHandlerFactory.directory = EventHandlerFactory.STARTPATH;
				return EventHandlerFactory.directory.getCanonicalPath();
			} else {
				String path = EventHandlerFactory.directory.getCanonicalPath() + "/" + dest;
				EventHandlerFactory.directory = new File(path);
				return EventHandlerFactory.directory.getCanonicalPath();
			}
		} catch (Exception e) { return dest + " is not a directory"; }
		
	}

	public String getCatString() {
		String cat = "";
		String fname = messageEvent.message[3];
		try {
			File f = new File(EventHandlerFactory.directory.getCanonicalPath() + "/"  + fname);
			boolean isFile = f.isFile();
			boolean isDir = f.isDirectory();
			if(f.isFile()) {
				//read the file
				List<String> contents = Files.readAllLines(Paths.get(f.getAbsolutePath()), StandardCharsets.UTF_8);
				String formatted = "```";
				for (String line : contents) {
					formatted += "\n" + line;
				}
				formatted += "```";
				return formatted;
			} else if (isDir) {
				return f.getName() + " is a directory not a file.";
			} else {
				return f.getName() + " is not a file or a directory.";
			}
		} catch (IOException e) { return "No such file: " + fname; }
	}

	public String getDirString() {
		try {
			String dir = "```" + EventHandlerFactory.directory.getCanonicalPath();
			for(File f: EventHandlerFactory.directory.listFiles()) {
				if (f.isDirectory()) {
					dir += "\n<D> ";
				} else if (f.isFile()){
					dir += "\n<F> ";
				} else {
					dir += "\n    ";
				}
				dir += f.getName();
			}
			dir += "```";
			return dir;
		} catch (IOException e) { return "dir failed"; }
	}
	
}
