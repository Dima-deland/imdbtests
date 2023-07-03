import com.beust.jcommander.Parameter;
import com.google.common.base.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;


public class Test250 {
    public static void main(String[] args) {
    }

    WebDriver driver = new ChromeDriver();
  @AfterTest
   public void tearDown() {driver.quit();
  }
  @Parameter (names = "pathtodriver")
  public void dr(String pathtodriver){
      System.setProperty("webdriver.chrome.driver", pathtodriver);}
  @Test
    public void Test250() {
        var toplimit = 250;
        var sitev = 1;
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

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      WebElement top = driver.findElement(By.xpath("//*[@id='imdbHeader']/div[2]/aside/div/div[2]/div/div[1]/span/div/div/ul/a[2]"));
      driver.get(top.getAttribute("href"));


      //check which site version we have, toggle sitev to 0 if version is old, remains 1 as default when site version new
        WebElement body = driver.findElement(By.cssSelector("body"));
      if (body.getAttribute("className").contains("fixed")){
          sitev=0;
      }

        if (sitev==0) {
            //get data for old version: titles,year, rating
            for (int i = 0; i < toplimit; i++) {
                title[i] = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1) + "]/td[2]/a"));
                movie[i].Title = title[i].getText();
            }
            for (int i = 0; i < toplimit; i++) {
                year[i] = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1) + "]/td[2]/span"));
                movie[i].Year = year[i].getText().substring(1, 5);
            }
            for (int i = 0; i < toplimit; i++) {
                rating[i] = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1 ) + "]/td[3]/strong"));
                movie[i].Rating = rating[i].getText().replace(',','.');
            }
        }else{
            //get data for new version: titles,year, rating
            for (int i = 0; i < toplimit; i++) {
                    title[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i+1) + "]/div[2]/div/div/div[1]/a/h3"));
                    titletext[i] = title[i].getText();
                    movie[i].Title = titletext[i].substring(titletext[i].indexOf(".")+2);
                }
                for (int i = 0; i < toplimit; i++) {
                    year[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i+1) + "]/div[2]/div/div/div[2]/span[1]"));
                    movie[i].Year = year[i].getText();
                }
                for (int i = 0; i < toplimit; i++) {
                    rating[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i + 1) + "]/div[2]/div/div/span/div/span"));
                    movie[i].Rating = rating[i].getText();
                }
        }
        // define position of random movie
        Random r = new Random();
        int rposition = r.nextInt(toplimit) + 1;

       System.out.println("Movie on " + Integer.toString(rposition) + " was checked");

        if (sitev==0) {
            WebElement rmovielink = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(rposition) + "]/td[2]/a"));
            new Actions(driver)
                    .scrollToElement(rmovielink)
                    .perform();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            rmovielink.click();
        }else{
            //close notification that overlay page
            WebElement clsnt = driver.findElement(By.className("ipc-snackbase__buttons"));
            clsnt.click();

            // open random movie page
            WebElement rmovielink = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(rposition) + "]/div[2]/div/div/div[1]/a"));
            rmovielink.getAttribute("href");
            driver.get(rmovielink.getAttribute("href"));
        }
      // get values from random movie page
        WebElement mtitle = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/h1/span"));
        WebElement myear = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/ul/li[1]/a"));
        WebElement mrating = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[2]/div/div[1]/a/span/div/div[2]/div[1]/span[1]"));
        WebElement mposition = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/div/section/div/div[1]/section[1]/div/div/a"));

        String mtitlev = mtitle.getText();
        String myearv = myear.getText();
        String mratingv = mrating.getText();
        String mpositionv = mposition.getText().substring(17);
        String rmposlst = String.valueOf(movie[rposition-1].Position);


      // compare values on random movie page
        Verify.verify(mtitlev.equals(movie[rposition-1].Title), "Title isn't matched Value on list:"+movie[rposition-1].Title.toString()+" Value on page:"+mtitlev.toString());
        Verify.verify(myearv.equals(movie[rposition-1].Year), "Year isn't matched Value on list:"+movie[rposition-1].Year.toString()+" Value on page:"+myearv.toString());
        Verify.verify(mratingv.equals(movie[rposition-1].Rating), "Rating isn't matched. Value on list:"+movie[rposition-1].Rating.toString()+" Value on page:"+mratingv.toString());
        Verify.verify(mpositionv.equals(rmposlst), "Position isn't matched. Value on list:"+ rmposlst +" Value on page:"+mpositionv);


        driver.quit();
    }
}


