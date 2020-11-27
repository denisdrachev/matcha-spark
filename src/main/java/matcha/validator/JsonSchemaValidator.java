package matcha.validator;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import spark.resource.ClassPathResource;
//import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

//@Service
public class JsonSchemaValidator {

    private Schema schema;

    public JsonSchemaValidator(String schemaPath) throws IOException {
        try {
            ClassPathResource classPathResource = new ClassPathResource(schemaPath);
            if (!classPathResource.isReadable())
                throw new IOException("File not found. ".concat(schemaPath));
            InputStream inputStream = classPathResource.getInputStream();
            JSONObject jsonObject = new JSONObject(new JSONTokener(inputStream));
            schema = SchemaLoader.load(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean validate(String json) {
        JSONObject request = new JSONObject(json);
        schema.validate(request);
        return true;
    }
}
