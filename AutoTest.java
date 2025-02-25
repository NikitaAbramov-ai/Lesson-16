package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.PaymentPage;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private WebDriver driver;
    private PaymentPage paymentPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://mts.by");
        paymentPage = new PaymentPage(driver);
    }

    @Test
    public void testServiceFieldsPlaceholders() {
        String[] services = {"Услуги связи", "Домашний интернет", "Рассрочка", "Задолженность"};

        for (String service : services) {
            paymentPage.selectServiceType(service);
            assertEquals("Введите номер телефона", paymentPage.getCardDetailsPlaceholder());
        }
    }

    @Test
    public void testPaymentProcessForServices() {
        paymentPage.selectServiceType("Услуги связи");

        paymentPage.clickContinue();

        assertNotNull(paymentPage.getDisplayedAmount());
        assertNotNull(paymentPage.getDisplayedPhoneNumber());

        assertEquals("Введите реквизиты карты", paymentPage.getCardDetailsPlaceholder());

        assertTrue(driver.findElement(By.id("visaIcon")).isDisplayed());
        assertTrue(driver.findElement(By.id("masterCardIcon")).isDisplayed());
    }
}
