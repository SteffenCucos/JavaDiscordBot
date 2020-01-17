package EventHandlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import EventHandlers.EventHandler.MessageFormat;
import Exceptions.InvalidEntityException;

public class PythonEventHandler extends AbstractEventHandler  {

	public enum TYPE {
		 RUN,
		 CODE,
		 CREATE,
		 NULL
	}
	public TYPE type = TYPE.NULL;
	
    public PythonEventHandler(MessageEvent messageEvent){
        this.messageEvent = messageEvent;
        String command = messageEvent.commandArgs.get(0);
        switch(command) {
	        case "run":
	        	type = TYPE.RUN; break;
	        case "create":
	        	type = TYPE.CREATE; break;
	        default:
	        	if(command.contains("```")) {
	        		type = TYPE.CODE;
	        	} else {
	        		type = TYPE.NULL;
	        	}
	    }	
    }
    
    @Override
    public boolean supportsLock() {
    	return true;
    }

    @Override
    public void handleEvent() throws InvalidEntityException {
    	String output = "";
    	switch(type) {
	    	case RUN://runs a python.py file
	    		String[] params = extractParams(messageEvent.commandArgs);
	    		output = runWithParams(params);
	        	break;
	    	case CODE://idle style one liners
	    		createCode(parseCode(), "DEFAULT.py");
	        	output = runCode("DEFAULT.py");
	    		break;
	    	case CREATE://creates a python.py filew
	    		String filename = messageEvent.commandArgs.get(1);
	    		createCode(parseCode(), filename);
	    		break;
			default:
				break;
	    	}
    	sendMessage(output, MessageFormat.CODE_BLOCK);
    }
    
    
    private String[] extractParams(List<String> message) {
    	String[] params = new String[0];
    	int numParams = message.size() - 1;
    	if (numParams > 0) {
    		params = new String[numParams];
    		for (int i = 0; i < numParams; i ++) {
    			params[i] = message.get(i + 1); 
    		}
    	}
		return params;
	}

	private String runWithParams(String[] params) {
    	try {
    		String formatedParams = "";
    		for(String s : params) {
    			formatedParams += " " + s;
    		}
	    	Process p = Runtime.getRuntime().exec("python " + formatedParams);
	    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	        
	        String output = parseReadLines(stdInput);
	        String error = parseReadLines(stdError);
	        String toSend = "";
	        if (!output.equals("")) {
	        	toSend += "\n-----START-OUTPUT------\n"
		        		 + output 
		        		 + "-----END-OUTPUT------\n";
	        } 
	        if (!error.equals("")) {
	        	toSend += "\n-----START-ERROR------\n"
		        		+ error
		        		+ "-----END-ERROR------";
	        }
	        return toSend;
    	}  catch (Exception e) {
	    	System.out.println(e.getMessage());
	    	return "500 Error";
	    }
	}
    
    public void createCode(String code, String filename) {
    	try {
    		List<String> codeList = new ArrayList<String>();
    		codeList.add(code);
    		Path py = Paths.get(filename);
    		Files.write(py, codeList, Charset.forName("UTF-8"));
    	} catch (Exception e) {
    		//Path could not be made or code could not be written
    	}
    }
    
    public String runCode(String filename) {
	    return runWithParams(new String[] {filename});		
    }
    
	public String parseCode() {
    	String[] split = this.messageEvent.messageRaw.split("```");
    	return split[1];
    }
	
    static String parseReadLines(BufferedReader reader) throws Exception {
    	String s = null;
    	List<String> output = new ArrayList<String>();
    	while ((s = reader.readLine()) != null) {
	        	output.add(s);
	    }
    	String lines = "";
        for(String line : output) {
        	lines += line + "\n";
        }
	    return lines;
    }
}
