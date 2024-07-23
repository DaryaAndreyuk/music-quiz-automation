import basecomponents.BaseTest;
import io.qameta.allure.Description;
import org.example.pages.RegisterGamePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorValidationTest extends BaseTest {


    @Test
    @Tag("negative")
    @DisplayName("Verify error message for invalid login credentials")
    @Description("This test attempts to log into the website using a login and a password")
    public void loginErrorValidation() {
        performLoginAndCheckError("wrong.email@gmail.com", "Stronbbbssword1!", "Учётные данные не найдены в базе.");
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty username")
    public void emptyUserNameErrorValidation() {
        validatePersonalDataError("", "375294999632", "dsfd@fgfds.com", "Заполните, пожалуйста, все необходимые поля.");
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty phone")
    public void emptyPhoneErrorValidation() {
        validatePersonalDataError("TestName", "", "dsfd@fgfds.com", "Заполните, пожалуйста, все необходимые поля.");
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty email")
    public void emptyEmailErrorValidation() {
        validatePersonalDataError("TestName", "375294999632", "", "Заполните, пожалуйста, все необходимые поля.");
    }

    @ParameterizedTest
    @Tag("negative")
    @DisplayName("Verify error message for invalid email format")
    @ValueSource(strings = {
            "plainaddress",
            "missingatsymbol.com"
    })
    void emailFormatErrorValidation(String email) {
        validatePersonalDataError("dfdsl", "375294999632", email, "Email неверного формата.");
    }

    @ParameterizedTest
    @Tag("negative")
    @DisplayName("Verify error message for invalid phone format")
    @ValueSource(strings = {"1", "1234", "12345678"})
    void phoneFormatErrorValidation(String phoneNumber) {
        validatePersonalDataError("dfdsl", phoneNumber, "dsfd@fgfds.com", "Укажите полноценный номер телефона.");
    }

    @Test
    @Tag("negative")
    @DisplayName("Verify error message for empty team name")
    public void emptyTeamNameErrorValidation() {
        RegisterGamePage registerGamePage = initializeRegisterPage();
        registerGamePage.fillPersonalData("ggjs", "375294999632", "dsfd@fgfds.com");
        registerGamePage.clickFurtherButton();
        registerGamePage.fillTeamData("", 5);
        validateError("Заполните, пожалуйста, все необходимые поля.", registerGamePage);
    }

    private void validatePersonalDataError(String name, String phone, String email, String expectedErrorMessage) {
        RegisterGamePage registerGamePage = initializeRegisterPage();
        registerGamePage.fillPersonalData(name, phone, email);
        validateError(expectedErrorMessage, registerGamePage);
    }

    private void validateError(String expectedErrorMessage, RegisterGamePage registerGamePage) {
        registerGamePage.clickFurtherButton();
        assertEquals(expectedErrorMessage, registerGamePage.getErrorMessage());
    }
}