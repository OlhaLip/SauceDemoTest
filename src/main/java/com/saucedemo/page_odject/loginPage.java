package com.saucedemo.page_odject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class loginPage {

    private WebDriver driver;

    //PageFactory
    @FindBy(id = "user-name")
    public WebElement usernameField;

    @FindBy(name ="password")
    public WebElement passwordField;

    @FindBy(xpath = "//input[@data-test='login-button']")
    public WebElement loginButton;

    public loginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void authorize(String username, String password){
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }


}
