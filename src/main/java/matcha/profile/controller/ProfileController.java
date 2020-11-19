package matcha.profile.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.image.service.ImageService;
import matcha.profile.service.ProfileService;
import matcha.response.Response;
import matcha.user.service.UserService;
import matcha.userprofile.model.UserInfoModel;
import matcha.validator.ValidationMessageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProfileController {

    private ValidationMessageService validationMessageService;
    private ProfileService profileService;
    private UserService userService;
    private ImageService imageService;

    @PostMapping(value = "profile-update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response profileUpdate(@RequestBody UserInfoModel userProfile,
                                  @CookieValue(value = "token") String token
    ) {
        log.info("Request update user profile:{} token:{}", userProfile, token);
        Response response = validationMessageService.validateMessage(userProfile);
        if (response != null) {
            return response;
        }

        userService.checkUserByLoginAndActivationCode(userProfile.getLogin(), token);
        imageService.checkImagesIsCorrect(userProfile.getImages());
        userService.saveUserInfo(userProfile);
        return validationMessageService.prepareMessageOkOnlyType();
    }
}
