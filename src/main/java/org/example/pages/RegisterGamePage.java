package org.example.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.example.utils.AbstractComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.example.utils.Constants.*;

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

    @FindBy(id = "team-name")
    WebElement teamNameInput;

    @FindBy(css = "select")
    WebElement teamsDropdown;

    @FindBy(css = "select")
    List<WebElement> listOfTeams;

    @FindBy(xpath = "//img[contains(@src, 'plus')]")
    WebElement plusElement;

    @FindBy(xpath = "//button[text()='Далее']")
    WebElement furtherButton;

    @FindBy(css = "button[class*='reg-event-complete']")
    WebElement finalRegButton;

    @FindBy(id = "one")
    WebElement numberOfPlayers;

    public void fillPersonalData(String name, String phone, String email) {
        clearInputs();
        fillInputs(name, phone, email);
    }

    private void fillInputs(String name, String phone, String email) {
        fillInput(fioInput, name);
        fillInput(phoneInput, phone);
        fillInput(emailInput, email);
    }

    public void logMaskedSensitiveInfo(String userName, String userPhone, String userEmail) {
        String maskedPhone = maskPhone(userPhone);
        String maskedEmail = maskEmail(userEmail);
        Allure.step("Filling personal data:");
        Allure.step("Name: " + userName);
        Allure.step("Phone: " + maskedPhone);
        Allure.step("Email: " + maskedEmail);
    }

    @Step("Clicking to button \"Далее\"")
    public void clickFurtherButton() {
        furtherButton.click();
    }

    private void fillInput(WebElement inputField, String value) {
        if (value.isEmpty()) {
            inputField.sendKeys(" ");
            inputField.sendKeys(Keys.BACK_SPACE);
        } else {
            inputField.sendKeys(value);
        }
    }

    private void clearInputs() {
        fioInput.clear();
        phoneInput.clear();
        emailInput.clear();
    }

    @Step("Filling team data")
    public void fillTeamData(String teamName, int numberOfPlayers) {
        Select teamDropdown = new Select(teamsDropdown);
        if (!teamName.isEmpty()) {
            List<String> values = listOfTeams.stream()
                    .flatMap(element -> new Select(element).getOptions().stream())
                    .map(WebElement::getText)
                    .toList();

            if (values.contains(teamName)) {
                teamDropdown.selectByVisibleText(teamName);
            } else {
                selectCreateNewOption(teamDropdown, teamNameInput, teamName);
            }
        } else {
            selectCreateNewOption(teamDropdown, teamNameInput, " ");
            teamNameInput.sendKeys(Keys.BACK_SPACE);
        }

        clickPlusElement(numberOfPlayers - MINIMUM_NUMBER_OF_PLAYERS);
    }

    private void selectCreateNewOption(Select teamDropdown, WebElement teamNameInput, String teamName) {
        teamDropdown.selectByVisibleText(CREATE_NEW_OPTION);
        teamNameInput.sendKeys(teamName);
    }

    private void clickPlusElement(int numberOfClicks) {
        for (int i = 0; i < numberOfClicks; i++) {
            plusElement.click();
        }
    }

    @Step("Clicking on button \"Зарегистрировать\"" )
    public void registerTeam() {
        finalRegButton.click();
    }

    public WebElement getFinalRegButton() {
        return finalRegButton;
    }

    public String getFio() {
        return fioInput.getAttribute("value");
    }

    public String getPhone() {
        return phoneInput.getAttribute("value");
    }

    public String getEmail() {
        return emailInput.getAttribute("value");
    }

    public String getTeamName() {
        return teamNameInput.getAttribute("value");
    }

    public String getNumberOfPlayers() {
        return Arrays.stream(numberOfPlayers.getText().split(" ")).toList().get(0);
    }
}