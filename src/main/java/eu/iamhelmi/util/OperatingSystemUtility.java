package eu.iamhelmi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

public class OperatingSystemUtility {

	public static String runScript(String command){
		int iExitValue = 0;
		String sCommandString = "";
        sCommandString = command;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CommandLine oCmdLine = CommandLine.parse(sCommandString);
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
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
}
