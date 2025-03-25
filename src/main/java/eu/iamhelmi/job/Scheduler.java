package eu.iamhelmi.job;

import java.util.Date;

import eu.iamhelmi.StartWebSocket;
import eu.iamhelmi.util.OperatingSystemUtility;
import io.quarkus.logging.Log;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Inject;

//@ApplicationScoped
public class Scheduler {
	
	@Inject
	StartWebSocket ws;
	@Location("process.html")
	Template process;

	@Scheduled(every="10h")     
    void increment() {
        Log.info("scheduler is running");
        //ws.broadcast("clean");
        ws.broadcast("|");
        ws.broadcast(" |");
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ws.broadcast("Now is "+ new Date().toLocaleString());
        ws.broadcast(OperatingSystemUtility.runScript("ps"));
        ws.broadcast(OperatingSystemUtility.runScript("apt list"));
    }
	
}
