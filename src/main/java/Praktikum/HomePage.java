package Praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    private By lkHeaderButton = By.xpath("//p[contains(@class, 'header') and contains(text(), 'Личный Кабинет')]");
    private By loginButton = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");
    private By orderButton = By.xpath("//button[contains(text(), 'Оформить')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        System.out.println("WebDriver инициализирован: " + (driver != null));
    }

    public void clickLkButton() {
        driver.findElement(lkHeaderButton).click();
    }

    public void goToLogin() {
        driver.findElement(loginButton).click();
    }

    public void waitRedirectToMain() {
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

}