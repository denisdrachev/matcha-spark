package matcha.properties;

import lombok.Getter;
import lombok.Setter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
public class ConfigProperties {

    String folder;
    List<String> schemasList;

    public static boolean usersDefaultActive = true;
    public static String baseUrl = "http://localhost";
    public static String basePort = "4567";
    public static String usersDefaultInitLogin;
    public static String usersDefaultInitPassword;
    public static String usersDefaultInitCode;
    public static boolean emailSend = false;
}
