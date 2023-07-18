import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;


public class CheckGodfatherInTopFive {
    WebDriver driver = new ChromeDriver();
    private TopMoviesPage topMoviesPage = new TopMoviesPage(driver);
    private MoviePage moviePage = new MoviePage(driver);
    private Integer TOPLIMIT = 5;
    private Movies[] movie = new Movies[TOPLIMIT];
    private String GODFATHERURL = "/title/tt0068646/?ref_=chttp_t_2";
    private String godFatherlocaltitle;
    private  SoftAssert softAssert = new SoftAssert();
    private  Movies movieGodfatherPage = new Movies();
    private Integer godFatherplace;


    @Test
    public void CheckGodfatherInTopFive() {

        //open Top Movies Page and close notification on page
        topMoviesPage.openPage();
        topMoviesPage.closeNotification();

        //collect Title, Year, Place, Rating for Top 5 movies
        for (int i = 0; i < TOPLIMIT; i++){
            movie[i] = new Movies();
            movie[i].Title = topMoviesPage.getMovieTitle(i+1);
            movie[i].Year = topMoviesPage.getMovieYear(i+1);
            movie[i].Place = topMoviesPage.getMoviePlace(i+1);
            movie[i].Rating = topMoviesPage.getMovieRating(i+1);
        }

        // get movie name in local language
        godFatherlocaltitle = topMoviesPage.getMovieLocalTitleByurl(GODFATHERURL);
        godFatherplace = topMoviesPage.getMoviePlaceByurl(GODFATHERURL);

        // click on Godfather movie link and open page of movie
        topMoviesPage.waitMovieLink(godFatherplace);
        topMoviesPage.clickOnMovieLink(godFatherplace);

        //collect Title, Year, Place, Rating for Godfather movie
        movieGodfatherPage.Title = moviePage.getMovieTitle();
        movieGodfatherPage.Year = moviePage.getMovieYear();
        movieGodfatherPage.Place = moviePage.getMoviePlace();
        movieGodfatherPage.Rating = moviePage.getMovieRating();



        //check that Godfather movie in Top 5
        softAssert.assertTrue(godFatherplace<TOPLIMIT, "Godfather movie is not in Top 5");


        Integer gfi = godFatherplace - 1;

        //check that data on Top movies page equals data on Godfather movie page
        softAssert.assertEquals(movie[gfi].Title,movieGodfatherPage.Title,
                "Title doesn't match. Title in list:"+movie[gfi].Title+" ,title on page"+movieGodfatherPage.Title);
        softAssert.assertEquals(movie[gfi].Year,movieGodfatherPage.Year,
                "Year doesn't match. Year in list:"+movie[gfi].Year+" ,year on page"+movieGodfatherPage.Year);
        softAssert.assertEquals(movie[gfi].Rating,movieGodfatherPage.Rating,
                "Rating doesn't match. Rating in list:"+movie[gfi].Rating+" ,rating on page"+movieGodfatherPage.Rating);
        softAssert.assertEquals(movie[gfi].Place,movieGodfatherPage.Place,
                "Place doesn't match. Place in list:"+movie[gfi].Place+" ,place on page"+movieGodfatherPage.Place);

        softAssert.assertAll();

   driver.quit();
    }
}