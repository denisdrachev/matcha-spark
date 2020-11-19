package matcha.validator;

import lombok.Getter;
import matcha.properties.ConfigProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ValidatorFactory {

    private ConfigProperties properties;
    @Getter
    private Map<String, JsonSchemaValidator> validatorMap = new HashMap();

    public ValidatorFactory(ConfigProperties properties) {
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
