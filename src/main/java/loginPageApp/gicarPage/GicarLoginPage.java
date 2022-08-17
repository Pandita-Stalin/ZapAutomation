package loginPageApp.gicarPage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import tools.Waiting;

import java.util.logging.Logger;

public class GicarLoginPage {

    Logger logger = Logger.getLogger(GicarLoginPage.class.getName());

    @FindBy (css = "#user")
    protected WebElement userInput;

    public WebElement getUserInput() {
        return userInput;
    }
    @FindBy(css = "#password")
    protected WebElement passwordInput;

    public WebElement getPasswordInput() {
        return passwordInput;
    }
    @FindBy(css = "#Login > div:nth-child(3) > input")
    protected WebElement accessButton;

    public WebElement getAccessButton() {
        return accessButton;
    }

    /****************************************************************/

    public void elementDisplayed(WebElement expectedElement){
     if (expectedElement.isDisplayed()){
         Assert.assertTrue(expectedElement.isDisplayed());
     }else{
         logger.severe("ERROR!!! = no hemos encontrado el elemento " + expectedElement);
         System.err.println("ERROR!!! = no hemos encontrado el elemento " + expectedElement);
         System.exit(0);
     }
    }

    private void completeTheLoginPart(String loginName, String gicarPass){
        userInput.sendKeys("CREDENCIALES");
        passwordInput.sendKeys("CREDENCIALES");
        accessButton.click();
        Waiting.time(5000);
    }

    public void setCompleteLoginPart(String loginName, String gicarPass){
        completeTheLoginPart(loginName,gicarPass);
    }
}
