package matcha.blacklist.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import matcha.blacklist.manipulation.BlackListManipulator;
import matcha.blacklist.model.BlackListMessage;
import matcha.image.service.ImageService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class BlackListService {

    private BlackListManipulator blackListManipulator = new BlackListManipulator();

    private static BlackListService blackListService;

    public static BlackListService getInstance() {
        if (blackListService == null) {
            blackListService = new BlackListService();
        }
        return blackListService;
    }

    public BlackListMessage getBlackListMessage(String fromLogin, String toLogin) {
        return blackListManipulator.getBlackListMessage(fromLogin, toLogin);
    }

    public boolean isBlackListExists(String fromLogin, String toLogin) {
        return blackListManipulator.isBlackListExists(fromLogin, toLogin);
    }

    public void updateBlackListMessage(BlackListMessage message) {
        blackListManipulator.updateBlackListMessage(message);
    }

    public void insertBlackListMessage(BlackListMessage message) {
        blackListManipulator.insertBlackListMessage(message);
    }
}
