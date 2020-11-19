package matcha.reactive;

import lombok.extern.slf4j.Slf4j;
import matcha.event.model.Event;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class EventUnicastServiceImpl implements EventUnicastService {

    private EmitterProcessor<JSONObject> processor = EmitterProcessor.create();

    @Override
    public void onNext(Event next) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("event", next);
        log.trace("Next Event: {}", jsonObject);
        processor.onNext(jsonObject);
    }

    @Override
    public Flux<JSONObject> getMessages() {
        return processor.publish().autoConnect();
    }
}
