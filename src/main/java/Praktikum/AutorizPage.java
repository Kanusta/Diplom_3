package Praktikum;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class AutorizPage {

    private WebDriver driver;

    private By fillLogin = By.name("name");
    private By fillPassword = By.name("Пароль");
    private By registerButtom = By.xpath("//a[contains(@class, 'Auth') and contains(text(), 'Зарегистрироваться')]");
    private By loginButton = By.xpath("//button[contains(text(), 'Войти')]");
    private By resetPasswordButton = By.xpath("//a[contains(text(), 'Восстановить')]");

    public AutorizPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Нажать кнопку Зарегистрироваться")
    public  void clickRegisterButtom() {
        driver.findElement(registerButtom).click();
    }

    @Step("Нажать кнопку Восстановить пароль")
    public void clickResetPassword() {
        driver.findElement(resetPasswordButton).click();
    }

    @Step("Заполнить поле Email")
    public void fillEmail(String login) {
        driver.findElement(fillLogin).sendKeys(login);
    }

    @Step("Заполнить поле Пароль")
    public void fillUpPassword(String password) {
        driver.findElement(fillPassword).sendKeys(password);
    }

    @Step("Войти в систему")
    public void login(String login, String password) {
        fillEmail(login);
        fillUpPassword(password);
        driver.findElement(loginButton).click();
    }

    @Step("Ожидание преавторизации")
    public void waitRedirectToLogin() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));

    }
}