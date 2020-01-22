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
        System.setProperty("webdriver.chrome.driver", "G:\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,15);
        actions = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("https://www.wiley.com/en-us");
        closeLocationBoard();

        SearchTest search = new SearchTest();
        SubHeaderTest subHeader = new SubHeaderTest();


        // point 1
        String[] verifiableLinks = {"Students", "Instructors", "Book Authors", "Professionals", "Researchers",
                "Institutions", "Librarians", "Corporations", "Societies", "Journal Editors", "Government"};
        subHeader.setCheckedSumLinls(verifiableLinks);
        subHeader.point1TestLinksInWhoWeServe();


        // point 2
        search.setWord("Java");
        search.point2TestInputSearch();

        // point 3
        search.point3TestMatchButtonsOnProduct();

        // point 4
        String[] checkedSumLinls = {"Information & Library Science",  "Education & Public Policy",
                "K-12 General", "Higher Education General",
                "Vocational Technology",
                "Conflict Resolution & Mediation (School settings)",
                "Curriculum Tools-General", "Special Educational Needs", "Theory of Education",
                "Education Special Topics ", "Educational Research & Statistics", "Literacy & Reading",
                "Classroom Management"};
        String linkName = "Education";
        subHeader.setCheckedSumLinls(checkedSumLinls);
        subHeader.setLinkName(linkName);
        subHeader.point4Test();



        driver.quit();
        driver = null;
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
