Feature: Comprar un item en el sitio web 
	
  @testSauceDemo
  Scenario: Usuario compra un item ( o al menos lo intenta ...)
    Given open the browser and navigate to "https://www.saucedemo.com"
    And login with username "standard_user" and password "secret_sauce"
    When search for "Bike Light" and add it to the cart
    Then proceed to checkout and buy the item
    
  @otherTest @se_podria_dejar_como_outline
  Scenario: Usuario valida item y precio en el home
    Given open the browser and navigate to "https://www.saucedemo.com"
    And login with username "standard_user" and password "secret_sauce"
    Then validate item "Bolt T-Shirt" with price "$15.90"  
    # precio real $15.99