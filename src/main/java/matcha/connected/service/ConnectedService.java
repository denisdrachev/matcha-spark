package matcha.connected.service;

import matcha.connected.manipulation.ConnectedManipulator;
import matcha.connected.model.ConnectedEntity;
import matcha.connected.model.ConnectedWithUserInfo;

import java.util.List;

public class ConnectedService {

    private ConnectedManipulator connectedManipulator = new ConnectedManipulator();

    private static ConnectedService connectedService;

    public static ConnectedService getInstance() {
        if (connectedService == null) {
            connectedService = new ConnectedService();
        }
        return connectedService;
    }

    public ConnectedEntity getConnected(String fromLogin, String toLogin) {
        return connectedManipulator.getConnected(fromLogin, toLogin);
    }

    public boolean isConnectedExist(String fromLogin, String toLogin) {
        return connectedManipulator.isConnectedExist(fromLogin, toLogin);
    }

    public void updateConnected(ConnectedEntity connected) {
        connectedManipulator.updateConnected(connected);
    }

    public void insertConnected(ConnectedEntity connected) {
        connectedManipulator.insertConnected(connected);
    }

    public void saveConnected(ConnectedEntity connected) {
        if (isConnectedExist(connected.getFromLogin(), connected.getToLogin())) {
            updateConnected(connected);
        } else {
            insertConnected(connected);
        }
    }

    public List<ConnectedEntity> getAllConnected() {
        return connectedManipulator.getAllConnected();
    }

    public List<ConnectedWithUserInfo> getAllConnectedWithUser(String login) {
        return connectedManipulator.getAllConnectedWithUser(login);
    }
}
