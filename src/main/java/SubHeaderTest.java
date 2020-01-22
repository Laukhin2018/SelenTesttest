import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SubHeaderTest {

    private String[] checkedSumLinls;
    public void setCheckedSumLinls(String[] arrayLinks){
        checkedSumLinls = arrayLinks;
    }

    private String linkName;
    public void setLinkName(String link){
        linkName = link;
    }

    public void point1TestLinksInWhoWeServe(){
        System.out.println("Point 1:");

        ArrayList<String> result = new ArrayList<String>();
        List<WebElement> allLinkWhoWeServe = MainClass.driver.findElements(By.cssSelector("#Level1NavNode1 > ul > li > a[href]"));
        System.out.println("Name of links that match the name of the array:");
        int countLinkWhoWeServe = allLinkWhoWeServe.size();

        for (int item = 1; item <= countLinkWhoWeServe; item++){

            WebElement buttonWhoWeServe = MainClass.driver.findElement(By.cssSelector("#main-header-navbar > ul > li.dropdown-submenu:nth-child(1)"));
            MainClass.clickableElement(buttonWhoWeServe);
            buttonWhoWeServe.click();

            WebElement linksWhoWeServe = MainClass.driver.findElement(By.cssSelector("#Level1NavNode1 > ul > li:nth-child(" + item + ") > a[href]"));
            MainClass.clickableElement(linksWhoWeServe);
            String name = linksWhoWeServe.getAttribute("innerText");

            Boolean hasLink = checkNameLink(name, checkedSumLinls);
            if (!hasLink){
                continue;
            }
            else{
                linksWhoWeServe.click();
            }


            WebElement bannerImage = MainClass.driver.findElement(By.cssSelector(".sg-title-h1"));
            MainClass.visibleElement(bannerImage);
            result.add(MainClass.driver.getTitle() + ": " + MainClass.driver.getCurrentUrl());

            WebElement pageFooter = MainClass.driver.findElement(By.cssSelector(".main-footer-copyright"));
            MainClass.scrollToElement(pageFooter);
        }
        MainClass.driver.get("https://www.wiley.com/en-us");
        System.out.println("\nResult point 1. Found headers and URLs for the entered array:");
        for(String item : result){
            System.out.println(item);
        }
        System.out.println("End test!\n");
    }

    public void point4Test() throws InterruptedException {

        System.out.println("Point 4:");



        WebElement buttonSubjects = MainClass.driver.findElement(By.cssSelector("#main-header-navbar > ul > li.dropdown-submenu:nth-child(2)"));
        MainClass.actions.moveToElement(buttonSubjects).build().perform();
        Thread.sleep(1000);

        List<WebElement> allLinkSubjects = MainClass.driver.findElements(By.cssSelector("#Level1NavNode2 > ul > li"));
        boolean hasLink = true;
        for (WebElement item : allLinkSubjects){
            String name = item.getAttribute("innerText");
            hasLink = name.contains(linkName);
            if(hasLink){
                MainClass.actions.moveToElement(item).build().perform();
                Thread.sleep(1000);

                List<WebElement> allSubLink = item.findElements(By.cssSelector("div > ul > ul > li.dropdown-item"));
                for (WebElement subItem : allSubLink){
                    String nameSublink = subItem.getAttribute("innerText");

                    Boolean hasName = checkNameLink(nameSublink, checkedSumLinls);
                    if (!hasName){
                        continue;
                    }
                    else{
                        System.out.println("Name of links found \"" + nameSublink + "\"\n");
                    }
                }

                break;
            }
        }
        if(!hasLink){
            System.out.println("Error! Link not found!");
        }
        System.out.println("End test!\n");
    }

    public static Boolean checkNameLink (String nameLink, String[] names){
        Boolean hasLink = true;
        for (String linkName : names){
            hasLink = linkName.contains(nameLink);
            if(hasLink){
                System.out.println("Name of links \"" + nameLink + "\" match element array \"" + linkName + "\"");
                break;
            }
        }
        return hasLink;
    }
}
