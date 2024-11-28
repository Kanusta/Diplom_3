import clients.ClientOrder;
import clients.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import user.UserLogin;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static org.apache.hc.core5.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.*;
  public class GetOrderTest {

   private User user;
   private Order order;
   private UserClient userClient;
   private ClientOrder orderClient;
   private String userToken;

  @Before
  @Description("Создание пользователя")
  public void setUp() {
    user = UserGenerator.getRandomUser();
    order = new Order();
    userClient = new UserClient();
    orderClient = new ClientOrder();
    order = Order.createIngredients();
}

  @Test
  @DisplayName("Получение заказов авторизованного пользователя, код 200")
  public void getOrderWithAutorized() {
    userToken = userClient.createUser(user).extract().path("accessToken");
    userClient.authUser(UserLogin.withCorrectData(user));
    ValidatableResponse responseOrder = orderClient.createOrderWithAutorized(order, userToken);
    String nameBurger = responseOrder.extract().path("name");
    orderClient.getOrdersWithAutorized(userToken)
            .assertThat()
            .statusCode(SC_OK)
            .and()
            .body("success", is(true))
            .and()
            .body("orders[0].name", equalTo(nameBurger));
}

  @Test
  @DisplayName("Получение заказов неавторизованного пользователя, код 401")
   public void getOrderWithoutAutorized() {
    orderClient.createOrderWithoutAutorized(order);
    orderClient.getUserOrdersWithoutAutorized()
            .assertThat()
            .statusCode(SC_UNAUTHORIZED)
            .and()
            .body("success", is(false))
            .and()
            .body("message", equalTo("You should be authorised"));
}

  @After
  @Description("Удаление пользователя")
  public void deleteUser() {
    if (userToken != null) {
        userClient.deleteUser(userToken);
    }
}
    }
