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
    private WebDriverWait wait;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldFormTestV1() {
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name] input"))).sendKeys("Иванов Иван");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79270000000");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=agreement]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button_view_extra"))).click();
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=order-success]")));
        assertTrue(result.isDisplayed());
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", result.getText().trim());
    }

    @Test
    void shouldShowErrorForInvalidName() {
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name] input"))).sendKeys("Ivanov Ivan");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79270000000");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=agreement]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button_view_extra"))).click();
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name].input_invalid .input__sub")));
        assertTrue(result.isDisplayed());
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", result.getText().trim());
    }

    @Test
    void shouldShowErrorForEmptyName() {
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name] input"))).sendKeys("");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79270000000");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=agreement]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button_view_extra"))).click();
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name].input_invalid .input__sub")));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void shouldShowErrorForInvalidPhone() {
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name] input"))).sendKeys("Иванов Иван");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=phone] input"))).sendKeys("00000");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=agreement]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button_view_extra"))).click();
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")));
        assertTrue(result.isDisplayed());
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", result.getText().trim());
    }

    @Test
    void shouldShowErrorForEmptyPhone() {
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name] input"))).sendKeys("Иванов Иван");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=phone] input"))).sendKeys("");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=agreement]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button_view_extra"))).click();
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")));
        assertTrue(result.isDisplayed());
        assertEquals("Поле обязательно для заполнения", result.getText().trim());
    }

    @Test
    void shouldShowErrorForUncheckedAgreement() {
        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=name] input"))).sendKeys("Иванов Иван");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test-id=phone] input"))).sendKeys("+79270000000");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".button_view_extra"))).click();
        WebElement result = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test-id=agreement].input_invalid")));
        assertTrue(result.isDisplayed());
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", result.getText().trim());
    }
}