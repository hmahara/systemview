package eu.iamhelmi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

import io.quarkus.logging.Log;

public class OperatingSystemUtility {

	public static String runScript(String command){
		int iExitValue = 0;
		String sCommandString = "";
        sCommandString = command;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine oCmdLine = CommandLine.parse(sCommandString);
        //oCmdLine.addArgument(" -ef ");
        //oCmdLine.addArgument(" | ");
        //oCmdLine.addArgument(" grep ");
        //oCmdLine.addArgument(" java ");
        DefaultExecutor oDefaultExecutor = DefaultExecutor.builder().get();//new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        try {
        	oDefaultExecutor.setStreamHandler(streamHandler);
            iExitValue = oDefaultExecutor.execute(oCmdLine);
            return outputStream.toString();
        } catch (ExecuteException e) {
            System.err.println("Execution failed.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("permission denied.");
            e.printStackTrace();
        }
        return "return value of command: "+ sCommandString;
    }
	
	public static String showProcessGrep(String grep){
		int iExitValue = 0;
		String sCommandString = "ps aux";
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine oCmdLine = CommandLine.parse(sCommandString);

        DefaultExecutor oDefaultExecutor = DefaultExecutor.builder().get();//new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        try {
        	oDefaultExecutor.setStreamHandler(streamHandler);
            iExitValue = oDefaultExecutor.execute(oCmdLine);
            String s = outputStream.toString();
            if (grep == null || grep.length()<1) {
            	return s;
            }
            String[] ss = s.split(System.lineSeparator());
            StringBuffer sb = new StringBuffer();
            Pattern p = Pattern.compile(grep);
            if (ss != null) {
            	for (String a : ss) {
            		Matcher m = p.matcher(a);
            		if (m.find()) {
                    	Log.info("Match");
                    	sb.append(a).append(System.lineSeparator());
                    } 
            	}
            

            }
            return sb.toString();
        } catch (ExecuteException e) {
            System.err.println("Execution failed.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("permission denied.");
            e.printStackTrace();
        }
        return "return value of command: "+ sCommandString;
    }
}
