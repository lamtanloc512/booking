package org.hrsgroup;

import com.owlike.genson.Genson;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hrsgroup.model.Booking;
import org.hrsgroup.util.JwtBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {
    private static Vertx vertx = Vertx.vertx();

    private static final String keystorePath = System.getProperty("user.dir")
            + "/target/liberty/wlp/usr/servers/"
            + "defaultServer/resources/security/key.p12";

    static String urlBooking;
    static String urlHotel;
    static String token;

    @BeforeAll
    public static void setup() throws Exception {
        String URL_BASE = "http://localhost"
                + ":" + System.getProperty("http.port")
                + "/api/v1";
        urlBooking = URL_BASE + "/bookings";
        urlHotel = URL_BASE + "/hotels";
        token = createJwt("booking_user", Set.of("user", "admin"));

    }

    @Test
    public void testGetBookings() {
        Response response = makeGetRequest(urlBooking, "");
        System.out.println(response);
        assertEquals(200, response.getStatus(),
                "Incorrect response code from " + urlBooking);

    }

    @Test
    public void testGetBookingById() {
        String urlBookingById = urlBooking + "/201";
        Response response = makeGetRequest(urlBookingById, "");
        System.out.println(response);
        assertEquals(200, response.getStatus(),
                "Incorrect response code from " + urlBookingById);

    }

//    @Test
//    public void testUpdateBookingById() {
//        String urlBookingById = urlBooking + "/351";
//        Response response = null;
//        try (Client client = ClientBuilder.newClient()) {
//            Invocation.Builder builder = client.target(urlBookingById).request();
//            builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
//            if (token != null) {
//                builder.header(HttpHeaders.AUTHORIZATION, token);
//            }
//
//            System.out.println(token);
//            String hotel = new Genson().serialize(
//                    Json.createObjectBuilder()
//                            .add("id", "54")
//                            .build()
//            );
//            String user = new Genson().serialize(
//                    Json.createObjectBuilder()
//                            .add("id", "120")
//                            .build()
//            );
//
//            String serialize = new Genson().serialize(
//                    Json.createObjectBuilder()
//                            .add("hotel", hotel)
//                            .add("user", user)
//                            .add("rooms", "[]")
//                            .build()
//            );
//            response = builder.put(Entity.json(serialize));
//        }
//        System.out.println(response);
//        assertEquals(200, response.getStatus(),
//                "Incorrect response code from " + urlBookingById);
//
//    }

    @Test
    public void testDeleteBookingById() {
        String urlBookingById = urlBooking + "/201";
        Response response = makeDeleteRequest(urlBookingById, token);
        assertEquals(200, response.getStatus(),
                "Incorrect response code from " + urlBookingById);

    }

    private Response makeGetRequest(String url, String authHeader) {
        try (Client client = ClientBuilder.newClient()) {
            System.out.println(url);
            jakarta.ws.rs.client.Invocation.Builder builder = client.target(url).request();
            builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            if (authHeader != null) {
                builder.header(HttpHeaders.AUTHORIZATION, authHeader);
            }
            return builder.get();
        }
    }

    private Response makePostRequest(String url, String authHeader) {
        try (Client client = ClientBuilder.newClient()) {
            jakarta.ws.rs.client.Invocation.Builder builder = client.target(url).request();
            builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            if (authHeader != null) {
                builder.header(HttpHeaders.AUTHORIZATION, authHeader);
            }
            return builder.get();
        }
    }

    private Response makeDeleteRequest(String url, String authHeader) {
        try (Client client = ClientBuilder.newClient()) {
            jakarta.ws.rs.client.Invocation.Builder builder = client.target(url).request();
            builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            if (authHeader != null) {
                builder.header(HttpHeaders.AUTHORIZATION, authHeader);
            }
            return builder.get();
        }
    }


    private static String createJwt(String username, Set<String> groups) throws IOException {
        JWTAuthOptions config = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("RS256")
                        .setBuffer(getPrivateKey()));
        JWTAuth provider = JWTAuth.create(vertx, config);
        io.vertx.core.json.JsonObject claimsObj = new JsonObject()
                .put("exp", (System.currentTimeMillis() / 1000) + 300)  // Expire time
                .put("iat", (System.currentTimeMillis() / 1000))        // Issued time
                .put("jti", Long.toHexString(System.nanoTime()))        // Unique value
                .put("sub", username)                                   // Subject
                .put("upn", username)                                   // Subject again
                .put("iss", "localhost")
                .put("aud", "user")
                .put("groups", getGroupArray(groups));

        return provider.generateToken(claimsObj,
                new JWTOptions().setAlgorithm("RS256"));
    }

    private static String getPrivateKey() throws IOException {
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            char[] password = new String("secret").toCharArray();
            keystore.load(new FileInputStream(keystorePath), password);
            Key key = keystore.getKey("default", password);
            String output = "-----BEGIN PRIVATE KEY-----\n"
                    + Base64.getEncoder().encodeToString(key.getEncoded()) + "\n"
                    + "-----END PRIVATE KEY-----";
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static JsonArray getGroupArray(Set<String> groups) {
        JsonArray arr = new JsonArray();
        if (groups != null) {
            for (String group : groups) {
                arr.add(group);
            }
        }
        return arr;
    }

}
