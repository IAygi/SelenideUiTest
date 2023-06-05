package ru.krackdigger;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainTest {

    @BeforeEach
    public void initDriver() throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "My test");
            put("sessionTimeout", "1m");
            put("screenResolution", "1920x1080x24");
//            put("enableVNC", true);
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            put("enableVideo", true);
        }});
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://194.67.119.85:4444/wd/hub"), options);
        driver.manage().window().setSize(new Dimension(1920,1080));
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterEach
    public void stopDriver() {
        Optional.of(WebDriverRunner.getWebDriver()).ifPresent(WebDriver::quit);
    }

    @Test
    public void userCanLoginByUsername() {
        open("https://google.com");
        $(By.name("q")).setValue("text").pressEnter();
//        $("#submit").click();
//        $(".loading_progress").should(disappear); // Само подождёт, пока элемент исчезнет
//        $("#username").shouldHave(text("Hello, Johny!")); // Само подождёт, пока у элемента появится нужный текст
    }
}
