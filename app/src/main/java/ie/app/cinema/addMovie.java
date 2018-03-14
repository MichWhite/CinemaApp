package ie.app.cinema;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;

import ie.app.models.Movie;
import ie.app.database.db_connector;


public class addMovie extends db_connector {
    public EditText movieTitle;
    public EditText cinemaId;
    public EditText priceId;
    public EditText timeBox;
    public RadioGroup ageRating;
    public DatePicker datePicker;
    public Cinema cinema = new Cinema();

    public  db_connector db;
    public  SQLiteDatabase DB;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_movie);
        movieTitle = (EditText) findViewById(R.id.movieId);
        cinemaId = (EditText) findViewById(R.id.cinemaId);
        priceId = (EditText) findViewById(R.id.priceId);
        timeBox = (EditText) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        ageRating = (RadioGroup) findViewById(R.id.ageRating);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        db =new db_connector();
        DB = openOrCreateDatabase("MovieDB", Context.MODE_PRIVATE, null);
        DB.execSQL("CREATE TABLE IF NOT EXISTS Movie(MovieID VARCHAR, MovieTitle VARCHAR,CinemaName VARCHAR," +
                "MoviePrice DOUBLE,MoiveTime VARCHAR, AgeRating VARCHAR, MovieDate VARCHAR, VideoId VARCHAR);");


    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public void saveButtonPressed(View view) {
        int movieId = getAll(DB).size() + 1;
        if (priceId.getText().toString().equals("")) {
            priceId.setError("Please Enter a valid price");
            priceId.isFocused();
        }
        if (cinemaId.getText().toString().equals("")) {
            cinemaId.setError("Please Enter a Cinema Name");
            cinemaId.isFocused();
        }
        if (movieTitle.getText().toString().equals("")) {
            movieTitle.setError("Please Enter a Movie Title");
            movieTitle.isFocused();
        }
        if (timeBox.getText().toString().equals("")) {
            timeBox.setError("Please Enter a valid time");
            timeBox.isFocused();
        }

        if (!priceId.getText().toString().equals("") && !cinemaId.getText().toString().equals("") && !movieTitle.getText().toString().equals("")) {
            String restriction = "";

            if (ageRating.getCheckedRadioButtonId() == R.id.radioPG) {
                restriction = "PG";
            } else if (ageRating.getCheckedRadioButtonId() == R.id.radio12A) {
                restriction = "12A";
            } else if (ageRating.getCheckedRadioButtonId() == R.id.radio15A) {
                restriction = "15A";
            } else if (ageRating.getCheckedRadioButtonId() == R.id.radio16A) {
                restriction = "16A";
            } else if (ageRating.getCheckedRadioButtonId() == R.id.radio18) {
                restriction = "18";
            }

            for (int i = 0; i < getAll(DB).size(); i++) {
                if (getAll(DB).get(i).movieId == movieId) {
                    movieId = movieId + 1;
                }
            }
            double price = round(Double.parseDouble(priceId.getText().toString()), 2);
            String cinemaName = cinemaId.getText().toString();
            String movieName = movieTitle.getText().toString();
            int timeHour = 00;
            int timeMinute = 00;
            if (timeBox.getText().toString().length() == 5 && timeBox.getText().toString().charAt(2) == ((Character) ':')) {
                timeHour = Integer.parseInt(timeBox.getText().toString().split(":")[0]);
                timeMinute = Integer.parseInt(timeBox.getText().toString().split(":")[1]);

                if (timeHour < 25 && timeHour != 0 && timeMinute < 60) {
                    String time = timeBox.getText().toString();

                    int dateDay = datePicker.getDayOfMonth();
                    int dateMonth = datePicker.getMonth() + 1;
                    int dateYear = datePicker.getYear();
                    String videoId =null;
                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                          videoId = getVideoId(movieName);
                         }

                        Movie tempMovie = new Movie(movieId, price, movieName, cinemaName, restriction, time, dateDay, dateMonth, dateYear, videoId);

                        insert(DB, tempMovie);


                        Toast.makeText(this, "Movie has been saved!", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(this, Cinema.class));


                    } else {
                        timeBox.setText("");
                        Toast.makeText(this, "Time must be in 24 hour format", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    timeBox.setText("");
                    Toast.makeText(this, "Time must be in 24 hour format", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(this, "Values are missing! Please enter a mising values", Toast.LENGTH_SHORT).show();

        }
    }




    public void clearButtonPressed(View view) {

        cinemaId.setText("");

        priceId.setText("");
        movieTitle.setText("");
        timeBox.setText("");
        movieTitle.isFocused();
        Toast.makeText(this, "Movie details  been cleared!", Toast.LENGTH_SHORT).show();

    }


public static String getVideoId(String movieTitle){

    String movieVideoId = null;    String Title;
    if(movieTitle.contains(" ")){
        Title  = movieTitle.replace(" ", "+");
    }
    else{
        Title  = movieTitle;
    }

    try{
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=" + Title + "+offical+trailer&key=AIzaSyDVOcml_uuk5EYwXlY4l97OcF__aDYoM7Q";

        Document doc = Jsoup.connect(url).ignoreContentType(true).get();

        String getJson = doc.text();
        JSONObject jsonObject = (JSONObject) new JSONTokener(getJson).nextValue();

        JSONObject items = jsonObject.getJSONArray("items").getJSONObject(0);
        movieVideoId = items.getJSONObject("id").get("videoId").toString();


    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (JSONException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return movieVideoId;



}





}
