package pages.SauceDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.WebBasePage;

public class LoginPage extends WebBasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this); 
    }

    @FindBy(id = "user-name")
    private WebElement usernameID;

    @FindBy(id = "password")
    private WebElement passwordID;

    @FindBy(id = "login-button")
    private WebElement btnLoginID;

    public void sendUser(String name) throws Exception{
        sendKeys(usernameID, name);
    }

    public void sendPassword(String pass) throws Exception{
        sendKeys(passwordID, pass);
    }

    public void clickBtnLogin() throws Exception{
        clickToElement(btnLoginID);
    }

    public void navigateTo(String url) {
		goToUrl(url);
	}



}
