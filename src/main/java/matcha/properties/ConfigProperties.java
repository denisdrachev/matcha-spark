package matcha.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;

@Getter
@Setter
public class ConfigProperties {

    String folder;
    List<String> schemasList;

    public static boolean usersDefaultActive = true;
    public static String baseUrl = "https://matcha-42-front.herokuapp.com";
    public static String usersDefaultInitLogin;
    public static String usersDefaultInitPassword;
    public static String usersDefaultInitCode;
    public static boolean emailSend = false;
    public static boolean debug = true;
}
