package matcha.profile.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.image.service.ImageService;
import matcha.profile.service.ProfileService;
import matcha.response.Response;
import matcha.user.model.UserInfo;
import matcha.user.service.UserService;
import matcha.userprofile.model.UserInfoModel;
import matcha.validator.ValidationMessageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static spark.Spark.exception;
import static spark.Spark.post;

@Slf4j
//@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProfileController {

    private ValidationMessageService validationMessageService = ValidationMessageService.getInstance();
    private ProfileService profileService = ProfileService.getInstance();
    private UserService userService = UserService.getInstance();
    private ImageService imageService = ImageService.getInstance();

    public void init() {
        profileUpdate();
    }

    //    @PostMapping(value = "profile-update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void profileUpdate(/*@RequestBody UserInfoModel userProfile,
                                  @CookieValue(value = "token") String token*/
    ) {

        post("/profile-update", (req, res) -> {

            String token = req.cookie("token");

            if (token == null || token.isEmpty()) {
                return validationMessageService.prepareErrorMessage("Вы не авторизованы.");
            }

            UserInfoModel userProfile = new Gson().fromJson(req.body(), UserInfoModel.class);
            log.info("Request update user profile:{} token:{}", userProfile, token);

            Response response = validationMessageService.validateMessage(userProfile);
            if (response != null) {
                return response;
            }

            userService.checkUserByLoginAndActivationCode(userProfile.getLogin(), token);
            imageService.checkImagesIsCorrect(userProfile.getImages());
            userService.saveUserInfo(userProfile);
            return validationMessageService.prepareMessageOkOnlyType();
        });

        exception(Exception.class, (exception, request, response) -> {
            response.body(validationMessageService.prepareErrorMessage(exception.getMessage()).toString());
        });



    }
}
