package Praktikum;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RestorePasswordPage {

    private WebDriver driver;

    private By signInButton = By.xpath("//a[contains(text(), 'Войти')]");

    public RestorePasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажать кнопку Войти")
    public void clickLogin() {
        driver.findElement(signInButton).click();
    }
}



