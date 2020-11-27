//package matcha.exception.handler;
//
//import lombok.Data;
//import matcha.exception.context.UserAlreadyExistException;
//import matcha.exception.context.UserRegistrationException;
//import matcha.exception.context.image.ValidateAvatarInImagesException;
//import matcha.exception.db.GetProfileByIdDBException;
//import matcha.exception.db.image.UpdateImageByIdDBException;
//import matcha.exception.db.location.GetActiveLocationByLoginException;
//import matcha.exception.service.UserRegistryException;
//import matcha.exception.user.UserAuthException;
//import matcha.exception.user.UserLoginOrPasswordIncorrectException;
//import matcha.exception.user.UserNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.support.WebExchangeBindException;
//
////import org.springframework.web.bind.MissingRequestCookieException;
//
//@ControllerAdvice
//public class ProfileExceptionHandler /*extends ResponseEntityExceptionHandler*/ {
//
//    @ExceptionHandler({UserNotFoundException.class, UserAuthException.class, UserRegistryException.class,
//            UserAlreadyExistException.class, GetProfileByIdDBException.class, UserRegistrationException.class,
//            UserLoginOrPasswordIncorrectException.class, ValidateAvatarInImagesException.class})
//    protected ResponseEntity<AwesomeException> handleUserNotFoundException(Exception e) {
//        return new ResponseEntity<>(new AwesomeException(e.getMessage()), HttpStatus.OK);
//    }
//
//    @ExceptionHandler({GetActiveLocationByLoginException.class, UpdateImageByIdDBException.class})
//    protected ResponseEntity<AwesomeException> handleBadNewsException() {
//        return new ResponseEntity<>(new AwesomeException("Что-то явно пошло не по плану... Сообщите о случившемся кому-нибудь"), HttpStatus.OK);
//    }
//
//    @ExceptionHandler(WebExchangeBindException.class)
//    protected ResponseEntity<AwesomeException> handleCwookieException(BindException e) {
//        System.err.println(e);
//        return new ResponseEntity<>(new AwesomeException("Вы не авторизованы"), HttpStatus.OK);
//    }
//
//    @Data
//    private static class AwesomeException {
//        private String type = "error";
//        private final String message;
//    }
//}
