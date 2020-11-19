package matcha.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gateways {

    REGISTRATION("register"),
    LOGIN("login"),
    PROFILE_UPDATE("profile-update"),
    PROFILE_GET("profile-get");

    private final String uri;
}
