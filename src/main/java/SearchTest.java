import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchTest {

    private String word;
    public void setWord(String link){
        word = link;
    }


    public void point3TestMatchButtonsOnProduct(){
        // point 3
        System.out.println("Point 3:");

        String searchWord = word;
        WebElement searchBox = MainClass.driver.findElement(By.cssSelector("#js-site-search-input"));
        searchBox.sendKeys(searchWord);

        WebElement searchButton = MainClass.driver.findElement(By.cssSelector(".glyphicon.glyphicon-search"));
        MainClass.clickableElement(searchButton);
        searchButton.click();

        int counter = 1;
        List<WebElement> allProductItem = MainClass.driver.findElements(By.cssSelector(".product-item"));
        for(WebElement product : allProductItem){
            MainClass.scrollToElement(product);

            System.out.println("Find product " + counter);
            List<WebElement> allButtonPresent = product.findElements(By.cssSelector(".nav.nav-tabs.eBundlePlpTab.hidden-xs > li"));
            for (WebElement button : allButtonPresent){
                MainClass.clickableElement(button);
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
        MainClass.driver.get("https://www.wiley.com/en-us");
        System.out.println("End test!\n");
    }


    public void point2TestInputSearch(){
        System.out.println("Point 2:");

        String searchWord = word;
        WebElement searchBox = MainClass.driver.findElement(By.cssSelector("#js-site-search-input"));
        searchBox.sendKeys(searchWord);
        WebElement boardResultSearch = MainClass.driver.findElement(By.cssSelector(".ui-menu-item-wrapper"));
        MainClass.wait.until(ExpectedConditions.attributeContains(boardResultSearch, "display", "block"));

        int counter = 1;
        List<WebElement> itemResultSearch = MainClass.driver.findElements(By.cssSelector(".searchresults-item.ui-menu-item > a"));
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
        MainClass.driver.get("https://www.wiley.com/en-us");
        System.out.println("End test!\n");
    }
}
