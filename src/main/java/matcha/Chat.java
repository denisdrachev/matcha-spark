//package matcha;
//
//import spark.Session;
//
//import java.text.SimpleDateFormat;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class Chat {
//
//    static Map<Session, String> userUsernameMap = new ConcurrentHashMap();
//    static int nextUserNumber = 1; //Used for creating the next username
//
//
//    public static void broadcastMessage(String sender, String message) {
//        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
//            try {
//                session.getRemote().sendString(String.valueOf(new JSONObject()
//                        .put("userMessage", createHtmlMessageFromSender(sender, message))
//                        .put("userlist", userUsernameMap.values())
//                ));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    //Builds a HTML element with a sender-name, a message, and a timestamp,
//    private static String createHtmlMessageFromSender(String sender, String message) {
//        return article().with(
//                b(sender + " says:"),
//                p(message),
//                span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
//        ).render();
//    }
//}
