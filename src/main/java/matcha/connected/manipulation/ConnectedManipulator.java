package matcha.connected.manipulation;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matcha.connected.db.ConnectedDB;
import matcha.connected.model.ConnectedEntity;
import matcha.connected.model.ConnectedWithUserInfo;
import matcha.exception.db.NotFoundBlackListMessageDBException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
//@Service
//@RequiredArgsConstructor
//@NoArgsConstructor
public class ConnectedManipulator {

    private final ConnectedDB connectedDB = new ConnectedDB();

    public void insertConnected(ConnectedEntity blackListMessage) {
        connectedDB.insertConnected(blackListMessage);
    }

    public void updateConnected(ConnectedEntity connectedEntity) {
        connectedDB.updateConnected(connectedEntity);
    }

    public ConnectedEntity getConnected(String fromLogin, String toLogin) {
        ConnectedEntity connected = connectedDB.getConnected(fromLogin, toLogin);
        return connected != null ? connected : new ConnectedEntity(toLogin, fromLogin, false);
    }

    public boolean isConnectedExist(String fromLogin, String toLogin) {
        return connectedDB.getConnected(fromLogin, toLogin) != null;
    }

    public List<ConnectedEntity> getAllConnected() {
        return connectedDB.getAllConnected();
    }

    public List<ConnectedWithUserInfo> getAllConnectedWithUser(String login) {
        return connectedDB.getAllConnectedWithUser(login);
    }
}