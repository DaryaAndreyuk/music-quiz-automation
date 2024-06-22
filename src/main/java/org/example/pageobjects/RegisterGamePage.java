package org.example.pageobjects;

import org.example.utils.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import static org.example.utils.Constants.MINIMUM_NUMBER_OF_PLAYERS;

public class RegisterGamePage extends AbstractComponent {
    WebDriver driver;

    public RegisterGamePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "fio")
    WebElement fioInput;

    @FindBy(id = "phone")
    WebElement phoneInput;

    @FindBy(id = "email")
    WebElement emailInput;

    @FindBy(css = "select[class*='py-2']")
    WebElement teamsDropdown;

    @FindBy(xpath = "//div[@class='flex justify-between items-center pt-2']//div[2]")
    WebElement plusElement;

    @FindBy(css = "div[class='flex mb-0 w-full'] button[class*='cursor-pointer']")
    WebElement furtherButton;

    @FindBy(css = "button[class*='reg-event-complete']")
    WebElement finalRegButton;

    public void fillPersonalData(String name, String phone, String email) {
        if (fioInput.getAttribute("value").isEmpty())
            fioInput.sendKeys(name);

        if (phoneInput.getAttribute("value").isEmpty())
            phoneInput.sendKeys(phone);

        if (emailInput.getAttribute("value").isEmpty())
            emailInput.sendKeys(email);

        furtherButton.click();
    }

    public void fillTeamData(String teamName, int numberOfPlayers) {
        Select teamDropdown = new Select(teamsDropdown);
        teamDropdown.selectByVisibleText(teamName);
        int numberOfClicks = numberOfPlayers - MINIMUM_NUMBER_OF_PLAYERS;
        for (int i = 0; i < numberOfClicks; i++)
            plusElement.click();
        furtherButton.click();
    }

    public void registerTeam() {
        finalRegButton.click();
    }
}
