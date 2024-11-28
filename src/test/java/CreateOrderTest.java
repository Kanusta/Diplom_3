import clients.ClientOrder;
import clients.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import order.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import user.UserLogin;
import static org.apache.hc.core5.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

public class CreateOrderTest {

    private User user;
    private Order order;
    private UserClient userClient;
    private ClientOrder clientOrder;
    private String userToken;

    @Before
    @Description("Создание пользователя")
    public void setUp() {
        user = UserGenerator.getRandomUser();
        order = new Order();
        userClient = new UserClient();
        clientOrder = new ClientOrder();
    }

    @Test
    @DisplayName("Создание заказа с ингридиентами авторизованным пользователем, код 200")
    public void createSuccessOrderWithAutorized() {
        order = Order.createIngredients();
        userToken = userClient.createUser(user).extract().path("accessToken");
        userClient.authUser(UserLogin.withCorrectData(user));
        clientOrder.createOrderWithAutorized(order, userToken)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true))
                .and()
                .body("order.number", notNullValue())
                .and()
                .body("order._id", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAutorized() {
        order = Order.createIngredients();
        clientOrder.createOrderWithoutAutorized(order)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", is(true));

    }

    @Test
    @DisplayName("Создание заказа без ингредиентов, код 400")
    public void createOrderWithoutIngredients() {
        clientOrder.createOrderWithoutAutorized(order)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("success", is(false))
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов, код 500")
    public void orderCreateWithInvalidHashIngredients() {
        order = Order.createIngredientsWithErrorHash();
        clientOrder.createOrderWithoutAutorized(order)
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    @Description("Удаление пользователя")
    public void deleteUser() {
        if (userToken != null) {
            userClient.deleteUser(userToken);
        }
    }
}