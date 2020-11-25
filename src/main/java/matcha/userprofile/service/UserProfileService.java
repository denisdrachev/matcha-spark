package matcha.userprofile.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.event.service.EventService;
import matcha.profile.db.ProfileDB;
import matcha.profile.model.ProfileEntity;
import matcha.user.db.UserDB;
import matcha.user.model.UserEntity;
import matcha.userprofile.model.UserProfileChat;
import org.springframework.stereotype.Service;

@Slf4j
//@Service
//@AllArgsConstructor
public class UserProfileService implements UserProfileInterface {

    //TODO РЕФАКТОРИНГ! ТУДУ ТУДУ!

    private UserDB userDB = new UserDB();
    private ProfileDB profileDB = new ProfileDB();

    private static UserProfileService userProfileService;

    public static UserProfileService getInstance() {
        if (userProfileService == null) {
            userProfileService = new UserProfileService();
        }
        return userProfileService;
    }

    @Override
    public UserProfileChat getChatUserProfile(String login) {
        UserEntity userByLogin = userDB.getUserByLogin(login);
        ProfileEntity profileById = profileDB.getProfileById(userByLogin.getProfileId());
        return UserProfileChat.builder()
                .login(userByLogin.getLogin())
                .fname(userByLogin.getFname())
                .lname(userByLogin.getLname())
                .gender(profileById.getGender())
//                .tags(profileById.getTags())
                .images(profileById.getImages())
                .build();
    }


}
