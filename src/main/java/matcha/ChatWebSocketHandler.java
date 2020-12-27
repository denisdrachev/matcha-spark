package matcha;

import matcha.user.model.UserEntity;
import matcha.user.service.UserService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@WebSocket
public class ChatWebSocketHandler {

    private String sender, msg;
    private UserService userService = UserService.getInstance();
    public static Map<String, Session> webSocketConnection = new HashMap<>();

    @OnWebSocketConnect
    public void onConnect(Session userSession) throws Exception {

        System.out.println("onConnect! ");
//        String username = "User" + Chat.nextUserNumber++;
//        Chat.userUsernameMap.put(user, username);
//        Chat.broadcastMessage(sender = "Server", msg = (username + " joined the chat"));


        try {
            String token = userSession.getUpgradeRequest().getParameterMap().get("token").get(0);
            System.out.println(token);
//            String login = "login";
            UserEntity user = userService.checkUserByToken(token);
            webSocketConnection.put(user.getLogin(), userSession);


//            userSession.getRemote().sendString(String.valueOf(new JSONObject()
//                            .put("token", token)
////                    .put("login", login)
//            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("onClose! ");
        String keyFroRemove = null;
        for (Map.Entry<String, Session> stringSessionEntry : webSocketConnection.entrySet()) {
            if (stringSessionEntry.getValue() == user) {
                keyFroRemove = stringSessionEntry.getKey();
                break;
            }
        }
        if (keyFroRemove != null) {
            webSocketConnection.remove(keyFroRemove);
        }
//        webSocketConnection.re
//        String username = Chat.userUsernameMap.get(user);
//        Chat.userUsernameMap.remove(user);
//        Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
    }

    @OnWebSocketError
    public void onError(Session user, Throwable reason) {
        try {
            System.out.println("onError! ");
            String keyFroRemove = null;
            for (Map.Entry<String, Session> stringSessionEntry : webSocketConnection.entrySet()) {
                if (stringSessionEntry.getValue() == user) {
                    keyFroRemove = stringSessionEntry.getKey();
                    break;
                }
            }
            if (keyFroRemove != null) {
                webSocketConnection.remove(keyFroRemove);
            }
//        webSocketConnection.re
//        String username = Chat.userUsernameMap.get(user);
//        Chat.userUsernameMap.remove(user);
//        Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"));
        } catch (Exception e) {

        }
    }

    //TODO убрать лишнее
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