package org.jbl;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import jakarta.ws.rs.core.MediaType;
import org.jbl.UserDomain.User;
import org.jbl.UserDomain.UserRequest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusIntegrationTest
class UserResourceIT {
    // Execute the same tests but in packaged mode.

   @Test
   void testUserEndpointsIT() {
      var user = new User(
         "john",
         "john@doe.com", "Testvägen 1, 12345 Farsta",
         "+465555555");
      var user2 = new User(
         "johan",
         "johan@doe.com", "Festvägen 2, 12345 Farsta",
         "+46666664444");

      int PORT = 8081;

      given()
         .port(PORT)
         .contentType(MediaType.APPLICATION_JSON)
         .body(new UserRequest(user))
         .when().post("/user")
         .then()
         .statusCode(204);

      given()
         .port(PORT)
         .contentType(MediaType.APPLICATION_JSON)
         .body(new UserRequest(user2))
         .when().post("/user")
         .then()
         .statusCode(204);

      var allUsers = given()
         .port(PORT)
         .when().get("/user")
         .then()
         .statusCode(200)
         .extract().jsonPath().getList("users", User.class);

      assertThat(allUsers, hasSize(2));
      assertThat(allUsers, containsInRelativeOrder(user, user2));

      var paged1 = given()
         .port(PORT)
         .queryParam("page",1)
         .queryParam("pageSize",1)
         .when().get("/user/page")
         .then()
         .statusCode(200)
         .extract().jsonPath().getList("users", User.class);

      assertThat(paged1, hasSize(1));
      assertThat(paged1, contains(user));

      var paged2 = given()
         .port(PORT)
         .queryParam("page",2)
         .queryParam("pageSize",1)
         .when().get("/user/page")
         .then()
         .statusCode(200)
         .extract().jsonPath().getList("users", User.class);

      assertThat(paged2, hasSize(1));
      assertThat(paged2, contains(user2));

      var paged3 = given()
         .port(PORT)
         .when().get("/user/page")
         .then()
         .statusCode(200)
         .extract().jsonPath().getList("users", User.class);

      assertThat(paged3, hasSize(2));
      assertThat(paged3, containsInRelativeOrder(user, user2));
   }

}
