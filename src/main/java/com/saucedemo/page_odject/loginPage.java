package com.saucedemo.page_odject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class loginPage {

        WebDriverWait wait;

    private WebDriver driver;

    //PageFactory
    @FindBy(id = "user-name")
    public WebElement usernameField;

    @FindBy(name ="password")
    public WebElement passwordField;

    @FindBy(xpath = "//input[@data-test='login-button']")
    public WebElement loginButton;

    @FindBy(css ="h3[data-test='error']")
    private WebElement errorMessage;


    public loginPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//        this.driver = driver;
//        PageFactory.initElements(driver,this);
    }

    public void authorize(String username, String password){
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(loginButton)); //explicit waits

        loginButton.click();
        // Если элемент ошибки отображается, выводим текст
//        if (errorMessage.isDisplayed()) {
//            System.out.println("Ошибка авторизации: " + errorMessage.getText());
//        }
    }
    // Метод для получения текста ошибки, если он есть
    public String getErrorMessage() {
        if (errorMessage.isDisplayed()) {
            return errorMessage.getText();
        }
        return null;  // Возвращаем null, если ошибка не отображается
    }


}
