import com.google.common.base.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;


public class Test250 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/dmytro/chromedriver");
    }

    WebDriver driver = new ChromeDriver();
    /*@AfterSuite
    public void tearDown() {
        driver.quit();
    }*/
    @Test
    public void Test250() {
        var toplimit = 250;
        Movies[] movie = new Movies[toplimit];
        WebElement[] title = new WebElement[toplimit];
        WebElement[] year = new WebElement[toplimit];
        WebElement[] rating = new WebElement[toplimit];
        String[] titletext =new String[toplimit];


        for (int i = 0; i < toplimit; i++) {
            movie[i] = new Movies();
            movie[i].Position = i + 1;
        }

        driver.get("https://www.imdb.com");


        WebElement burger = driver.findElement(By.xpath("//*[@id='imdbHeader-navDrawerOpen']"));
        burger.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement top = driver.findElement(By.linkText("Top 250 Movies"));
        top.click();


        // get class name to check site version
        WebElement body = driver.findElement(By.cssSelector("body"));

        if (body.getAttribute("className").contains("fixed")) {
            //get titles data
            for (int i = 0; i < toplimit; i++) {
                title[i] = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1) + "]/td[2]/a"));
                movie[i].Title = title[i].getText();
            }
            //get year data
            for (int i = 0; i < toplimit; i++) {
                year[i] = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1) + "]/td[2]/span"));
                movie[i].Year = year[i].getText().substring(1, 5);
            }
            //get rating data
            for (int i = 0; i < toplimit; i++) {
                rating[i] = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1 ) + "]/td[3]/strong"));
                movie[i].Rating = rating[i].getText();
            }
        }else{
                //get titles data new version
                for (int i = 0; i < toplimit; i++) {
                    title[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i+1) + "]/div[2]/div/div/div[1]/a/h3"));
                    titletext[i] = title[i].getText();
                    movie[i].Title = titletext[i].substring(titletext[i].indexOf(".")+2);
                }
                //get year data new version
                for (int i = 0; i < toplimit; i++) {
                    year[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i+1) + "]/div[2]/div/div/div[2]/span[1]"));
                    movie[i].Year = year[i].getText();
                }
                //get rating data new version
                for (int i = 0; i < toplimit; i++) {
                    rating[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i + 1) + "]/div[2]/div/div/span/div/span"));
                    movie[i].Rating = rating[i].getText();
                }
        }

        Random r = new Random();
        int rposition = r.nextInt(toplimit) + 1;

       System.out.println("Movie on " + Integer.toString(rposition) + " was checked");

        if (body.getAttribute("className").contains("fixed")) {
            WebElement rmovielink = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(rposition) + "]/td[2]/a"));
            new Actions(driver)
                    .scrollToElement(rmovielink)
                    .perform();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            rmovielink.click();
        }else{

            WebElement clsnt = driver.findElement(By.className("ipc-snackbase__buttons"));
            clsnt.click();


            WebElement rmovielink = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(rposition) + "]/div[2]/div/div/div[1]/a"));
            rmovielink.getAttribute("href");
            System.out.println(rmovielink.getAttribute("href"));

            driver.get(rmovielink.getAttribute("href"));
        }

        WebElement mtitle = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/h1/span"));
        WebElement myear = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/ul/li[1]/a"));
        WebElement mrating = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[2]/div/div[1]/a/span/div/div[2]/div[1]/span[1]"));

        String mtitlev = mtitle.getText();
        String myearv = myear.getText();
        String mratingv = mrating.getText();


        Verify.verify(mtitlev.equals(movie[rposition-1].Title), "Title isn't matched Value on list:"+movie[rposition-1].Title.toString()+" Value on page:"+mtitle.getText().toString());
        Verify.verify(myearv.equals(movie[rposition-1].Year), "Year isn't matched Value on list:"+movie[rposition-1].Year.toString()+" Value on page:"+myear.getText().toString());
        Verify.verify(mratingv.equals(movie[rposition-1].Rating), "Rating isn't matched. Value on list:"+movie[rposition-1].Rating.toString()+" Value on page:"+mrating.getText().toString());

        driver.quit();
    }
}


