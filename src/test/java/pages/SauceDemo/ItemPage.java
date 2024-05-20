package pages.SauceDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.WebBasePage;

public class ItemPage extends WebBasePage{
    public ItemPage (WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this); 
    }

    @FindBy(id = "checkout")
    private WebElement checkOutBtn;

    @FindBy(css = ".checkout_info")
    private WebElement formulario;


    public void clickCheckOutBtn() throws Exception{
        clickToElement(checkOutBtn);
    }

    public String getURL(){
        return getCurrentURL();
    }

    public boolean isVisibleForm(){
        return isVisible(formulario, 5);
    }
}
