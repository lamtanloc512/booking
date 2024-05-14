package org.hrsgroup.util;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Set;

public class JwtBuilder {
    private static final String keystorePath = System.getProperty("user.dir") + "/resources/security/key.p12";

    public static String buildJwt(String username, Set<String> group) throws IOException {
        JWTAuthOptions config = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("RS256")
                        .setBuffer(getPrivateKey()));
        Vertx vertx = Vertx.vertx();
        JWTAuth provider = JWTAuth.create(vertx, config);
        io.vertx.core.json.JsonObject claimsObj = new JsonObject()
                .put("exp", (System.currentTimeMillis() / 1000) + 300)  // Expire time// Unique value
                .put("iat", (System.currentTimeMillis() / 1000))        // Issued time
                .put("jti", Long.toHexString(System.nanoTime()))        // Unique value
                .put("iss", "localhost")
                .put("sub", username)                         // Subject
                .put("upn", username)
                .put("aud", "user")
                .put("groups", getGroupArray(group));

        return provider.generateToken(claimsObj, new JWTOptions().setAlgorithm("RS256"));
    }


    private static String getPrivateKey() {
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            char[] password = "secret".toCharArray();
            keystore.load(new FileInputStream(keystorePath), password);
            Key key = keystore.getKey("default", password);
            return "-----BEGIN PRIVATE KEY-----\n"
                    + Base64.getEncoder().encodeToString(key.getEncoded()) + "\n"
                    + "-----END PRIVATE KEY-----";

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
