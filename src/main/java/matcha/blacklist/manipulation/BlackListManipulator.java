package matcha.blacklist.manipulation;

import lombok.extern.slf4j.Slf4j;
import matcha.blacklist.db.BlackListDB;
import matcha.blacklist.model.BlackListMessage;
import matcha.exception.db.SelectDBException;

import java.util.List;

@Slf4j
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
        } catch (SelectDBException e) {
            return new BlackListMessage(toLogin, fromLogin, false);
        }
    }

    public boolean isBlackListExists(String fromLogin, String toLogin) {
        return blackListDB.isBlackListExists(fromLogin, toLogin);
    }

    public List<BlackListMessage> getAllBlackList() {
        return blackListDB.getAllBlackList();
    }
}
