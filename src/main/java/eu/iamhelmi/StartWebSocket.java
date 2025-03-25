package eu.iamhelmi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/start-websocket/{name}")
@ApplicationScoped
public class StartWebSocket {
	
	Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
    	broadcast("User " + name + " joined");
        sessions.put(name, session);
        System.out.println("onOpen> " + name);
    }

    @OnClose
    public void onClose(Session session, @PathParam("name") String name) {
    	sessions.remove(name);
        broadcast("User " + name + " left");
        System.out.println("onClose> " + name);
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
    	sessions.remove(name);
        broadcast("User " + name + " left on error: " + throwable);
        System.out.println("onError> " + name + ": " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) {
    	broadcast(">> " + name + ": " + message);
        System.out.println("onMessage> " + name + ": " + message);
    }
    
    public void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
