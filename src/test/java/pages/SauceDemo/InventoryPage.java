package pages.SauceDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.WebBasePage;

public class InventoryPage extends WebBasePage {

    public InventoryPage (WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this); 
    }

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement bikeLight;

    public String XPATH_ADD_TO_CART_ITEM_BTN = "//div[@class='inventory_item_description' and contains(.//div,'%s')]//button";
    public String XPATH_ALT = "//div[@class='inventory_item_label' and contains(.,'Bike Light')]/ancestor::div[@class='inventory_item_description']//button";

    public String XPATH_ITEM_TITLE = "//div[@data-test='inventory-item-name' and contains(.,'%s')]";
    public String XPATH_ITEM_PRICE = "//div[@data-test='inventory-item-name' and contains(.,'%s')]/ancestor::div[@class='inventory_item_description']" + 
                                    "//div[@class='inventory_item_price' and contains(.,'%s')]";

    @FindBy(className = "shopping_cart_link")
    private WebElement iconCart;
    
    public void addToCartBikeLight() throws Exception{
        clickToElement(bikeLight);
    }

    public void addItemToCart(String item) throws Exception{
        By elementBy = By.xpath(String.format(XPATH_ADD_TO_CART_ITEM_BTN, item));
        //driver.findElement(By.xpath(String.format(XPATH_ADD_TO_CART_ITEM_BTN, item))).click();
        clickToElement(elementBy);
    }

    public void clickCart() throws Exception{
        clickToElement(iconCart);
    }

    public boolean isVisibleItemName(String name){
        By elementBy = By.xpath(String.format(XPATH_ITEM_TITLE, name));
        return isVisible(elementBy, 10);
    }

    public boolean isVisiblePriceName(String name, String price){
        By elementBy = By.xpath(String.format(XPATH_ITEM_PRICE, name, price));
        return isVisible(elementBy, 10);
    }

}
