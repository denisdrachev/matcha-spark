package matcha;

import matcha.user.service.UserService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONObject;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebSocket
public class ChatWebSocketHandler {

    private String sender, msg;
    private UserService userService = UserService.getInstance();
    public static Map<String, Session> webSocketConnection = new HashMap<>();

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        System.out.println("onConnect! ");
//        String username = "User" + Chat.nextUserNumber++;
//        Chat.userUsernameMap.put(user, username);
//        Chat.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));

        String token = user.getUpgradeRequest().getParameterMap().get("token").get(0);
        System.out.println(token);

        try {
            String login = "login";
//            String login = userService.checkUserToToken(authorization);
            user.getRemote().sendString(String.valueOf(new JSONObject()
                    .put("token", token)
//                    .put("login", login)
            ));
//            webSocketConnection.put(login, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("onClose! ");
//        webSocketConnection.re
//        String username = Chat.userUsernameMap.get(user);
//        Chat.userUsernameMap.remove(user);
//        Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.println("onMessage! " + message);
        try {
            user.getRemote().sendString(String.valueOf(new JSONObject()
                    .put("userMessage", "Hi hi hi!")
                    .put("userlist", message)
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Chat.broadcastMessage(sender = Chat.userUsernameMap.get(user), msg = message);
    }

}