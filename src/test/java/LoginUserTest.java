import Praktikum.*;
import api.User;
import api.UserClient;
import constant.Constants;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.junit.After;
import userGenerator.UserGenerator;
import static org.junit.Assert.assertEquals;

public class LoginUserTest {
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
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginByButtonHomePage()  {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.clickLkButton();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.login(user.getEmail(), user.getPassword());
        homePage.waitRedirectToMain();

    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void loginByButtonLK() {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.clickLkButton();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.login(user.getEmail(), user.getPassword());
        homePage.waitRedirectToMain();

    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void loginInRegistrationForm()  {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.clickLkButton();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.clickRegisterButtom();
        RegisterUser registerUser = new RegisterUser(driverRule.getDriver());
        registerUser.clickLogin();
        autorizPage.login(user.getEmail(), user.getPassword());
        homePage.waitRedirectToMain();

    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginBYRecoveryForm()  {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.clickLkButton();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.clickResetPassword();
        RestorePasswordPage restorePasswordPage = new RestorePasswordPage(driverRule.getDriver());
        restorePasswordPage.clickLogin();
        autorizPage.login(user.getEmail(), user.getPassword());
        homePage.waitRedirectToMain();

    }

    @Test
    @DisplayName("Выход из Личного кабинета")
    public void exitLK()  {
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
        lkUserPage.clickLogout();

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
}
}