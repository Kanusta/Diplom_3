package api;

import constant.Constants;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class UserClient {

    public void userDelete(String token) {
        String response = given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL)
                .header("Authorization", token)
                .when()
                .delete(Constants.LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(HTTP_ACCEPTED)
                .body("success", is(true))
                .extract()
                .path("message");
        assertEquals(response, "User successfully removed");
    }

    public String userCreate(User user) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL)
                .body(user)
                .when()
                .post(Constants.REGISTER)
                .then().log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
                .extract()
                .path("accessToken");
    }
}