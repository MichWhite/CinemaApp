package ie.app.database;

/**
 * Created by MichWhite on 29-Feb-16.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import ie.app.cinema.R;
import ie.app.cinema.addMovie;
import ie.app.models.Movie;
import ie.app.models.Movie;


public class db_connector extends AppCompatActivity {



public void insert(SQLiteDatabase db, Movie addMovie){

    String date =addMovie.dateDay + "/" + addMovie.dateMonth + "/" + addMovie.dateYear;

    db.execSQL("INSERT INTO Movie VALUES('" + addMovie.movieId + "','" + addMovie.movieName + "','" + addMovie.cinemaName +
            "','" + addMovie.moviePrice + "','" + addMovie.time + "','" + addMovie.ageRating + "','" + date + "','" + addMovie.videoId + "');");


}


public ArrayList<Movie> getByTitle(SQLiteDatabase db, String movieName){
    Cursor c=db.rawQuery("SELECT * FROM Movie WHERE MovieTitle='" + movieName+ "'", null);

    ArrayList<Movie> tempmovies =new ArrayList();

    for(int i =0; i<c.getCount(); i++)
    {
        c.moveToPosition(i);

        int tempMovieId =(c.getInt(c.getColumnIndex("MovieID")));
        String tempMovieName = (c.getString(c.getColumnIndex("MovieTitle")));
        String tempCinema = (c.getString(c.getColumnIndex("CinemaName")));
        Double tempPrice = (c.getDouble(c.getColumnIndex("MoviePrice")));
        String tempTime = (c.getString(c.getColumnIndex("MoiveTime")));
        String tempAgeRating = (c.getString(c.getColumnIndex("AgeRating")));
        String VideoId = (c.getString(c.getColumnIndex("VideoId")));

        String Day = c.getString(c.getColumnIndex("MovieDate")).split("/")[0];
        String Month = c.getString(c.getColumnIndex("MovieDate")).split("/")[1];
        String Year = c.getString(c.getColumnIndex("MovieDate")).split("/")[2];

        int tempDay =(Integer.parseInt(Day));
        int tempMonth =(Integer.parseInt(Month));
        int tempYear =(Integer.parseInt(Year));
        Movie tempMovie= new Movie(tempMovieId,tempPrice,tempMovieName,tempCinema,tempAgeRating, tempTime,tempDay,tempMonth,tempYear,VideoId);


        tempmovies.add(tempMovie);
    } while (c.moveToNext());
    if (c != null && !c.isClosed()) {
        c.close();
    }

    c.close();

    return tempmovies;
}

public ArrayList<Movie> getById(SQLiteDatabase db, Movie movie){
    Cursor c = db.rawQuery("SELECT * FROM Movie WHERE MovieID='" + movie.movieId+ "'", null);
    ArrayList<Movie> tempmovies =new ArrayList();
    int x = 0;

    for(int i =0; i<c.getCount(); i++)
    {
        c.moveToPosition(i);

        int tempMovieId =(c.getInt(c.getColumnIndex("MovieID")));
        String tempMovieName = (c.getString(c.getColumnIndex("MovieTitle")));
        String tempCinema = (c.getString(c.getColumnIndex("CinemaName")));
        Double tempPrice = (c.getDouble(c.getColumnIndex("MoviePrice")));
        String tempTime = (c.getString(c.getColumnIndex("MoiveTime")));
        String tempAgeRating = (c.getString(c.getColumnIndex("AgeRating")));
        String VideoId = (c.getString(c.getColumnIndex("VideoId")));

        String Day = c.getString(c.getColumnIndex("MovieDate")).split("/")[0];
        String Month = c.getString(c.getColumnIndex("MovieDate")).split("/")[1];
        String Year = c.getString(c.getColumnIndex("MovieDate")).split("/")[2];

        int tempDay =(Integer.parseInt(Day));
        int tempMonth =(Integer.parseInt(Month));
        int tempYear =(Integer.parseInt(Year));
        Movie tempMovie= new Movie(tempMovieId,tempPrice,tempMovieName,tempCinema,tempAgeRating, tempTime,tempDay,tempMonth,tempYear,VideoId);


        tempmovies.add(tempMovie);
        x = x + 1;
    } while (c.moveToNext());
    if (c != null && !c.isClosed()) {
        c.close();
    }
    c.close();
    return tempmovies;
}

public ArrayList<Movie> getAll(SQLiteDatabase db){
    Cursor c=db.rawQuery("SELECT * FROM Movie", null);

    ArrayList<Movie> tempmovies =new ArrayList();
    int x = 0;

    for(int i =0; i<c.getCount(); i++)
    {
        c.moveToPosition(i);

        int tempMovieId =(c.getInt(c.getColumnIndex("MovieID")));
        String tempMovieName = (c.getString(c.getColumnIndex("MovieTitle")));
        String tempCinema = (c.getString(c.getColumnIndex("CinemaName")));
        Double tempPrice = (c.getDouble(c.getColumnIndex("MoviePrice")));
        String tempTime = (c.getString(c.getColumnIndex("MoiveTime")));
        String tempAgeRating = (c.getString(c.getColumnIndex("AgeRating")));
        String VideoId = (c.getString(c.getColumnIndex("VideoId")));

        String Day = c.getString(c.getColumnIndex("MovieDate")).split("/")[0];
        String Month = c.getString(c.getColumnIndex("MovieDate")).split("/")[1];
        String Year = c.getString(c.getColumnIndex("MovieDate")).split("/")[2];

        int tempDay =(Integer.parseInt(Day));
        int tempMonth =(Integer.parseInt(Month));
        int tempYear =(Integer.parseInt(Year));
        Movie tempMovie= new Movie(tempMovieId,tempPrice,tempMovieName,tempCinema,tempAgeRating, tempTime,tempDay,tempMonth,tempYear,VideoId);


        tempmovies.add(tempMovie);
        x = x + 1;
    } while (c.moveToNext());
    if (c != null && !c.isClosed()) {
        c.close();
    }
    c.close();
    return tempmovies;
}

public void delete(SQLiteDatabase db, Movie deleteMovie){
    db.execSQL("DELETE FROM Movie WHERE MovieID='" + deleteMovie.movieId + "'");
}

public void update(SQLiteDatabase db, Movie editMovie){

    String date = editMovie.dateDay + "/" + editMovie.dateMonth + "/" + editMovie.dateYear;

    db.execSQL("UPDATE Movie SET MovieID ='"+editMovie.movieId+"', MovieTitle ='"+editMovie.movieName+"',CinemaName  ='"+editMovie.cinemaName
                    +"', MoviePrice ='"+editMovie.moviePrice+"',MoiveTime ='"+editMovie.time+"' , AgeRating ='"+editMovie.ageRating+"', VideoId ='"+editMovie.videoId+"'," +
                    " MovieDate = '"+date+"' WHERE MovieID='"+editMovie.movieId+"'");


}


    public ArrayList<Movie> sortBy(SQLiteDatabase db, String order){
        Cursor c=db.rawQuery("SELECT * FROM Movie ORDER BY " + order+ "", null);

        ArrayList<Movie> tempmovies =new ArrayList();
        int x = 0;

        for(int i =0; i<c.getCount(); i++)
        {
            c.moveToPosition(i);

            int tempMovieId =(c.getInt(c.getColumnIndex("MovieID")));
            String tempMovieName = (c.getString(c.getColumnIndex("MovieTitle")));
            String tempCinema = (c.getString(c.getColumnIndex("CinemaName")));
            Double tempPrice = (c.getDouble(c.getColumnIndex("MoviePrice")));
            String tempTime = (c.getString(c.getColumnIndex("MoiveTime")));
            String tempAgeRating = (c.getString(c.getColumnIndex("AgeRating")));
            String VideoId = (c.getString(c.getColumnIndex("VideoId")));

            String Day = c.getString(c.getColumnIndex("MovieDate")).split("/")[0];
            String Month = c.getString(c.getColumnIndex("MovieDate")).split("/")[1];
            String Year = c.getString(c.getColumnIndex("MovieDate")).split("/")[2];

            int tempDay =(Integer.parseInt(Day));
            int tempMonth =(Integer.parseInt(Month));
            int tempYear =(Integer.parseInt(Year));
            Movie tempMovie= new Movie(tempMovieId,tempPrice,tempMovieName,tempCinema,tempAgeRating, tempTime,tempDay,tempMonth,tempYear,VideoId);


            tempmovies.add(tempMovie);
            x = x + 1;
        } while (c.moveToNext());
        if (c != null && !c.isClosed()) {
            c.close();
        }
        c.close();
        return tempmovies;
    }


}

