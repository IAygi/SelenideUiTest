package ru.krackdigger;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.codeborne.selenide.Condition.image;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private RemoteWebDriver driver;

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = "https://tatyana-aygi.ru";
        // Configuration.holdBrowserOpen = true;
    }

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

//    @AfterAll
//    public static void cleanUp() {
//        System.out.println("After All cleanUp() method called");
//    }

    @Test
    @Tag("main_page")
    public void mainPage() {
        step("Открыть главную страницу", () -> {
            open("/");
        });
        step("Проверить заголовок страницы", () -> {
            String title = $(By.className("page-title")).getText();
            assertEquals(title, "Фотограф Татьяна Айги");
        });
        step("Проверить наличие изображения в главной галерее", () -> {
            $(By.className("swiper-slide-image")).shouldHave(image);
        });
        step("Проверить наличие подгалереи", () -> {
            assert ($(By.className("foogallery")).isDisplayed());
        });
    }

    @Test
    @Tag("children_page")
    public void childrenPage() {
        step("Открыть галерею \"Дети\"", () -> {
            open("/children");
        });
        step("Проверить заголовок страницы", () -> {
            String title = $(By.className("page-title")).getText();
            assertEquals(title, "Дети");
        });
        step("Проверить наличие галереи", () -> {
            assert ($(By.className("elementor-image-gallery")).isDisplayed());
        });
    }

    @Test
    @Tag("create_order")
    public void createOrder() {
        step("Открыть галерею", () -> {
            open("/gallery");
        });
        step("Ввести логин и пароль", () -> {
            $(By.id("user_login")).setValue("selenide");
            $(By.id("user_pass")).setValue("junit5");
            $(By.id("btn_login")).click();
        });
        step("Закрыть модальное окно", () -> {
            $(By.id("wow-modal-close-1")).click();
        });
        step("Выбрать фотографии", () -> {
            $(By.id("al_2")).setSelected(true);
            $(By.id("fl_3")).setSelected(true);
            $(By.id("pr_4")).setSelected(true);
        });
        step("Заполнить форму", () -> {
            $(By.id("user_name")).scrollIntoView(true).setValue("test_name");
            $(By.id("phone_number")).setValue("8-911-111-11-11");
            $(By.id("e_mail")).setValue("mail@mail.com");
            $(By.name("message")).setValue("Test message");
        });
        step("Перейти на экран подтверждения выбора", () -> {
            $(By.id("btn_id_main")).click();
        });
        step("Подтвердить выбор", () -> {
            $(By.id("btn_go")).click();
        });
        step("Проверка создания заказа", () -> {
            String txt = $(By.id("txt_result")).getText().substring(0, 37);
            assertEquals(txt, "Ваш заказ на сумму 400 ₽ сформирован!");
        });
    }
}
