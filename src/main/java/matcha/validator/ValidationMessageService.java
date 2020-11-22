package matcha.validator;

import com.google.gson.JsonElement;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.model.MyObject;
import matcha.response.*;
import org.json.JSONObject;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Service
@Slf4j
@NoArgsConstructor
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

    public ResponseError prepareValidateMessage(BindingResult bindingResult) {
        String validatorMessage = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.info("Validator message: {}", validatorMessage);
        return new ResponseError("error", validatorMessage);
    }

    public ResponseError prepareMessageNullObject() {
        return new ResponseError("error", "Request body is empty");
    }

    public ResponseError prepareErrorMessage(String message) {
        ResponseError error = new ResponseError("error", message);
        log.info("Response: {}", error);
        return error;
    }

    public ResponseError prepareErrorMessage(StringBuilder message) {
        return prepareErrorMessage(message.toString());
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

        log.info("Validator message: {}", validatorMessage);

        if (validatorMessage.length() > 0)
            return new ResponseError("error", validatorMessage);
        return null;
    }

    public ResponseDataList prepareMessageOkDataList(List list) {
        ResponseDataList ok = new ResponseDataList("ok", list);
        log.info("Response: {}", ok);
        return ok;
    }
//    public boolean validateJsonBySchema(String schemaName, String json) {
////        try {
//        validatorFactory.getValidatorMap().get(schemaName).validate(json);
//        return true;
////        } catch (ValidationException e) {
////            String clientMessage;
////            if (e.getCausingExceptions().size() != 0) {
////                clientMessage = Utils.clearValidateMessage(e.getCausingExceptions());
////            } else {
////                if (e.getMessage().matches(StringConstants.validationDelimiter))
////                    clientMessage = e.getMessage().split(StringConstants.validationDelimiter)[1];
////                else
////                    clientMessage = e.getMessage();
////            }
////            StringBuilder sb = new StringBuilder()
////                    .append("Failed schema validate. Schema: ")
////                    .append(schemaName)
////                    .append(" json: ")
////                    .append(json)
////                    .append(" Message: ")
////                    .append(clientMessage);
////            String result = sb.toString();
////            log.warn(result);
////            return new ResponseError("error", clientMessage);
////        }
//    }
}
