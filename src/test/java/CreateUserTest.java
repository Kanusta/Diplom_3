import clients.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import org.junit.After;
import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateUserTest {

    private User user;
    private UserClient userClient;
    private String userToken;

    @Before
    @Description("Создание уникального пользователя")
    public void setUp() {
        user = UserGenerator.getRandomUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Создание пользователя с корректными данными, код 200")
    public void createUser() {
        userToken = userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true))
                .extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован, ошибка 403")
    public void createDuplicateUser() {

        userClient.createUser(user)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));

        userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false))
                .and()
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без email, ошибка 403")
    public void createUserWithoutEmail() {
        user = UserGenerator.getUserWithoutEmail();
        userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false))
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без password, ошибка 403")
    public void createUserWithoutPassword() {
        user = UserGenerator.getUserWithoutPassword();
        userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false))
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без name, ошибка 403")
    public void createUserWithoutName() {
        user = UserGenerator.getUserWithoutName();
        userClient.createUser(user)
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .and()
                .body("success", is(false))
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    @Description("Удаление пользователя")
    public void deleteUser() {
        if (userToken != null) {
            userClient.deleteUser(userToken);
        }
    }
}