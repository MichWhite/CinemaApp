package ie.app.cinema;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import ie.app.models.Movie;
import ie.app.database.db_connector;
import ie.app.models.Movie;
import ie.app.cinema.Cinema;

public class viewMovie extends db_connector {
    public Cinema cinema = new Cinema();

    public ListView listView;
    public MovieAdapter adapter;
    public int position = 0;
    public ArrayList<Movie> searchedMovies = new ArrayList<Movie>();
    public EditText search_Bar;
    public SQLiteDatabase DB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DB = openOrCreateDatabase("MovieDB", Context.MODE_PRIVATE, null);
        DB.execSQL("CREATE TABLE IF NOT EXISTS Movie(MovieID VARCHAR, MovieTitle VARCHAR,CinemaName VARCHAR," +
                "MoviePrice DOUBLE,MoiveTime VARCHAR, AgeRating VARCHAR, MovieDate VARCHAR, VideoId VARCHAR);");

        if (getAll(DB).isEmpty()) {
            startActivity(new Intent(this, Cinema.class));
        }

        adapter = new MovieAdapter(this, getAll(DB));
        searchedMovies.clear();

        listView = (ListView) findViewById(R.id.movieList);
        listView.setAdapter(adapter);

        Toast.makeText(this, "Tap movie Details to edit!!", Toast.LENGTH_SHORT).show();

        search_Bar = (EditText) findViewById(R.id.searchBar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.by_movieId:
                searchedMovies.clear();
                searchedMovies = sortBy(DB, "MovieID");
                listView.setAdapter(new MovieAdapter(this,  searchedMovies));
                return true;

            case R.id.by_movieTitle:
                searchedMovies.clear();
                searchedMovies = sortBy(DB, "MovieTitle");
                listView.setAdapter(new MovieAdapter(this,  searchedMovies));
                return true;

            case R.id.by_time:
                searchedMovies.clear();
                searchedMovies = sortBy(DB, "MoiveTime");
                listView.setAdapter(new MovieAdapter(this,  searchedMovies));
                return true;

            case R.id.by_cinema:
                searchedMovies.clear();
                searchedMovies = sortBy(DB, "CinemaName");
                listView.setAdapter(new MovieAdapter(this,  searchedMovies));
                return true;
            default:
                return super.onOptionsItemSelected(item);



    }

    }


