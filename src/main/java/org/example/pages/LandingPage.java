package org.example.pages;

import io.qameta.allure.Allure;
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

    public UpcomingGamesPage loginApplication(String userEmail, String userPassword) {
        performLogin(userEmail, userPassword);
        return new UpcomingGamesPage(driver);
    }
    @Step("Clicking on \"Account\" icon")
    public void authSubmitClick(){
        authClickElement.click();
    }

    public void performLogin(String userEmail, String userPassword) {
        authSubmitClick();
        sendSensitiveData( userEmail,  userPassword);
        submitLogin();
    }

    public  void sendSensitiveData(String userEmail, String userPassword){
        emailElement.sendKeys(userEmail);
        passwordElement.sendKeys(userPassword);
    }

    @Step("Logging in with user email and password:")
    public void submitLogin(){
        submitButton.click();
    }

    public  void logMaskedSensitiveInfo(String userEmail, String userPassword) {
        String maskedEmail = maskEmail(userEmail);
        String maskedPassword = maskPassword(userPassword);
        Allure.step("Email: " + maskedEmail);
        Allure.step("Password: " + maskedPassword);
    }

    public String getEmail() {
        return emailElement.getAttribute("value");
    }

    public String getPassword() {
        return passwordElement.getAttribute("value");
    }
}