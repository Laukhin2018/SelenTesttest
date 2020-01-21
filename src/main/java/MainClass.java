import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainClass {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions actions;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "G:\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,15);
        actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://www.wiley.com/en-us");
        closeLocationBoard();

        MainClass tests = new MainClass();


        // point 1
        //tests.point1TestLinksInWhoWeServe();


        // point 2
        //tests.point2TestInputSearch("Java");

        // point 3
        System.out.println("Point 3:");

        String searchWord = "Java";
        WebElement searchBox = driver.findElement(By.cssSelector("#js-site-search-input"));
        searchBox.sendKeys(searchWord);

        WebElement searchButton = driver.findElement(By.cssSelector(".glyphicon.glyphicon-search"));
        clickableElement(searchButton);
        searchButton.click();

        List<WebElement> allProductItem = driver.findElements(By.cssSelector(".product-item"));
        for(WebElement product : allProductItem){
            scrollToElement(product);

        }



        driver.quit();
        driver = null;
    }

    public void point2TestInputSearch(String word){
        System.out.println("Point 2:");
        String searchWord = word;
        WebElement searchBox = driver.findElement(By.cssSelector("#js-site-search-input"));
        searchBox.sendKeys(searchWord);
        WebElement boardResultSearch = driver.findElement(By.cssSelector(".ui-menu-item-wrapper"));
        wait.until(ExpectedConditions.attributeContains(boardResultSearch, "display", "block"));

        int counter = 1;
        List<WebElement> itemResultSearch = driver.findElements(By.cssSelector(".searchresults-item.ui-menu-item > a"));
        for(WebElement item : itemResultSearch){
            String textResult = item.getAttribute("textContent");
            Boolean isContainsWord = (textResult.toLowerCase()).contains(searchWord.toLowerCase());
            if(isContainsWord){
                System.out.println(counter + ") Result search " + "\"" + textResult +
                        "\"" + "  has word " + "\"" + searchWord + "\"");
            }
            else {
                System.out.println(counter + ") Attention!!! Result search "  + "\"" + textResult + "\"" +
                        "  has NOT word " + "\"" + searchWord + "\"");
            }
            counter++;
        }
    }

    public void point1TestLinksInWhoWeServe(){
        System.out.println("Point 1:");
        ArrayList<String> result = new ArrayList<String>();
        List<WebElement> allLinkWhoWeServe = driver.findElements(By.cssSelector("#Level1NavNode1 > ul > li > a[href]"));
        int countLinkWhoWeServe = allLinkWhoWeServe.size();

        for (int item = 1; item <= countLinkWhoWeServe; item++){

            if (item == 11){
                continue;
            }

            WebElement buttonWhoWeServe = driver.findElement(By.cssSelector("#main-header-navbar > ul > li.dropdown-submenu:nth-child(1)"));
            clickableElement(buttonWhoWeServe);
            buttonWhoWeServe.click();

            WebElement linksWhoWeServe = driver.findElement(By.cssSelector("#Level1NavNode1 > ul > li:nth-child(" + item + ") > a[href]"));
            clickableElement(linksWhoWeServe);
            linksWhoWeServe.click();

            WebElement bannerImage = driver.findElement(By.cssSelector(".sg-title-h1"));
            visibleElement(bannerImage);
            result.add(driver.getTitle() + ": " + driver.getCurrentUrl());

            WebElement pageFooter = driver.findElement(By.cssSelector(".main-footer-copyright"));
            scrollToElement(pageFooter);
        }
        driver.get("https://www.wiley.com/en-us");
        System.out.println("Result test №1. Titles and URLs of checked links:");
        for(String item : result){
            System.out.println(item);
        }
    }

    public static void  scrollToElement(WebElement outElement){
        clickableElement(outElement);
        actions.moveToElement(outElement).build().perform();
    }

    public static void visibleElement(WebElement locator){
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    public static void clickableElement(WebElement locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void closeLocationBoard(){

        try{
            WebElement closeFirstBoard1 = driver.findElement(By.cssSelector(".modal-footer > button:nth-child(2)"));
            clickableElement(closeFirstBoard1);
            closeFirstBoard1.click();
        }
        catch (WebDriverException e){

            System.out.println("Получена ошибка " + e);
        }

    }
}