public void viewTrailer(View v){
    List<Movie> tempMovies;
    if(searchedMovies.isEmpty()) {
        tempMovies = getAll(DB);

    }else{
        tempMovies = searchedMovies;
    }
    position = listView.getPositionForView((View) v.getParent());
    Movie movie = tempMovies.get(position);
    Intent i = new Intent(this, trailerViewer.class);
    i.putExtra("search", movie.videoId);
    startActivity(i);
}

    public void deleteButtonPressed(View view){
        final List<Movie> tempMovies;
        if(searchedMovies.isEmpty()) {
            tempMovies = getAll(DB);

        }else{
            tempMovies = searchedMovies;
            }

            position = listView.getPositionForView((View) view.getParent());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setPositiveButton(R.string.DeleteButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(!searchedMovies.isEmpty()) {

                        delete(DB, tempMovies.get(position));
//                        movies.remove(tempMovies.get(position));
                    }

                    delete(DB, tempMovies.get(position));
//                    adapter.remove(tempMovies.get(position));
                    Toast.makeText(viewMovie.this, "Movie details have been deleted!", Toast.LENGTH_SHORT).show();
                    listView.invalidateViews();
                    startActivity(new Intent(viewMovie.this, viewMovie.class));
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(viewMovie.this, "Movie details have not been deleted!", Toast.LENGTH_SHORT).show();
                    listView.invalidateViews();
                    startActivity(new Intent(viewMovie.this, viewMovie.class));
                }
            });

            AlertDialog dialog = builder.create();
            dialog.setTitle("Are You Sure You Want To Delete "+tempMovies.get(position).movieName+" Details?");
            dialog.show();

}

    public void onBackPressed(){
        startActivity(new Intent(viewMovie.this, Cinema.class));

    }

    public void editButtonPressed(View view){
        final Movie editMovie;
        if(searchedMovies.isEmpty()){



            position = listView.getPositionForView((View) view.getParent());

            editMovie = getAll(DB).get(position);

        }
        else{
            position = listView.getPositionForView((View) view.getParent());
            editMovie = searchedMovies.get(position);
        }


        setContentView(R.layout.content_add_movie);
        Button saveButtonPressed = (Button) findViewById(R.id.saveButton);
        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setEnabled(false);

        final EditText movieTitle = (EditText) findViewById(R.id.movieId);
        final EditText cinemaId = (EditText) findViewById(R.id.cinemaId);
        final EditText priceId = (EditText) findViewById(R.id.priceId);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
       final RadioGroup ageRating = (RadioGroup) findViewById(R.id.ageRating);
        final EditText timeId = (EditText) findViewById(R.id.timePicker);

        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        movieTitle.setText(editMovie.movieName);
        cinemaId.setText(editMovie.cinemaName);
        timeId.setText(editMovie.time);
        Double moviePrice  =editMovie.moviePrice;

        int dateDay  =editMovie.dateDay;
        int dateMonth  =editMovie.dateMonth -1;
        int dateYear = editMovie.dateYear;

        datePicker.updateDate(dateYear, dateMonth, dateDay);
        priceId.setText(moviePrice.toString());


        if(editMovie.ageRating.equals("PG")){
            ageRating.check(R.id.radioPG);

        }
        else if(editMovie.ageRating.equals("12A")){
            ageRating.check(R.id.radio12A);
        }
        else if(editMovie.ageRating.equals("15A")){
            ageRating.check(R.id.radio15A);
        }
        else if(editMovie.ageRating.equals("16A")){
            ageRating.check(R.id.radio16A);
        }
        else if(editMovie.ageRating.equals("18")){
            ageRating.check(R.id.radio18);
        }


        saveButtonPressed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

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
                if (timeId.getText().toString().equals("")) {
                    timeId.setError("Please Enter a valid time");
                    timeId.isFocused();
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
                    String movieName = movieTitle.getText().toString();
                    String cinemaName = cinemaId.getText().toString();
                    double price = Double.parseDouble(priceId.getText().toString());
                    int timeHour = 00;
                    int timeMinute = 00;
                    if (timeId.getText().toString().length() == 5 && timeId.getText().toString().charAt(2) == ((Character) ':')) {
                        timeHour = Integer.parseInt(timeId.getText().toString().split(":")[0]);
                        timeMinute = Integer.parseInt(timeId.getText().toString().split(":")[1]);

                        if (timeHour < 25 && timeHour != 0 && timeMinute < 60) {
                            String time = timeId.getText().toString();
                            int dateDay = datePicker.getDayOfMonth();
                            int dateMonth = datePicker.getMonth() + 1;
                            int dateYear = datePicker.getYear();

                            editMovie.setAgeRating(restriction);
                            editMovie.setCinemaName(cinemaName);
                            editMovie.setMovieName(movieName);
                            editMovie.setTime(time);
                            editMovie.setDateDay(dateDay);
                            editMovie.setDateMonth(dateMonth);
                            editMovie.setDateYear(dateYear);
                            editMovie.setmoviePrice(price);
                            editMovie.setVideoId(addMovie.getVideoId(movieName));

                            update(DB, editMovie);

                            startActivity(new Intent(viewMovie.this, viewMovie.class));


                        } else {
                            timeId.setText("");
                            Toast.makeText(viewMovie.this, "Time must be in 24 hour format", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        timeId.setText("");
                        Toast.makeText(viewMovie.this, "Time must be in 24 hour format", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(viewMovie.this, "Values are missing! Please enter a mising values", Toast.LENGTH_SHORT).show();
                }

            }
    });

}


public void searchButton(View view){
    searchedMovies.clear();

        String searchItem = search_Bar.getText().toString().toLowerCase();
        Boolean found = false;



        searchedMovies = getByTitle(DB,searchItem);



        if (searchedMovies.size()>0){
            found = true;
        }

    if(!found) {
        Toast.makeText(this, "Movie Not Found!", Toast.LENGTH_SHORT).show();
    }

    adapter = new MovieAdapter(this,  searchedMovies);
    listView = (ListView) findViewById(R.id.movieList);
    listView.setAdapter(adapter);

}

    public void showAll(View view){
        searchedMovies.clear();
        if(!search_Bar.getText().toString().equals("")) {
            search_Bar.setText("");
        }
        adapter = new MovieAdapter(this,  getAll(DB));

        listView = (ListView) findViewById(R.id.movieList);
        listView.setAdapter(adapter);
    }

}




class MovieAdapter extends ArrayAdapter<Movie>
{
    private Context        context;
    public List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies)
    {
        super(context, R.layout.row_movie, movies);
        this.context   = context;
        this.movies = movies;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View     view       = inflater.inflate(R.layout.row_movie, parent, false);
        Movie Movie   = movies.get(position);

        TextView ageRatingView = (TextView) view.findViewById(R.id.row_age_rating);
        TextView titleView = (TextView) view.findViewById(R.id.row_title);
        TextView cinemaView = (TextView) view.findViewById(R.id.row_cinema);
        TextView dateView = (TextView) view.findViewById(R.id.row_date);
        TextView timeView = (TextView) view.findViewById(R.id.row_time);
        TextView priceView = (TextView) view.findViewById(R.id.row_price);
        TextView movieIdView = (TextView) view.findViewById(R.id.row_movieId);

        ageRatingView.setText(Movie.ageRating);
        titleView.setText(Movie.movieName);
        cinemaView.setText(Movie.cinemaName);
        dateView.setText(Movie.dateDay + "/" + Movie.dateMonth + "/" + Movie.dateYear);
        timeView.setText(Movie.time);
        priceView.setText("€ " + Movie.moviePrice);
        movieIdView.setText(Integer.toString(Movie.movieId));

        return view;
    }

    @Override
    public int getCount()
    {
        return movies.size();
    }
}