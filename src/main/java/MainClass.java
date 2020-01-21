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

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\browserDriver\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,15);
        actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://www.wiley.com/en-us");
        closeLocationBoard();

        MainClass tests = new MainClass();


        // point 1
        String[] verifiableLinks = {"Students", "Instructors", "Book Authors", "Professionals", "Researchers",
                "Institutions", "Librarians", "Corporations", "Societies", "Journal Editors", "Government"};
        //tests.point1TestLinksInWhoWeServe(verifiableLinks);


        // point 2
        //tests.point2TestInputSearch("Java");

        // point 3
        //tests.point3TestMatchButtonsOnProduct("Java");

        // point 4
        String linkName = "Education";
        System.out.println("Point 4:");

        String[] checkedSumLinls = {"Information & Library Science",  "Education & Public Policy",
                "K-12 General", "Higher Education General",
                "Vocational Technology ",
                " Conflict Resolution & Mediation (School settings) ",
                " Curriculum Tools- General ", " Special Educational Needs ", " Theory of Education ",
                " Education Special Topics ", " Educational Research & Statistics ", " Literacy & Reading ",
                "Classroom Management"};

        WebElement buttonSubjects = driver.findElement(By.cssSelector("#main-header-navbar > ul > li.dropdown-submenu:nth-child(2)"));
        actions.moveToElement(buttonSubjects).build().perform();
        Thread.sleep(1000);

        List<WebElement> allLinkSubjects = driver.findElements(By.cssSelector("#Level1NavNode2 > ul > li"));
        boolean hasLink = true;
        for (WebElement item : allLinkSubjects){
            String name = item.getAttribute("innerText");
            hasLink = name.contains(linkName);
            if(hasLink){
                actions.moveToElement(item).build().perform();
                Thread.sleep(1000);

                Boolean has = true;
                for (String link : checkedSumLinls){
                    has = link.contains(name);
                    if(has){
                        System.out.println(link + " has!!!");
                        break;
                    }
                }
                if (!has){
                    continue;
                }

                break;
            }
        }
        if(!hasLink){
            System.out.println("Error! Link not found!");
        }











        driver.quit();
        driver = null;
    }

    public void point3TestMatchButtonsOnProduct(String word){
        // point 3
        System.out.println("Point 3:");

        String searchWord = word;
        WebElement searchBox = driver.findElement(By.cssSelector("#js-site-search-input"));
        searchBox.sendKeys(searchWord);

        WebElement searchButton = driver.findElement(By.cssSelector(".glyphicon.glyphicon-search"));
        clickableElement(searchButton);
        searchButton.click();

        int counter = 1;
        List<WebElement> allProductItem = driver.findElements(By.cssSelector(".product-item"));
        for(WebElement product : allProductItem){
            scrollToElement(product);

            System.out.println("Find product " + counter);
            List<WebElement> allButtonPresent = product.findElements(By.cssSelector(".nav.nav-tabs.eBundlePlpTab.hidden-xs > li"));
            for (WebElement button : allButtonPresent){
                clickableElement(button);
                button.click();

                WebElement buttonName = button.findElement(By.cssSelector(".productButtonGroupName"));
                String groupName = buttonName.getAttribute("textContent");


                WebElement actionZone = product.findElement(By.cssSelector("#tabContentStyle .tab-pane.active"));

                String checkedButton = "";
                try{
                    WebElement currentButton = actionZone.findElement(By.cssSelector(".tab-pane.active button.small-button.add-to-cart-button.js-add-to-cart"));
                    checkedButton = currentButton.getAttribute("innerText");

                }
                catch (NoSuchElementException e){
                    WebElement currentButton = actionZone.findElement(By.cssSelector(".product-button"));
                    checkedButton = currentButton.getAttribute("innerText");
                }

                if (groupName.equals("E-Book") || groupName.equals("Print")){
                    if(checkedButton.equals("ADD TO CART")){
                        System.out.println("Group \"" + groupName + "\" match with button \"" + checkedButton + "\"");
                    }
                    else {
                        System.out.println("Error!!! Group \"" + groupName + "\" does not match with button \"" + checkedButton + "\"");
                    }
                }
                else if (groupName.equals("O-Book")){
                    if(checkedButton.equals("VIEW ON WILEY ONLINE LIBRARY")){
                        System.out.println("Group \"" + groupName + "\" match with button \"" + checkedButton + "\"");
                    }
                    else {
                        System.out.println("Error!!! \"" + groupName + "\" does not match with \"" + checkedButton +"\"");
                    }
                }
                else {
                    System.out.println("Error!!! Group \"" + groupName + "\" does not match");
                }
            }
            System.out.println("");
            counter++;
        }
        driver.get("https://www.wiley.com/en-us");
        System.out.println("End test!");
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
        searchBox.clear();
        driver.get("https://www.wiley.com/en-us");
        System.out.println("End test!");
    }

    public void point1TestLinksInWhoWeServe(String[] verifiableLinks){
        System.out.println("Point 1:");

        ArrayList<String> result = new ArrayList<String>();
        List<WebElement> allLinkWhoWeServe = driver.findElements(By.cssSelector("#Level1NavNode1 > ul > li > a[href]"));
        int countLinkWhoWeServe = allLinkWhoWeServe.size();

        for (int item = 1; item <= countLinkWhoWeServe; item++){

            WebElement buttonWhoWeServe = driver.findElement(By.cssSelector("#main-header-navbar > ul > li.dropdown-submenu:nth-child(1)"));
            clickableElement(buttonWhoWeServe);
            buttonWhoWeServe.click();

            WebElement linksWhoWeServe = driver.findElement(By.cssSelector("#Level1NavNode1 > ul > li:nth-child(" + item + ") > a[href]"));
            clickableElement(linksWhoWeServe);
            String name = linksWhoWeServe.getAttribute("innerText");

            Boolean hasLink = true;
            for (String linkName : verifiableLinks){
                hasLink = linkName.equals(name);
                if(hasLink){
                    break;
                }
            }
            if (!hasLink){
                continue;
            }

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
        System.out.println("End test!");
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
