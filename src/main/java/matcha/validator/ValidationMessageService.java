package matcha.validator;

import com.google.gson.JsonElement;
import lombok.extern.slf4j.Slf4j;
import matcha.model.MyObject;
import matcha.response.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ValidationMessageService {

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private static ValidationMessageService validationMessageService;

    public static ValidationMessageService getInstance() {
        if (validationMessageService == null) {
            validationMessageService = new ValidationMessageService();
        }
        return validationMessageService;
    }

    public ResponseError prepareErrorMessage(String message) {
        ResponseError error = new ResponseError("error", message);
        log.info("Response error: {}", error);
        return error;
    }

    public ResponseOnlyType prepareMessageOkOnlyType() {
        ResponseOnlyType ok = new ResponseOnlyType("ok");
        log.info("Response: {}", ok);
        return ok;
    }

    public ResponseOkData prepareMessageOkData(Object o) {
        ResponseOkData ok = new ResponseOkData("ok", o);
        log.info("Response: {}", ok);
        return ok;
    }

    public ResponseOkDataObject prepareMessageOkData(JsonElement o) {
        ResponseOkDataObject ok = new ResponseOkDataObject("ok", o);
        log.info("Response: {}", ok);
        return ok;
    }

    public Response validateMessage(MyObject myObject) {
        if (myObject == null) {
            return new ResponseError("error", "Body is empty");
        }

        Set<ConstraintViolation<MyObject>> validate = validator.validate(myObject);

        String validatorMessage = validate.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        log.info("Validator message: ({}) {}", validatorMessage.length(), validatorMessage);

        if (validatorMessage.length() > 0)
            return new ResponseError("error", validatorMessage);
        return null;
    }
}
