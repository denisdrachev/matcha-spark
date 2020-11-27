package matcha.validator;

import lombok.Getter;
import matcha.properties.ConfigProperties;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory3 {

    private ConfigProperties properties;
    @Getter
    private Map<String, JsonSchemaValidator> validatorMap = new HashMap();

    public ValidatorFactory3(ConfigProperties properties) {
        this.properties = properties;
        properties.getSchemasList().forEach(s -> {
            try {
                validatorMap.put(s.split("[.]")[0],
                        new JsonSchemaValidator(properties.getFolder().concat(File.separator).concat(s))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        validatorMap.forEach((s, jsonSchemaValidator) -> System.out.println("key: " + s + "; value: " + jsonSchemaValidator));
    }
}
