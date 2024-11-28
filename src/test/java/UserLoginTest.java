import clients.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import user.UserLogin;
import static org.hamcrest.CoreMatchers.*;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;

public class UserLoginTest {

    private User user;
    private UserClient userClient;
    private UserLogin userLogin;
    private String userToken;

    @Before
    @Description("Создание пользователя")
    public void setUp() {
        user = UserGenerator.getRandomUser();
        userClient = new UserClient();
        userToken = userClient.createUser(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Логин под существующим пользователем, код 200")
    public void loginUser() {
        userClient.authUser(UserLogin.withCorrectData(user))
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));
    }

    @Test
    @DisplayName("Логин с неверным email, код 401")
    public void loginWithErrorEmail() {
        userLogin = UserLogin.withErrorEmail(user);
        userClient.authUser(userLogin)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", is(false))
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин с неверным password, код 401")
    public void loginWithErrorPassword() {
        userLogin = UserLogin.withErrorPassword(user);
        userClient.authUser(userLogin)
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .and()
                .body("success", is(false))
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    @Description("Удаление пользователя")
    public void deleteUser() {
        if (userToken != null) {
            userClient.deleteUser(userToken);
        }
    }
}