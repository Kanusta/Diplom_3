import Praktikum.AutorizPage;
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

public class LkUserTest {

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
    @DisplayName("Переход по клику на «Личный кабинет»")
    public void goToCabinet() {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.goToLogin();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.login(user.getEmail(), user.getPassword());
        homePage.clickLkButton();
        LkUserPage lkUserPage = new LkUserPage(driverRule.getDriver());
        lkUserPage.getName();
        assertEquals(user.getName(), lkUserPage.getName());
        assertEquals(user.getEmail(), lkUserPage.getLogin());
    }

    @Test
    @DisplayName("Переход по клику на логотип")
    public void goToMainByLogo() {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.goToLogin();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.login(user.getEmail(),user.getPassword());
        homePage.clickLkButton();
        LkUserPage lkUserPage = new LkUserPage(driverRule.getDriver());
        lkUserPage.getName();
        assertEquals(user.getName(),lkUserPage.getName());
        assertEquals(user.getEmail(),lkUserPage.getLogin());
        lkUserPage.clickLogo();

    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
}}