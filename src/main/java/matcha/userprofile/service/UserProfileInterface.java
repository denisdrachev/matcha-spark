package matcha.userprofile.service;

import matcha.userprofile.model.UserProfileChat;

public interface UserProfileInterface {

    UserProfileChat getChatUserProfile(String login);
}
