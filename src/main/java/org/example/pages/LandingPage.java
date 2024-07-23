package org.example.pages;

import io.qameta.allure.Step;
import org.example.utils.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {

    WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class, 'p-1 cursor-pointer')]/div[contains(@class, 'border-white')]")
    WebElement authClickElement;

    @FindBy(id = "email")
    WebElement emailElement;

    @FindBy(id = "password")
    WebElement passwordElement;

    @FindBy(xpath = "//button[text()='Войти']")
    WebElement submitButton;

    @Step("Logging in with user email and password")
    public UpcomingGamesPage loginApplication(String userEmail, String userPassword) {
        authClickElement.click();
        emailElement.sendKeys(userEmail);
        passwordElement.sendKeys(userPassword);
        submitButton.click();
        return new UpcomingGamesPage(driver);
    }

    public String getEmail() {
        return emailElement.getAttribute("value");
    }

    public String getPassword() {
        return passwordElement.getAttribute("value");
    }
}