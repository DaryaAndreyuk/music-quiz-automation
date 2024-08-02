package org.example.pages;

import io.qameta.allure.Step;
import org.example.utils.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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

    @FindBy(xpath = "//div[contains(@class, 'p-5')]")
    List<WebElement> upcomingGames;

    By buttonForRegisterLocator = By.xpath("//button[contains(text(), 'Зарегистрироваться')]");

    @Step("Clicking on button \"Сыграть\"")
    public void getUpcomingGamesList() {
        //waitForElementToAppear(buttonForRegisterLocator);
        // WebElement mainMozgoQuizElementbutton = gameElement.findElement(buttonForRegisterLocator);
        waitForWebElementToBeClickable(mainMozgoQuizElement);
        Actions actions = new Actions(driver);
        actions.moveToElement(mainMozgoQuizElement).click().perform();


        //waitForWebElementToBeClickable(mainMozgoQuizElement);
        //mainMozgoQuizElement.click();
        closeCookieAlert();
        waitForWebElementToBeClickable(playButton);
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(playButton).click().perform();

        //playButton.click();
    }

    @Step("Choosing game with type {0}")
    public WebElement getGameByType(String gameType) {
        return upcomingGames.stream().filter(game ->
                game.findElement(By.cssSelector("p")).getText().contains(gameType)).findFirst().orElse(null);
    }

    @Step("Clicking on button \"Зарегистрироваться\"")
    public RegisterGamePage clickOnRegisterButton(WebElement gameElement) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonForRegisterLocator));

        WebElement button = gameElement.findElement(buttonForRegisterLocator);

        wait.until(ExpectedConditions.elementToBeClickable(button));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

        return new RegisterGamePage(driver);
    }
}
