import Praktikum.AutorizPage;
import Praktikum.HomePage;
import Praktikum.RegisterUser;
import api.User;
import constant.Constants;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import userGenerator.UserGenerator;
import static org.junit.Assert.assertEquals;

public class RegisterUserTest {

    private WebDriver driver;
    User user;

    @Rule
    public DriverRule driverRule = new DriverRule();

    @Test
    @DisplayName("Регистрация нового пользователя")
    public void newUserRegister()  {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.clickLkButton();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.clickRegisterButtom();
        RegisterUser registerUser = new RegisterUser(driverRule.getDriver());
        user = new UserGenerator().randomUser();
        registerUser.fillUpName(user.getName());
        registerUser.fillUpEmail(user.getEmail());
        registerUser.fillUpPassword(user.getPassword());
        registerUser.clickRegisterButton();
        autorizPage.waitRedirectToLogin();
        autorizPage.login(user.getEmail(),user.getPassword());

    }

    @Test
    @DisplayName("Ошибкa для некорректного пароля. Минимальный пароль — шесть символов")
    public void checkRegisterPassword() {
        driver = driverRule.getDriver();
        driver.get(Constants.BASE_URL);
        HomePage homePage = new HomePage(driverRule.getDriver());
        homePage.clickLkButton();
        AutorizPage autorizPage = new AutorizPage(driverRule.getDriver());
        autorizPage.clickRegisterButtom();
        RegisterUser registerUser = new RegisterUser(driverRule.getDriver());
        User user = new UserGenerator().randomUser();
        user.setPassword("12345");
        registerUser.fillUpName(user.getName());
        registerUser.fillUpEmail(user.getEmail());
        registerUser.fillUpPassword(user.getPassword());
        registerUser.clickRegisterButton();
        String errorMessage = registerUser.checkPasswordError();
        assertEquals("Некорректный пароль",errorMessage);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
