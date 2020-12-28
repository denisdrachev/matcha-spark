package matcha;

import lombok.extern.slf4j.Slf4j;
import matcha.user.model.UserEntity;
import matcha.user.service.UserService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebSocket
public class ChatWebSocketHandler {

    private UserService userService = UserService.getInstance();
    public static Map<String, Session> webSocketConnection = new HashMap<>();

    @OnWebSocketConnect
    public void onConnect(Session userSession) throws Exception {
        log.info("onConnect!");
        try {
            String token = userSession.getUpgradeRequest().getParameterMap().get("token").get(0);
            log.info(token);
            UserEntity user = userService.checkUserByToken(token);
            webSocketConnection.put(user.getLogin(), userSession);
        } catch (Exception e) {
            log.info("Error socket onConnect");
        }
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        log.info("onClose!");
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
    }

    @OnWebSocketError
    public void onError(Session user, Throwable reason) {
        log.info("onError!");
        try {
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
        } catch (Exception e) {

        }
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        log.info("onMessage: {}", message);
        try {
            user.getRemote().sendString(String.valueOf(new JSONObject()
                    .put("userMessage", "Hi hi hi!")
                    .put("userlist", message)
            ));
        } catch (Exception e) {
            log.info("Error socket onMessage");
        }
    }
}