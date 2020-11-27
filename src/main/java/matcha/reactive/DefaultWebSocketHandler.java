//package matcha.reactive;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.socket.WebSocketHandler;
//import org.springframework.web.reactive.socket.WebSocketMessage;
//import org.springframework.web.reactive.socket.WebSocketSession;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Component
//@AllArgsConstructor
//public class DefaultWebSocketHandler implements WebSocketHandler {
//
//    private EventUnicastService eventUnicastService;
//    private ObjectMapper objectMapper;
//
//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        Flux<WebSocketMessage> messages = session.receive()
//                // .doOnNext(message -> { read message here or in the block below })
//                .flatMap(message -> {
//                    // or read message here
//                    System.err.println(message);
//                    return eventUnicastService.getMessages();
//                })
//                .flatMap(o -> {
//                    try {
//                        return Mono.just(objectMapper.writeValueAsString(o));
//                    } catch (JsonProcessingException e) {
//                        return Mono.error(e);
//                    }
//                }).map(session::textMessage);
//        return session.send(messages);
//    }
//}