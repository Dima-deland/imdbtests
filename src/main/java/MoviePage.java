

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MoviePage {

    WebDriver driver;
    public MoviePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getMovieTitle() {
        WebElement title = driver.findElement(By.cssSelector("span.sc-afe43def-1.fDTGTb"));
        return title.getText();
    }

    public String getMovieYear() {
        WebElement title = driver.findElement(By.cssSelector("div.sc-acac9414-0.jFcAtv>ul>li:nth-child(1)>a"));
        return title.getText();
    }

    public String getMovieRating() {
        WebElement title = driver.findElement(By.cssSelector(" span.sc-bde20123-1.iZlgcd"));
        return title.getText();
    }

    public String getMoviePlace() {
        WebElement title = driver.findElement(By.cssSelector("div.sc-c7c3a435-1.NixYx.ipc-page-grid__item.ipc-page-grid__item--span-2>section:nth-child(3)>div>div>a"));
        return title.getText().substring(17);
    }

}




