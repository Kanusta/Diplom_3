package Praktikum;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {

    private final WebDriver driver;
    private final By bunsButton = By.xpath("//span[text()='Булки']//parent::div");
    private final By saucesButton = By.xpath("//span[text()='Соусы']//parent::div");
    private final By ingredientsButton = By.xpath("//span[text()='Начинки']//parent::div");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
    }


    public void clickButtonBuns(){
        driver.findElement(bunsButton).click();
    }

    public void clickButtonSauces(){
        driver.findElement(saucesButton).click();
    }

    public void clickButtonIngredients(){
        driver.findElement(ingredientsButton).click();
    }

}