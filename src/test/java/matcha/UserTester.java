package matcha;

import com.google.gson.Gson;
import matcha.image.model.Image;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTester {

    public HttpResponse registrationUser(String login, Double x, Double y) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:4567/register");

        Map<String, Double> location = new HashMap<>();
        location.put("x", x);
        location.put("y", y);

        Map<String, Object> data = new HashMap<>();
        data.put("login", login);
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
        return httpResponse;

        // Then
//        String responseString = new BasicResponseHandler().handleResponse(httpResponse);
//
//        HashMap hashMap = new Gson().fromJson(responseString, HashMap.class);
//        assertEquals("ok", hashMap.get("type"));
    }

    public HttpResponse loginUser(String login, Double x, Double y) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:4567/login");

        Map<String, Double> location = new HashMap<>();
        location.put("x", x);
        location.put("y", y);

        Map<String, Object> data = new HashMap<>();
        data.put("login", login);
        data.put("password", "test");
        data.put("location", location);

        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();

        basicHttpEntity.setContent(new ByteArrayInputStream(new Gson().toJsonTree(data).toString().getBytes()));
        httpPost.setEntity(basicHttpEntity);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);

        return httpResponse;

        // Then
//        String responseString = new BasicResponseHandler().handleResponse(httpResponse);
//        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode(), responseString);
//
//        Map<String, String> hashMap = new Gson().fromJson(responseString, HashMap.class);
//
//        assertEquals("ok", hashMap.get("type"));
//        assertFalse(hashMap.get("token").isEmpty());
//
//        test2token = hashMap.get("token");
//        System.err.println(test2token);
    }

    public HttpResponse updateUserProfile(String token, String login, Double x, Double y, List<String> tags) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:4567/profile-update");
        httpPost.setHeader("Authorization", token);

        /*{
  "login": "test2",
  "fname":"first name",
  "lname":"last name",
  "password": "test",
  "email": "email@gmail.com",
  "age": 55,
  "gender": 1,
  "preference": [0, 1],
  "biography": "my name is Jack",
  "tags": [
    "aaa",
    "bbb"
  ],
  "images": [{
        "index": 0,
        "src": "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png",
        "avatar": true
    },
    {
        "index": 1,
        "src": "https://images.thenorthface.com/is/image/TheNorthFaceEU/CF8C_15Q_hero?$638x745$",
        "avatar": false
    }
  ],
      "location": null
}*/

        Image image = new Image();
        image.setAvatar(true);
        image.setSrc("https://i.ibb.co/cw9TnX9/0f6339673fed.jpg");
        image.setIndex(1);

        Map<String, Double> location = new HashMap<>();
        location.put("x", x);
        location.put("y", y);

        Map<String, Object> data = new HashMap<>();
        data.put("login", login);
        data.put("fname", "first name");
        data.put("lname", "last name");
        data.put("biography", "test");
        data.put("preference", List.of(0, 1));
        data.put("password", "test");
        data.put("gender", 1);
        data.put("age", 52);
        data.put("email", "email@mail.ru");
        data.put("location", location);
        data.put("tags", tags);
        data.put("images", List.of(image));

        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();

        basicHttpEntity.setContent(new ByteArrayInputStream(new Gson().toJsonTree(data).toString().getBytes()));
        httpPost.setEntity(basicHttpEntity);

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);

        return httpResponse;

        // Then
//        String responseString = new BasicResponseHandler().handleResponse(httpResponse);
//        assertEquals(HttpStatus.SC_OK, httpResponse.getStatusLine().getStatusCode(), responseString);
//
//        Map<String, String> hashMap = new Gson().fromJson(responseString, HashMap.class);
//
//        assertEquals("ok", hashMap.get("type"));
//        assertFalse(hashMap.get("token").isEmpty());
//
//        test2token = hashMap.get("token");
//        System.err.println(test2token);
    }

    @Test
    void getUsers(String token) throws ClientProtocolException, IOException {
        // Given
//        String name = RandomStringUtils.randomAlphabetic(8);
        HttpUriRequest request = new HttpGet("http://localhost:4567/get-users?tags=tag2,tag4&ageMin=0&ageMax=100&minRating=0&maxRating=999&deltaRadius=1000&limit=10&offset=0");
        request.setHeader("Authorization", token);
        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        String responseString = new BasicResponseHandler().handleResponse(httpResponse);
        System.out.println(responseString);
        HashMap hashMap = new Gson().fromJson(responseString, HashMap.class);

        System.err.println(hashMap);
//        for (Map.Entry<String, String> stringStringEntry : hashMap.entrySet()) {
//            System.err.println(stringStringEntry.getKey() + " " + stringStringEntry.getValue());
//        }

        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK, responseString);
    }

}
