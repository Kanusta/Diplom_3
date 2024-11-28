import clients.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import user.UserLogin;
import java.util.Locale;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.*;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserUpdateTest {

private User user;
private UserClient userClient;
private String userToken;
private User newUser;

@Before
@Description("Создание пользователя")
public void setUp() {
    user = UserGenerator.getRandomUser();
    userClient = new UserClient();
    userToken = userClient.createUser(user).extract().path("accessToken");
    newUser = UserGenerator.getRandomUser();
}

@Test
@DisplayName("Изменение данных пользователя с авторизацией")
public void editUserWithAutorized() {
    userClient.authUser(UserLogin.withCorrectData(user));
    userClient.editUser(newUser, userToken)
            .assertThat()
            .statusCode(SC_OK)
            .and()
            .body("success", is(true))
            .and()
            .body("user.email", equalTo(newUser.getEmail().toLowerCase(Locale.ROOT)))
            .and()
            .body("user.name", equalTo(newUser.getName()));
}

@Test
@DisplayName("Изменение данных пользователя без авторизации, код 401")
public void editUserWithoutAutorized() {
    userClient.editUserWithoutAuth(newUser)
            .assertThat()
            .statusCode(SC_UNAUTHORIZED)
            .and()
            .body("message", equalTo("You should be authorised"))
            .and()
            .body("success", is(false));
}

@After
@Description("Удаление пользователя")
public void deleteUser() {
    if (userToken != null) {
        userClient.deleteUser(userToken);
    }
}
}