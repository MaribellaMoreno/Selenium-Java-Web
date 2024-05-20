package steps.SauceDemoWeb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.SauceDemo.InventoryPage;
import pages.SauceDemo.ItemPage;
import pages.SauceDemo.LoginPage;
import steps.Hooks;


public class LoginDefinition {

	//private WebDriver driver; //ESTO NO VA, SE DEBE DECLARAR EL PAGE CON EL MISMO DRIVER DEL HOOKS!

	LoginPage login = new LoginPage(Hooks.driver);
	InventoryPage inventory = new InventoryPage(Hooks.driver);
	ItemPage item = new ItemPage(Hooks.driver);

	private String expectedCheckOutURL = "https://www.saucedemo.com/checkout-step-one.html"; 

	@Given("open the browser and navigate to {string}")
	public void openBrowserAndNavigateTo(String url) throws InterruptedException {
		login.navigateTo(url);
	}
	

	@And("login with username {string} and password {string}")
	public void login(String username, String password) throws Exception {
		login.sendUser(username);
		login.sendPassword(password);
		login.clickBtnLogin();
	}


	@When("search for {string} and add it to the cart")
	public void buscaAgregaItemAlCarrito(String item) throws Exception {
		inventory.addItemToCart(item);
		inventory.clickCart();   	        
	}
	

	@Then("proceed to checkout and buy the item")
	public void validaCheckout() throws Exception {
		item.clickCheckOutBtn();
		assertEquals("Redireccion erronea o no pudo seleccionar el elemento a comprar", expectedCheckOutURL, item.getURL());
		assertTrue("Formulario de 'Your information' no visible", item.isVisibleForm());
	}
	
	@Then("validate item {string} with price {string}")
	public void validaTituloPrecio(String name, String precio) throws Exception {
		assertTrue("No se encuentra el elemento " + name , inventory.isVisibleItemName(name));
		// Se puede validar el precio de otra forma, encontrando el elemento correspondiente, leyendo el texto del precio (getText) y formatear el dato para compararlo con el gherkin
		assertTrue("El precio del elemento no es igual a " + precio , inventory.isVisiblePriceName(name, precio));
	}
}








