package matcha;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import java.net.URI;

import static spark.Spark.*;

public class qqq {

    public qqq() {

        try
        {
            WebSocketContainer ws = ContainerProvider.getWebSocketContainer();

            ws.connectToServer(ChatWebSocketHandler.class,new URI("ws://echo.websocket.org/?encoding=text"));
//            closeLatch.await();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }

//        staticFileLocation("/public"); //index.html is served at localhost:4567 (default port)
//        webSocket("/chat", ChatWebSocketHandler.class);
//        init();
    }
}
