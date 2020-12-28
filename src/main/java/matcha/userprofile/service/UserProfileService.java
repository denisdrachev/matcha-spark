package matcha.userprofile.service;

import lombok.extern.slf4j.Slf4j;
import matcha.profile.db.ProfileDB;
import matcha.profile.model.ProfileEntity;
import matcha.user.db.UserDB;
import matcha.user.model.UserEntity;
import matcha.userprofile.model.UserProfileChat;

@Slf4j
public class UserProfileService implements UserProfileInterface {

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
                .images(profileById.getImages())
                .build();
    }
}
