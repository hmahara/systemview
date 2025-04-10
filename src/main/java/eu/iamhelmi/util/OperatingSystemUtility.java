package eu.iamhelmi.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;

import io.quarkus.logging.Log;

public class OperatingSystemUtility {

	public static String runScript(String command){
		int iExitValue = 0;
		String sCommandString = "";
        sCommandString = command;
        Log.info("Executing command line on OS: "+sCommandString);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StringBuffer sb = new StringBuffer();
        LogOutputStream outputS = new LogOutputStream() {
			
			@Override
			protected void processLine(String line, int logLevel) {
				Log.info(line);
				sb.append(line).append(System.lineSeparator());
			}
		};
        
        CommandLine oCmdLine = CommandLine.parse(sCommandString);

        DefaultExecutor oDefaultExecutor = DefaultExecutor.builder().get();//new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        //PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputS);
        try {
        	oDefaultExecutor.setStreamHandler(streamHandler);
        	
            iExitValue = oDefaultExecutor.execute(oCmdLine);
            return sb.toString();
            //return outputStream.toString();
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
                    	//Log.info("Match");
                    	sb.append(a).append(System.lineSeparator());
                    } 
            	}
            

            }
            return sb.toString();
        } catch (ExecuteException e) {
            Log.error("Execution failed." + e.getMessage());
            
        } catch (IOException e) {
            Log.error("permission denied."+ e.getMessage());
        }
        return "Cannot execute command: "+ sCommandString;
    }
	
	public static String getDebianSoftareInstalled(String softwarePattern){
		int iExitValue = 0;
		String grepWord1 = "now";
		String sCommandString = "apt list \"*"+softwarePattern+"*\"";
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine oCmdLine = CommandLine.parse(sCommandString);

        DefaultExecutor oDefaultExecutor = DefaultExecutor.builder().get();//new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        try {
        	oDefaultExecutor.setStreamHandler(streamHandler);
            iExitValue = oDefaultExecutor.execute(oCmdLine);
            String s = outputStream.toString();
            if (grepWord1 == null || grepWord1.length()<1) {
            	return s;
            }
            String[] ss = s.split(System.lineSeparator());
            StringBuffer sb = new StringBuffer();
            Pattern p = Pattern.compile(grepWord1);
            if (ss != null) {
            	for (String a : ss) {
            		Matcher m = p.matcher(a);
            		if (m.find()) {
                    	//Log.info("Match");
                    	sb.append(a).append(System.lineSeparator());
                    } 
            	}
            

            }
            return sb.toString();
        } catch (ExecuteException e) {
            Log.error("Execution failed." + e.getMessage());
            
        } catch (IOException e) {
            Log.error("permission denied."+ e.getMessage());
        }
        return "Cannot execute command: "+ sCommandString;
    }
	
	public static String listOfFile(String folder){
		StringBuffer sb = new StringBuffer();
		if (folder == null || folder.length()<0) {
			sb.append("* no folder to list the files")
			.append(System.lineSeparator())
			;
			return sb.toString();
		}
		
		File check = new File(folder);
		if (check == null) {
			sb.append("* Invalid or non-existing folder: "+folder)
			.append(System.lineSeparator())
			;
			return sb.toString();
		}
		File[] files = check.listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 
		Log.info("Number files in the folder: "+files.length);
		
		for (File file : files) {
		    if (file.isFile()) {
		    	Date d = new Date(file.lastModified());
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        sb.append(" "+sdf.format(d)+"  ").append(file.getName())
		        .append(System.lineSeparator())
		        ;
		    }
		}
		
		return sb.toString();
		
	}
}
