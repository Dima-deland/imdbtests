
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import java.util.Random;


public class CheckRandomMovieInRating {

    WebDriver driver = new ChromeDriver();
    private TopMoviesPage topMoviesPage = new TopMoviesPage(driver);
    private MoviePage moviePage = new MoviePage(driver);
    private Integer TOPLIMIT = 250;
    private Movies[] movie = new Movies[TOPLIMIT];
    private SoftAssert softAssert = new SoftAssert();
    private Movies randomMovie = new Movies();


    @Test
    public void CheckRandomMovieInRating() {

        //open Top Movies Page and close notification on page
        topMoviesPage.openPage();
        topMoviesPage.closeNotification();

        //collect Title, Year, Place, Rating for Top 250 movies
        for (int i = 0; i < TOPLIMIT; i++){
            movie[i] = new Movies();
            movie[i].Title = topMoviesPage.getMovieTitle(i+1);
            movie[i].Year = topMoviesPage.getMovieYear(i+1);
            movie[i].Place = topMoviesPage.getMoviePlace(i+1);
            movie[i].Rating = topMoviesPage.getMovieRating(i+1);
        }

        // define position of random movie
        Random r = new Random();
        Integer randomMovieplace = r.nextInt(TOPLIMIT) + 1;
        System.out.println("Movie on " + Integer.toString(randomMovieplace) + " was checked");

        topMoviesPage.scrolltoMovieLink(randomMovieplace);
        topMoviesPage.waitMovieLink(randomMovieplace);


        // click on rsndom movie link and open page of movie
       topMoviesPage.clickOnMovieLink(randomMovieplace);



        //collect Title, Year, Place, Rating for random movie
        randomMovie.Title = moviePage.getMovieTitle();
        randomMovie.Year = moviePage.getMovieYear();
        randomMovie.Place = moviePage.getMoviePlace();
        randomMovie.Rating = moviePage.getMovieRating();


        Integer rmi = randomMovieplace-1;


        //check that data on Top movies page equals data on random movie page
        softAssert.assertEquals(movie[rmi].Title,randomMovie.Title,
                "Title doesn't match. Title in list:"+movie[rmi].Title+" ,title on page"+randomMovie.Title);
        softAssert.assertEquals(movie[rmi].Year,randomMovie.Year,
                "Year doesn't match. Year in list:"+movie[rmi].Year+" ,year on page"+randomMovie.Year);
        softAssert.assertEquals(movie[rmi].Rating,randomMovie.Rating,
                "Rating doesn't match. Rating in list:"+movie[rmi].Rating+" ,rating on page"+randomMovie.Rating);
        softAssert.assertEquals(movie[rmi].Place,randomMovie.Place,
                "Place doesn't match. Place in list:"+movie[rmi].Place+" ,place on page"+randomMovie.Place);
        softAssert.assertAll();

        driver.quit();

    }
}

