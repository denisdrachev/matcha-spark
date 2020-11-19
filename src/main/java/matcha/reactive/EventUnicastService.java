package matcha.reactive;

import matcha.event.model.Event;
import org.json.JSONObject;
import reactor.core.publisher.Flux;

public interface EventUnicastService {
    /**
     * Add message to stream
     *
     * @param next - message which will be added to stream
     */
//    void onNext(EventSocket next);
    void onNext(Event next);

    Flux<JSONObject> getMessages();
}
