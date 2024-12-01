import Praktikum.AutorizPage;
import Praktikum.ConstructorPage;
import Praktikum.HomePage;
import Praktikum.LkUserPage;
import api.User;
import api.UserClient;
import constant.Constants;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import userGenerator.UserGenerator;
import static org.junit.Assert.assertEquals;

public class ConstructorTest {
    private WebDriver driver;
    private String accessToken;
    User user;
    UserClient userClient = new UserClient();

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Before
    public void createTestUser() {
        user = new UserGenerator().randomUser();
        accessToken = userClient.userCreate(user);

    }

    @Test
    @DisplayName("Переход  разделам ")
    public void constructurTest()  {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.clickLkButton();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.login(user.getEmail(), user.getPassword());
        homePage.clickLkButton();
        LkUserPage lkUserPage = new LkUserPage(driverRule.getDriver());
        lkUserPage.getName();
        assertEquals(user.getName(), lkUserPage.getName());
        assertEquals(user.getEmail(), lkUserPage.getLogin());
        lkUserPage.clickConstructor();
        ConstructorPage сonstructorPage = new ConstructorPage(driverRule.getDriver());
        сonstructorPage.clickButtonSauces();
        сonstructorPage.clickButtonIngredients();
        сonstructorPage.clickButtonBuns();
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void fromLkToHomePage() {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.clickLkButton();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.login(user.getEmail(), user.getPassword());
        homePage.clickLkButton();
        LkUserPage lkUserPage = new LkUserPage(driverRule.getDriver());
        lkUserPage.getName();
        assertEquals(user.getName(), lkUserPage.getName());
        assertEquals(user.getEmail(), lkUserPage.getLogin());
        lkUserPage.clickConstructor();

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
