package matcha.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Channels {

    NO_ACTION_CHANNEL("no-action-channel"),
    SCHEME_VALIDATION_ERROR("scheme-validation-error");

    private final String channelName;
}
