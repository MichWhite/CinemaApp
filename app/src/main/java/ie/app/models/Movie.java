package ie.app.models;


/**
 * Created by MichWhite on 10-Feb-16.
 */

public class Movie
{

    public String movieName;
    public String cinemaName;
    public double moviePrice;
    public String ageRating;
    public String time;
    public int dateDay;
    public int dateMonth;
    public int dateYear;
    public int movieId;
    public String videoId;

    public Movie (int movieId,double moviePrice, String movieName, String cinemaName, String ageRating,
                  String time, int dateDay, int dateMonth, int dateYear, String videoId)
    {
        this.movieId = movieId;
        this.moviePrice = moviePrice;
        this.movieName = movieName;
        this.cinemaName = cinemaName;
        this.ageRating = ageRating;
        this.time = time;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.videoId = videoId;
    }

    public void setmoviePrice(double moviePrice) {
        this.moviePrice = moviePrice;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }


    public void setTime(String time) {
        this.time = time;
    }
    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}