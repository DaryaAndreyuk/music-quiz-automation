package org.example.pageobjects;

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

    @FindBy(css = "button[class*='hover-up-mod']")
    WebElement submitElement;

    public UpcomingGamesPage loginApplication(String userEmail, String userPassword) {
        authClickElement.click();
        emailElement.sendKeys(userEmail);
        passwordElement.sendKeys(userPassword);
        submitElement.click();
        return new UpcomingGamesPage(driver);
    }
}