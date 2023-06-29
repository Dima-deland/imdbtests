import com.google.common.base.Verify;
import junit.runner.Version;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;


public class Test5 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/dmytro/chromedriver");
    }

    @Test
    public void Test5() {
        var toplimit = 5;
        Movies[] movie = new Movies[toplimit];
        WebElement[] title = new WebElement[toplimit];
        WebElement[] year = new WebElement[toplimit];
        WebElement[] rating = new WebElement[toplimit];


        for (int i = 0; i < toplimit; i++) {
            movie[i] = new Movies();
            movie[i].Position = i + 1;
//  System.out.println(movie[i].Position);
        }

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.imdb.com/");

        WebElement burger = driver.findElement(By.xpath("//*[@id=\'imdbHeader-navDrawerOpen\']"));
        burger.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        WebElement top = driver.findElement(By.linkText("Top 250 Movies"));
        top.click();

        // get class name to check site version
        WebElement gflink = driver.findElement(By.partialLinkText("The Godfather"));

        if (gflink.getAttribute("className").contains("ipc-title-link-wrapper")) {

            //get titles data new version
            for (int i = 0; i < toplimit; i++) {
                title[i] = driver.findElement(By.xpath("//*[@id=\'__next\']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i + 1) + "]/div[2]/div/div/div[1]/a/h3"));
                movie[i].Title = title[i].getText().substring(3);
            }

            //get year data new version
            for (int i = 0; i < toplimit; i++) {
                year[i] = driver.findElement(By.xpath("//*[@id=\'__next\']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i + 1) + "]/div[2]/div/div/div[2]/span[1]"));
                movie[i].Year = year[i].getText();
            }

            //get rating data new version
            for (int i = 0; i < toplimit; i++) {
                rating[i] = driver.findElement(By.xpath("//*[@id=\'__next\']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i + 1) + "]/div[2]/div/div/span/div/span"));
                movie[i].Rating = rating[i].getText();
            }
        } else {

            //get titles data
            for (int i = 0; i < toplimit; i++) {
                title[i] = driver.findElement(By.xpath("//*[@id=\'main\']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1) + "]/td[2]/a"));
                movie[i].Title = title[i].getText();
            }

            //get year data
            for (int i = 0; i < toplimit; i++) {
                year[i] = driver.findElement(By.xpath("//*[@id=\'main\']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1) + "]/td[2]/span"));
                movie[i].Year = year[i].getText().substring(1, 5);
            }

            //get rating data
            for (int i = 0; i < toplimit; i++) {
                rating[i] = driver.findElement(By.xpath("//*[@id=\'main\']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1) + "]/td[3]/strong"));
                movie[i].Rating = rating[i].getText();
            }
        }


            ArrayList movieslist = new ArrayList();
            for (int i = 0; i < toplimit; i++) {
                movieslist.add(movie[i].Title);
            }


            Verify.verify (movieslist.contains("The Godfather"),"It couldn't happen.'The Godfather' isn't in Top 5");


            gflink.click();

            WebElement mtitle = driver.findElement(By.xpath("//*[@id=\'__next\']/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/h1/span"));
            WebElement myear = driver.findElement(By.xpath("//*[@id=\'__next\']/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/ul/li[1]/a"));
            WebElement mrating = driver.findElement(By.xpath("//*[@id=\'__next\']/main/div/section[1]/section/div[3]/section/section/div[2]/div[2]/div/div[1]/a/span/div/div[2]/div[1]/span[1]"));

            String mtitlev = mtitle.getText();
            String myearv = myear.getText();
            String mratingv = mrating.getText();

            int position = movieslist.indexOf("The Godfather");

            Verify.verify(mtitlev.equals(movie[position].Title), "Title isn't matched Value on list:"+movie[position].Title.toString()+" Value on page:"+mtitlev.toString());
            Verify.verify(myearv.equals(movie[position].Year), "Year isn't matched Value on list:"+movie[position].Year.toString()+" Value on page:"+myearv.toString());
            Verify.verify(mratingv.equals(movie[position].Rating), "Rating isn't matched. Value on list:"+movie[position].Rating.toString()+" Value on page:"+mratingv.toString());

            driver.quit();


        }

    }


