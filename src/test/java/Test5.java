import com.beust.jcommander.Parameter;
import com.google.common.base.Verify;
import junit.runner.Version;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v85.systeminfo.SystemInfo;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;


public class Test5 {
    public static void main(String[] args) {
    }

    @Parameter(names = "pathtodriver")
    public void dr(String pathtodriver){
        System.setProperty("webdriver.chrome.driver", pathtodriver);}
   @Test
    public void Test5() {

        var toplimit = 5;
        var sitev = 1;
        String gflocname = new String();
        Movies[] movie = new Movies[toplimit];
        WebElement[] title = new WebElement[toplimit];
        WebElement[] year = new WebElement[toplimit];
        WebElement[] rating = new WebElement[toplimit];

       WebDriver driver = new ChromeDriver();

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
                rating[i] = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(i + 1) + "]/td[3]/strong"));
                movie[i].Rating = rating[i].getText().replace(',','.');
            }
        }else{
            //close notification that overlay page
            WebElement clsnt = driver.findElement(By.className("ipc-snackbase__buttons"));
            clsnt.click();

            //get data for old version: titles,year, rating
            for (int i = 0; i < toplimit; i++) {
                title[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i + 1) + "]/div[2]/div/div/div[1]/a/h3"));
                movie[i].Title = title[i].getText().substring(3);
            }
            for (int i = 0; i < toplimit; i++) {
                year[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i + 1) + "]/div[2]/div/div/div[2]/span[1]"));
                movie[i].Year = year[i].getText();
            }
            for (int i = 0; i < toplimit; i++) {
                rating[i] = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(i + 1) + "]/div[2]/div/div/span/div/span"));
                movie[i].Rating = rating[i].getText();
            }
        }


        String usercurrlang = Locale.getDefault().getDisplayLanguage().toString();
System.out.println(usercurrlang);

        switch (usercurrlang) {
            case "українська": gflocname = "Хрещений батько";
                break;
            case "English": gflocname = "The Godfather";
                break;
            case "русский": gflocname = "Крестный отец";
                break;
            default: System.out.println("Please set your system language to English, Ukrainian, Ru and rerun test");
                break;
        }

            ArrayList movieslist = new ArrayList();
            for (int i = 0; i < toplimit; i++) {
                movieslist.add(movie[i].Title);
            }

            // check if movie is present in Top5
            Verify.verify (movieslist.contains(gflocname),"It couldn't happen.'The Godfather' isn't in Top 5");

       int position = movieslist.indexOf(gflocname);

            if (sitev==0){
                WebElement gflink = driver.findElement(By.xpath("//*[@id='main']/div/span/div[1]/div/div[3]/table/tbody/tr[" + Integer.toString(position+1) + "]/td[2]/a"));
                driver.get (gflink.getAttribute("href"));
            }else {
                WebElement gflink = driver.findElement(By.xpath("//*[@id='__next']/main/div/div[3]/section/div/div[2]/div/ul/li[" + Integer.toString(position+1) +"]/div[2]/div/div/div[1]/a"));
                driver.get (gflink.getAttribute("href"));
            }


            WebElement mtitle = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/h1/span"));
            WebElement myear = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[1]/ul/li[1]/a"));
            WebElement mrating = driver.findElement(By.xpath("//*[@id='__next']/main/div/section[1]/section/div[3]/section/section/div[2]/div[2]/div/div[1]/a/span/div/div[2]/div[1]/span[1]"));

            String mtitlev = mtitle.getText();
            String myearv = myear.getText();
            String mratingv = mrating.getText();



            Verify.verify(mtitlev.equals(movie[position].Title), "Title isn't matched Value on list:"+movie[position].Title.toString()+" Value on page:"+mtitlev.toString());
            Verify.verify(myearv.equals(movie[position].Year), "Year isn't matched Value on list:"+movie[position].Year.toString()+" Value on page:"+myearv.toString());
            Verify.verify(mratingv.equals(movie[position].Rating), "Rating isn't matched. Value on list:"+movie[position].Rating.toString()+" Value on page:"+mratingv.toString());

            driver.quit();


        }

    }


