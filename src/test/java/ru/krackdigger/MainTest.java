package ru.krackdigger;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    RemoteWebDriver driver;

    @BeforeEach
    public void initDriver() throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "My test");
            put("sessionTimeout", "15m");
            // put("screenResolution", "1920x1080x24");
            // put("enableVNC", true);
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            put("enableVideo", true);
        }});
        driver = new RemoteWebDriver(new URL("http://194.67.119.85:4444/wd/hub"), options);
        // driver.manage().window().setSize(new Dimension(1920,1080));
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterEach
    public void stopDriver() {
        driver.quit();
    }

    @Test
    public void simpleTest() {
        open("https://tatyana-aygi.ru/gallery");
        Configuration.holdBrowserOpen = true;
        $(By.id("user_login")).setValue("selenide");
        $(By.id("user_pass")).setValue("junit5");
        $(By.id("btn_login")).click();
        $(By.id("wow-modal-close-1")).click();
        $(By.id("al_2")).setSelected(true);
        $(By.id("fl_3")).setSelected(true);
        $(By.id("pr_4")).setSelected(true);
        $(By.id("user_name")).scrollIntoView(true).setValue("test_name");
        $(By.id("phone_number")).setValue("8-911-111-11-11");
        $(By.id("e_mail")).setValue("mail@mail.com");
        $(By.name("message")).setValue("Test message");
        $(By.id("btn_id_main")).click();
        $(By.id("btn_go")).click();
        String txt = $(By.id("txt_result")).getText().substring(0, 37);
        assertEquals(txt, "Ваш заказ на сумму 400 ₽ сформирован!");
    }
}
