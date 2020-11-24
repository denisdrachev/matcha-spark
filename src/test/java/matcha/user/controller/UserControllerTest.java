package matcha.user.controller;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserControllerTest {

    private String test2token;



    @Test
    void registration() throws IOException {

        HttpPost httpPost = new HttpPost("http://localhost:4567/register");

        Map<String, Double> location = new HashMap<>();
        location.put("x", 1.6);
        location.put("y", 1.9);

        Map<String, Object> data = new HashMap<>();
        data.put("login", "test2");
        data.put("password", "test");
        data.put("email", "diqjwdjdoi2@mail.ru");
        data.put("fname", "test");
        data.put("lname", "test");
        data.put("location", location);

        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();

        basicHttpEntity.setContent(new ByteArrayInputStream(new Gson().toJsonTree(data).toString().getBytes()));
        httpPost.setEntity(basicHttpEntity);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);

        // Then
        String responseString = new BasicResponseHandler().handleResponse(httpResponse);

        Map<String, String> hashMap = new Gson().fromJson(responseString, HashMap.class);
        assertEquals("ok", hashMap.get("type"));

//
//        for (Map.Entry<String, String> stringStringEntry : hashMap.entrySet()) {
//            System.err.println(stringStringEntry.getKey() + " " + stringStringEntry.getValue());
//            if (stringStringEntry.getKey().equals("type")) {
//
//            }
//        }

        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode(), responseString);
    }

    @Test
    void login() throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:4567/login");

        Map<String, Double> location = new HashMap<>();
        location.put("x", 1.6);
        location.put("y", 1.9);

        Map<String, Object> data = new HashMap<>();
        data.put("login", "test2");
        data.put("password", "test");
        data.put("location", location);

        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();

        basicHttpEntity.setContent(new ByteArrayInputStream(new Gson().toJsonTree(data).toString().getBytes()));
        httpPost.setEntity(basicHttpEntity);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);


        // Then
        String responseString = new BasicResponseHandler().handleResponse(httpResponse);
        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode(), responseString);

        Map<String, String> hashMap = new Gson().fromJson(responseString, HashMap.class);

        assertEquals("ok", hashMap.get("type"));
        assertFalse(hashMap.get("token").isEmpty());

        test2token = hashMap.get("token");
        System.err.println(test2token);
    }

    @Test
    void getUserProfile() {
    }

    @Test
    void getUserProfileSelf() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void logout() {
    }

    @Test
    void confirmRegistration() {
    }
}