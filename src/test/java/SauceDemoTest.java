import com.saucedemo.page_odject.CartPage;
import com.saucedemo.page_odject.HeaderPage;
import com.saucedemo.page_odject.InventoryPage;
import com.saucedemo.page_odject.loginPage;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.openqa.selenium.WebElement;
import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SauceDemoTest {

    ChromeDriver driver;
    loginPage loginPage;
    InventoryPage inventoryPage;

    Configurations configs;
    Configuration config;
    HeaderPage headerPage;
    CartPage cartPage;


    @BeforeMethod
    public void setUp() throws ConfigurationException {
        driver = new ChromeDriver();
       // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit waits явные-не рекомендует
        loginPage = new loginPage(driver);
        inventoryPage = new InventoryPage(driver);
        headerPage =new HeaderPage(driver);
        cartPage =new CartPage(driver);

        configs = new Configurations();
        config = configs.properties("config.properties");
        driver.get(config.getString("web.url"));

    }

    @Test
    public void openSauceDemoPageTest(){

         loginPage.authorize(config.getString("username"),config.getString("password"));

      Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void sauceDemoAddItemToTheCartTest(){

        loginPage.authorize(config.getString("username"), config.getString("password"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

        inventoryPage.selectItemByName("Backpack");
        inventoryPage.selectItemByName("Bike Light");
        assertThat(headerPage.getShoppingCartBadge().getText()).isEqualTo("2");

        headerPage.getShoppingCartLink().click();
        assertThat(cartPage.getCartItems().size()).isEqualTo(2);

        // Classic way of asserting by contains
        assertThat(cartPage.getCartItems().get(0).getText()).contains("Backpack");
        assertThat(cartPage.getCartItems().get(1).getText()).contains("Bike Light");

        // Functional programming style -> Handling partial matches
        assertThat(cartPage.getCartItems())
                .extracting(WebElement::getText)
                .anyMatch(text -> text.contains("Bike Light"));

        assertThat(cartPage.getCartItems())
                .extracting(WebElement::getText)
                .anyMatch(text -> text.contains("Backpack"));


    }
    @Test
    public void testLoginWithInvalidCredentials() {
        loginPage.authorize("incorrect_user", "incorrect_password");

        // Получаем текст ошибки
        String errorMessage = loginPage.getErrorMessage();

        // Выводим ошибку на экран
        System.out.println("Ошибка при входе: " + errorMessage);

        // Проверяем, что ошибка соответствует ожидаемой
        Assert.assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service");
    }

    @Test
    public void testLoginWithEmptyCredentials() {
        loginPage.authorize("", "");

        // Получаем текст ошибки
        String errorMessage = loginPage.getErrorMessage();

        // Выводим ошибку на экран
        System.out.println("Ошибка при входе: " + errorMessage);

        // Проверяем, что ошибка соответствует ожидаемой
        Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
    }

    @Test
    public void testLoginWithValidCredentials() {
        loginPage.authorize("standard_user", "secret_sauce");

        // Проверяем, что после успешного входа мы попадаем на страницу инвентаря
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }
    @Test
    public void invalidLoginTest() {
        // Используем данные из конфигурации для логина (например, с неправильными данными)
        String username = config.getString("username");
        String password = config.getString("password");

        // Чтобы проверить неправильный логин, просто измените данные в config.properties
        // Пытаемся авторизоваться с неверными данными
        loginPage.authorize(username, password);

        // Получаем текст ошибки
        String errorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";

        // Проверяем, что ошибка отображается
        Assert.assertNotNull(errorMessage, "Ошибка не отображается");
        Assert.assertEquals(errorMessage, expectedErrorMessage, "Сообщение об ошибке не совпадает");

        // Проверяем, что URL не изменился (мы остаемся на странице логина)
        Assert.assertEquals(driver.getCurrentUrl(), config.getString("web.url"));
    }


      @AfterMethod
     public void tearDown(){
      // driver.close();
         // driver.quit();
   }


}
