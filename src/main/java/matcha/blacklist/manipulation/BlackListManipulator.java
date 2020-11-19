package matcha.blacklist.manipulation;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.db.BlackListDB;
import matcha.blacklist.model.BlackListMessage;
import matcha.exception.db.NotFoundBlackListMessageDBException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RequiredArgsConstructor
@NoArgsConstructor
public class BlackListManipulator {

    private final BlackListDB blackListDB = new BlackListDB();

    public void insertBlackListMessage(BlackListMessage blackListMessage) {
        blackListDB.insertBlackListMessage(blackListMessage);
    }

    public void updateBlackListMessage(BlackListMessage blackListMessage) {
        blackListDB.updateBlackListMessage(blackListMessage);
    }

    public BlackListMessage getBlackListMessage(String fromLogin, String toLogin) {
        try {
            return blackListDB.getBlackListMessage(fromLogin, toLogin);
        } catch (NotFoundBlackListMessageDBException e) {
            return new BlackListMessage(toLogin, fromLogin, false);
        }
    }

    public boolean isBlackListExists(String fromLogin, String toLogin) {
        return blackListDB.isBlackListExists(fromLogin, toLogin);
    }
}
