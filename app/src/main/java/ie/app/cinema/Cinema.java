
    package ie.app.cinema;

    import android.app.AlertDialog;
    import android.app.LauncherActivity;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.support.design.widget.NavigationView;
    import android.support.v4.view.GravityCompat;
    import android.support.v4.widget.DrawerLayout;
    import android.support.v7.app.ActionBarDrawerToggle;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.view.MenuItem;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.ScrollView;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.util.ArrayList;
    import java.util.List;

    import ie.app.database.db_connector;
    import ie.app.models.Movie;

    public class Cinema extends AppCompatActivity {

        public Button Viewbutton;
        public Button trailerbutton;
        public Toolbar toolbar;
        public SQLiteDatabase DB;
        public db_connector db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cinema);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            db = new db_connector();
            DB = openOrCreateDatabase("MovieDB", Context.MODE_PRIVATE, null);
            DB.execSQL("CREATE TABLE IF NOT EXISTS Movie(MovieID VARCHAR, MovieTitle VARCHAR,CinemaName VARCHAR," +
                    "MoviePrice DOUBLE,MoiveTime VARCHAR, AgeRating VARCHAR, MovieDate VARCHAR, VideoId VARCHAR);");

            Viewbutton = (Button) findViewById(R.id.Viewbutton);

            if (db.getAll(DB).isEmpty()) {
                Viewbutton.setEnabled(false);
                Viewbutton.setText("View Movies " + 0);

            }
            else{
                Viewbutton.setText("View Movies " + db.getAll(DB).size());

            }


        }



        public void addMoviePressed(View view) {
            startActivity(new Intent(this, addMovie.class));

        }

        public void viewMoviePressed(View view) {
            startActivity(new Intent(this, viewMovie.class));
        }

    }