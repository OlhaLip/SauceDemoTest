package com.saucedemo.page_odject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private  WebDriver driver;
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
       // this.inventoryItemsList = inventoryItemsList;
        PageFactory.initElements(driver,this);
    }

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItemsList;

    private final By inventoryButton = By.xpath(".//button[contains(@class, 'btn_inventory')]");

    // Метод для выбора товара по имени
    public void selectItemByName(String itemName) {
        // Перебираем все товары на странице
        for (WebElement item : inventoryItemsList) {
            // Проверяем, содержит ли текст товара нужное имя
            if (item.getText().contains(itemName)) {
                // Находим кнопку для этого товара и кликаем по ней
                item.findElement(inventoryButton).click();
                System.out.println("Товар выбран по имени: " + itemName);
                break; // Прерываем цикл после нахождения и клика на товар
            }
        }
    }


    }




