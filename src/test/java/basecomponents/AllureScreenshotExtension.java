package basecomponents;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;

public class AllureScreenshotExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isPresent()) {
            Object testInstance = context.getRequiredTestInstance();

            if (testInstance instanceof BaseTest test) {
                WebDriver driver = test.getDriver();
                if (driver != null) {
                    Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
                } else {
                    System.out.println("WebDriver не инициализирован или сессия завершена");
                }
            } else {
                System.out.println("Не удалось получить экземпляр тестового класса BaseTest");
            }
        }
    }
}