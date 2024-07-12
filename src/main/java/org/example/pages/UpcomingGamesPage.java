package org.example.pages;

import org.example.utils.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UpcomingGamesPage extends AbstractComponent {

    WebDriver driver;

    public UpcomingGamesPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[text()=' Сыграть ']")
    WebElement playButton;

    @FindBy(css = "img[src='/img/logo/mq_logo_black.svg']")
    WebElement mainMozgoQuizElement;

    @FindBy(css = "div[class='p-5 relative item']")
    List<WebElement> upcomingGames;

    By buttonForRegisterLocator = By.cssSelector(".reg-event-btn");

    public void getUpcomingGamesList() {
        waitForWebElementToBeClickable(mainMozgoQuizElement);
        mainMozgoQuizElement.click();
        playButton.click();
    }

    public WebElement getGameByType(String gameType) {
        return upcomingGames.stream().filter(game ->
                game.findElement(By.cssSelector("p")).getText().contains(gameType)).findFirst().orElse(null);
    }

    public RegisterGamePage clickOnRegisterButton(WebElement gameElement) {
        closeCookieAlert();
        WebElement button = gameElement.findElement(buttonForRegisterLocator);
        waitForElementToAppear(buttonForRegisterLocator);
        waitForWebElementToBeClickable(button);
        button.click();
        return new RegisterGamePage(driver);
    }
}
