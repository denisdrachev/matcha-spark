package matcha.event.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class EventControllerTest {

    @Test
    void getHistory() throws ClientProtocolException, IOException {
        // Given
//        String name = RandomStringUtils.randomAlphabetic(8);
        HttpUriRequest request = new HttpGet("http://localhost:4567/history?limit=10&offset=0");

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // Then
        String responseString = new BasicResponseHandler().handleResponse(httpResponse);
        System.out.println(responseString);
        HashMap<String, String> hashMap = new Gson().fromJson(responseString, HashMap.class);

        for (Map.Entry<String, String> stringStringEntry : hashMap.entrySet()) {
            System.err.println(stringStringEntry.getKey() + " " + stringStringEntry.getValue());
        }

        assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND, responseString);
    }

    @Test
    void getNotifications() {
    }

    @Test
    void likeUser() {
    }
}