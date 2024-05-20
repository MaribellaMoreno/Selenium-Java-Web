package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebBasePage {

    protected static final int DEFAULT_WAIT_TIMEOUT = 20;
    protected static final int POLLING_TIME = 1;
    protected static final int MAX_TRIES = 5;
    public static int TIMEOUT = 30;
    
    protected WebDriver driver;
    protected final WebDriverWait wait;

    protected WebBasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT), Duration.ofSeconds(POLLING_TIME));
		//PageFactory.initElements(new AjaxElementLocatorFactory(driver, DEFAULT_WAIT_TIMEOUT), this);
    }

    protected WebDriver getDriver(){
        return driver;
    }

    protected void goToUrl(String url) {
		driver.get(url);
	}

    protected String getCurrentURL(){
        return driver.getCurrentUrl();
    }

    protected static void waitExplicit(int seconds){
		try{
			Thread.sleep(seconds);
			
		}catch (InterruptedException ignored){
		}
	}

    protected void waitUntilElementIsVisible(By element) throws Exception{
		try{
			this.wait.withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT))
				.until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch(TimeoutException e){
			throw new TimeoutException(String.format(" No se encuentra el elemento despues de " + DEFAULT_WAIT_TIMEOUT + "segundos\nElemento: %s",element));
		}
	}

    protected void waitUntilElementIsVisible(WebElement element) throws Exception{
		try{
			this.wait.withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT))
				.until(ExpectedConditions.visibilityOf(element));
		} catch(TimeoutException e){
			throw new TimeoutException(String.format(" No se encuentra el elemento despues de " + DEFAULT_WAIT_TIMEOUT + "segundos\nElemento: %s",element));
		}
	}

    protected void waitUntilElementIsVisible(By element, int WAIT_TIMEOUT) throws Exception{
		try{
			this.wait.withTimeout(Duration.ofSeconds(WAIT_TIMEOUT))
				.until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch(TimeoutException e){
			throw new TimeoutException(String.format(" No se encuentra el elemento despues de " + WAIT_TIMEOUT + "segundos\nElemento: %s",element));
		}
	}

    protected void waitUntilElementIsVisible(WebElement element, int WAIT_TIMEOUT) throws Exception{
		try{
			this.wait.withTimeout(Duration.ofSeconds(WAIT_TIMEOUT))
				.until(ExpectedConditions.visibilityOf(element));
		} catch(TimeoutException e){
			throw new TimeoutException(String.format(" No se encuentra el elemento despues de " + WAIT_TIMEOUT + "segundos\nElemento: %s",element));
		}
	}

    protected void waitUntilElementIsVisibleNonError(By element, int WAIT_TIMEOUT){
		try{
			this.wait.withTimeout(Duration.ofSeconds(WAIT_TIMEOUT))
				.until(ExpectedConditions.visibilityOfElementLocated(element));
		}
		catch (Exception e){
		}
	}

    protected void waitUntilElementIsVisibleNonError(WebElement element, int WAIT_TIMEOUT){
		try{
			this.wait.withTimeout(Duration.ofSeconds(WAIT_TIMEOUT))
				.until(ExpectedConditions.visibilityOf(element));
		}
		catch (Exception e){
		}
	}

    protected boolean isVisible(By elementBy, int time){
        try{
            this.wait.withTimeout(Duration.ofSeconds(time))
				.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
            return true;
        }
        catch(TimeoutException toe){
            return false;
        }
    }

    protected boolean isVisible(WebElement element, int time){
        try{
            this.wait.withTimeout(Duration.ofSeconds(time))
				.until(ExpectedConditions.visibilityOf(element));
            return true;
        }
        catch(TimeoutException toe){
            return false;
        }
    }

    protected void waitUntilElementIsClickeable(WebElement element){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (TimeoutException e){
			throw new TimeoutException(String.format("El elemento %s no es clickeble", element));
		}
	}

    protected void waitUntilElementIsClickeable(By element){
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (TimeoutException e){
			throw new TimeoutException(String.format("El elemento %s no es clickeble", element));
		}
	}

    protected void clickToElement (By element) throws Exception{
		waitUntilElementIsClickeable(element);
        getDriver().findElement(element).click();
	}

    protected void clickToElement (WebElement element) throws Exception{
		waitUntilElementIsClickeable(element);
		element.click();
	}
    
    protected void sendKeys(WebElement webElement, String text) throws Exception{
		waitUntilElementIsVisible(webElement);
		webElement.clear();
		webElement.sendKeys(text);
	}

    protected String getTextWeb(WebElement element){
        return element.getText();
    }


    public void switchToFrame(WebElement iframe) throws Exception{
		waitUntilElementIsVisible(iframe);
		driver.switchTo().frame(iframe);
	}
	
	public void switchToFrame(By iframe) throws Exception{
		WebElement webelement = getDriver().findElement(iframe);
		waitUntilElementIsVisible(webelement);
		driver.switchTo().frame(webelement);
	}

    public void defaultWindow(){
		driver.switchTo().defaultContent();
	}

    public void refreshWebPage(){
		driver.navigate().refresh();
		waitExplicit(5);
	}
    public void write(WebElement element, String text) {
            element.clear();
            element.sendKeys(text);
    }
    public boolean waitForElement(WebElement element){
        try{            
            this.wait.withTimeout(Duration.ofSeconds(TIMEOUT)).until(ExpectedConditions.visibilityOf(element));
            return true;
        }catch(TimeoutException toe){
            return false;
        }
    }
    public boolean elementIsNotPresent(WebElement element){
            if(!waitForElement(element)){
                return true;
            }else{
                return false;
            }
    }
    //Espera a que desaparezca un elemento
    public void waitForElementDisappear(WebElement element){
        boolean isVisible = true;
        do{
            if(!waitForElement(element)){
                isVisible = false;
            }
        }while(isVisible);
    }
    /*
        Detiene la ejecución y la retoma cuando un elemento desaparezca.
        Exclusivo intervenciones humanas.
     */
    public void waitForHumanIntervention(WebElement element){
        System.out.println("Esperando intervencion humana...");
        //Enviar notificacion de espera por intervencion humana
        boolean flagFind = true;
        for (int i = 0 ; i < 120 ; i++){
            if (waitForElement(element)){
                flagFind = false;
                i = 120;
            }
        }
        if (flagFind){
            throw new TimeoutException("El tiempo para realizar la intervencion humana ha finalizado...");
        }else{
            System.out.println("Intervención humana válida, reinicializando accion automatica...");
        }
    }
}
