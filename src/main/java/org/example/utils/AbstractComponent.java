package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractComponent {

    WebDriver driver;

    @FindBy(xpath = "//img[contains(@src, 'cross')]")
    WebElement closeCookie;

    @FindBy(css = "div[class*='Vue-Toastification__toast-body']")
    public WebElement errorMessage;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
    }

    public String maskPassword(String userPassword) {
        return userPassword.replaceAll(".", "*");
    }

    public String maskEmail(String userEmail) {
        int atIndex = userEmail.indexOf('@');
        if (atIndex > 3) {
            return userEmail.replaceAll("(?<=.{3}).(?=.*@)", "*");
        } else {
            return userEmail.substring(0, 1) + userEmail.substring(1, atIndex).replaceAll(".", "*") + userEmail.substring(atIndex);
        }
    }

    public String maskPhone(String userPhone) {
        return userPhone.replaceAll("(?<=\\+?\\d{3})\\d(?=\\d{2})", "*");
    }

    public void closeCookieAlert() {
        if (closeCookie.isEnabled() && closeCookie.isDisplayed()) {
            waitForWebElementToBeClickable(closeCookie);
            closeCookie.click();

        }
    }

    public String getErrorMessage() {
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    public WebElement waitForElementToAppear(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForWebElementToAppear(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitForWebElementToBeClickable(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }
}