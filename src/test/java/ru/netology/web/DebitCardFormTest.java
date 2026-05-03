package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DebitCardFormTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupALL() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFormTestV1() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name] input"))).sendKeys("Иванов Иван");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79270000000");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=agreement]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button_view_extra"))).click();
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".Success_successBlock__2L3Cw")));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }
}