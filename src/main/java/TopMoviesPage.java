import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TopMoviesPage {

    WebDriver driver;
    public TopMoviesPage(WebDriver driver) {
        this.driver = driver;
    }


    WebElement[] placeandtitle = new WebElement[251];
    WebElement[] year = new WebElement[251];
    WebElement[] rating = new WebElement[251];
    WebElement movieLink;
    WebElement movietocompare;

    public void openPage() {
        driver.get("https://www.imdb.com/chart/top/?ref_=nv_mv_250");
    }

    public void closeNotification() {
        //close notification that overlay page
        WebElement clsnt = driver.findElement(By.className("ipc-snackbase__buttons"));
        clsnt.click();
    }

    public String getMovieTitle(Integer position) {
        placeandtitle[position] = driver.findElement(By.cssSelector("li:nth-child(" + position + ")>div>div>div>div>a>h3"));
        return    placeandtitle[position].getText().substring(placeandtitle[position].getText().indexOf(" ")+1);
    }

    public void waitMovieLink (Integer position) {
        movieLink = (new WebDriverWait(driver, Duration.ofSeconds(40))
        .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li:nth-child(" + position + ")>div>div>div>div>a"))));
    }

    public void clickOnMovieLink (Integer position) {
        movieLink.click();
    }

    public String getMovieYear(Integer position) {
        year[position] = driver.findElement(By.cssSelector("li:nth-child(" +position+ ")>div>div>div>div>span"));
        return year[position].getText();
    }

    public String getMovieRating(Integer position){
        rating[position] = driver.findElement(By.cssSelector("li:nth-child(" +position+ ") > div.ipc-metadata-list-summary-item__c > div > div > span > div > span"));
        return rating[position].getText();
    }

    public String getMoviePlace (Integer position){
        placeandtitle[position] = driver.findElement(By.cssSelector("li:nth-child(" + position + ")>div>div>div>div>a>h3"));
        return    placeandtitle[position].getText().substring(0,placeandtitle[position].getText().indexOf("."));
    }

    public String getMovieLocalTitleByurl (String link){
        movietocompare = driver.findElement(By.cssSelector("[href='"+link+"']"));
        return movietocompare.getText().substring(movietocompare.getText().indexOf(" ")+1);
    }

    public Integer getMoviePlaceByurl (String link){
        movietocompare = driver.findElement(By.cssSelector("[href='"+link+"']"));
        return Integer.valueOf(movietocompare.getText().substring(0, movietocompare.getText().indexOf(".")));
    }

    public void scrolltoMovieLink (Integer position) {
        WebElement footer = driver.findElement(By.cssSelector("li:nth-child(" + position + ")>div>div>div>div>a"));
        int deltaY = footer.getRect().y;
        new Actions(driver)
                .scrollByAmount(0, deltaY)
                .perform();
    }
}




