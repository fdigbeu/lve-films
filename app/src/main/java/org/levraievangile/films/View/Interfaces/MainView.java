package org.levraievangile.films.View.Interfaces;

import android.content.Context;

import org.levraievangile.films.Model.Video;

import java.util.ArrayList;

public class MainView {

    // MainActivity's interface
    public interface IVideo{
        public void initialize();
        public void events();
        public void loadVideoData(ArrayList<Video> videos, int numberColumns);
        public void closeActivity();
        public void progressBarVisibility(int visibility);
    }

    // Presenter's interface
    public interface IPresenter{
        public void loadVideoData(Context context);
        public void onLoadVideoFinished(ArrayList<Video> videos);
        public void onLoadVideoFailed(String message);
        public void saveVideosData(Context context, String contentData);
        public ArrayList<Video> getVideosData(Context context);
        public String changeFormatDate(String date);
        public String changeFormatDuration(String duration);
    }
}
