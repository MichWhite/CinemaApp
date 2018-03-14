package ie.app.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class trailerViewer extends YouTubeBaseActivity {
    public Button playVideo;
    public String movieId = null;
    private YouTubePlayerView youtubeVideo;
    private YouTubePlayer.OnInitializedListener youtubeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.trailer_viewer);
            youtubeVideo = (YouTubePlayerView) findViewById(R.id.Youtubeview);
            playVideo = (Button) findViewById(R.id.playVideo);


            playVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    play();
                    youtubeVideo.initialize("AIzaSyBr2QywHmr-yotVZD8jShb54keWKMZjwjU", youtubeListener);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play(){
        youtubeListener = new YouTubePlayer.OnInitializedListener() {
            Intent intent = getIntent();
            String VideoId = intent.getStringExtra("search");
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(VideoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }

        };
    }

}
