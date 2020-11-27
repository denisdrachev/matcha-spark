package matcha;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserTest {

    String login = "test3";
    String test2token;

    UserTester userTester = new UserTester();

    @Test
    public void createUser() throws IOException {
//        UserTester
        userTester.registrationUser(login, 1.1, 1.1);
    }

    @Test
    public void loginUser() throws IOException {
//        UserTester
        HttpResponse httpResponse = userTester.loginUser(login, 1.1, 1.1);

        String responseString = new BasicResponseHandler().handleResponse(httpResponse);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode(), responseString);

        Map<String, String> hashMap = new Gson().fromJson(responseString, HashMap.class);

        assertEquals("ok", hashMap.get("type"));
        assertFalse(hashMap.get("token").isEmpty());

        test2token = hashMap.get("token");
        System.err.println(test2token);
    }


    @Test
    public void updateProfileUser(Double x, Double y) throws IOException {
//        UserTester

        HttpResponse httpResponse = userTester.updateUserProfile(test2token, login, x, y, List.of("tag2", "tag4"));

        String responseString = new BasicResponseHandler().handleResponse(httpResponse);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode(), responseString);

        Map<String, String> hashMap = new Gson().fromJson(responseString, HashMap.class);

        assertEquals("ok", hashMap.get("type"));
//        assertFalse(hashMap.get("token").isEmpty());
//
//        test2token = hashMap.get("token");
//        System.err.println(test2token);
    }

    @Test
    public void getUsers() throws IOException {
//        UserTester

        userTester.getUsers(test2token);
    }

    @Test
    void scena() throws IOException {
        try {
            createUser();
        } catch (Exception e) {
            System.err.println("Пользователь уже существует");
        }
        loginUser();
        updateProfileUser(1.41, 1.23);
        getUsers();
    }


}
