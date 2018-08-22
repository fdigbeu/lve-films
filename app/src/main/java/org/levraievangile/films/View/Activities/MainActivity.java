package org.levraievangile.films.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import org.levraievangile.films.Model.Video;
import org.levraievangile.films.Presenter.MainPresenter;
import org.levraievangile.films.R;
import org.levraievangile.films.View.Adapters.VideoRecyclerAdapter;
import org.levraievangile.films.View.Interfaces.MainView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView.IVideo{

    private RecyclerView videoRecyclerView;
    private ProgressBar videoProgressBar;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--
        presenter = new MainPresenter(this);
        presenter.loadVideoData(MainActivity.this);
    }

    @Override
    public void initialize() {
        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        videoProgressBar = findViewById(R.id.videoProgressBar);
    }

    @Override
    public void events() {

    }

    @Override
    public void progressBarVisibility(int visibility){
        videoProgressBar.setVisibility(visibility);
    }

    @Override
    public void loadVideoData(ArrayList<Video> videos, int numberColumns) {
        GridLayoutManager gridLayout = new GridLayoutManager(MainActivity.this, numberColumns);
        videoRecyclerView.setLayoutManager(gridLayout);
        videoRecyclerView.setHasFixedSize(true);
        VideoRecyclerAdapter adapter = new VideoRecyclerAdapter(MainActivity.this, videos, this);
        videoRecyclerView.setAdapter(adapter);
    }

    @Override
    public void closeActivity() {
        this.finish();
    }
}
