import com.saucedemo.page_odject.loginPage;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SauceDemoTest {

    ChromeDriver driver;
    com.saucedemo.page_odject.loginPage loginPage;

    Configurations configs;
    Configuration config;



    @BeforeMethod
    public void setUp() throws ConfigurationException {
        driver = new ChromeDriver();
        loginPage = new loginPage(driver);
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
    public void addProductToTheCartTest(){

        loginPage.authorize(config.getString("username"), config.getString("password"));
        // Проверяем, что мы попали на страницу с товарами после логина
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);  // Добавим лог для отладки
        Assert.assertEquals(currentUrl, "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/inventory.html");
    }


    @AfterMethod
    public void tearDown(){
        driver.close();
        driver.quit();
    }


}
