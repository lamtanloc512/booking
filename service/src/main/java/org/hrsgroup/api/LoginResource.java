package org.hrsgroup.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hrsgroup.dto.LoginDto;
import org.hrsgroup.dto.ResponseDto;
import org.hrsgroup.util.JwtBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Set;


@Path("/login")
@Tag(name = "Login API")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @POST
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getJwt(LoginDto login) throws Exception {
        ResponseDto.ResponseDtoBuilder responseDtoBuilder = ResponseDto.builder();

        if (login.getUsername().equals("booking_user")
                && login.getPassword().equals("booking_pwd")) {
            String token = JwtBuilder.buildJwt(login.getUsername(), Set.of("user", "admin"));
            responseDtoBuilder
                    .code(200)
                    .message("Login successful!")
                    .data(token);

            return Response.ok(responseDtoBuilder.build()).build();
        }
        responseDtoBuilder.message("Bad username | password")
                .code(403)
                .data(null);

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(responseDtoBuilder.build())
                .build();
    }


}
