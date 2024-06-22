package org.example.pageobjects;

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

    @FindBy(css = "a[href='/account/games']")
    WebElement accountGamesElement;

    @FindBy(css = "a[href='/#upcoming-games']")
    WebElement upcomingGamesElement;

    @FindBy(css = "div[class='p-5 relative item']")
    List<WebElement> upcomingGames;

    By accountGamesLocator = By.cssSelector("a[href='/account/games']");
    By buttonForRegisterLocator = By.cssSelector(".reg-event-btn");

    public List<WebElement> getUpcomingGamesList() {
        waitForElementToAppear(accountGamesLocator);
        accountGamesElement.click();
        upcomingGamesElement.click();
        return upcomingGames;
    }

    public WebElement getGameByType(String gameType) {
        return upcomingGames.stream().filter(game ->
                game.findElement(By.cssSelector("p")).getText().contains(gameType)).findFirst().orElse(null);
    }

    public RegisterGamePage clickOnRegisterButton(WebElement gameElement){
        WebElement button = gameElement.findElement(buttonForRegisterLocator);
        waitForElementToAppear(buttonForRegisterLocator);
        button.click();
        return new RegisterGamePage(driver);
    }

}