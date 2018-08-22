package org.levraievangile.films.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.levraievangile.films.Model.Video;
import org.levraievangile.films.R;
import org.levraievangile.films.View.Interfaces.MainView;

import java.util.ArrayList;
import java.util.Date;

public class MainPresenter implements MainView.IPresenter{

    private MainView.IVideo iVideo;
    private MainAsyntask mainAsyntask;

    public MainPresenter(MainView.IVideo iVideo) {
        this.iVideo = iVideo;
    }

    @Override
    public void loadVideoData(Context context) {
        try {
            if(iVideo != null && context != null) {
                iVideo.initialize();
                iVideo.events();
                //--
                if(isMobileConnected(context)) {
                    iVideo.progressBarVisibility(View.VISIBLE);

                    mainAsyntask = new MainAsyntask();
                    mainAsyntask.initialize(context, this);
                    mainAsyntask.execute();
                }
                else{
                    ArrayList<Video> videos = getVideosData(context);
                    onLoadVideoFinished(videos);
                }
            }
        }
        catch (Exception ex){
            Log.e("TAG_ERROR", "MainPresenter-->loadVideoData() : "+ex.getMessage());
        }
    }

    @Override
    public void onLoadVideoFinished(ArrayList<Video> videos) {
        try {
            if(iVideo != null && videos != null && videos.size() > 0){
                iVideo.progressBarVisibility(View.GONE);
                iVideo.loadVideoData(videos, 1);
            }
        }
        catch (Exception ex){
            Log.e("TAG_ERROR", "MainPresenter-->onLoadVideoFinished() : "+ex.getMessage());
        }
    }

    @Override
    public void onLoadVideoFailed(String message) {
        try {
            if(iVideo != null) {
                iVideo.progressBarVisibility(View.GONE);
            }
        }
        catch (Exception ex){
            Log.e("TAG_ERROR", "MainPresenter-->onLoadVideoFailed() : "+ex.getMessage());
        }
    }

    @Override
    public void saveVideosData(Context context, String contentData) {
        try {
            saveDataInSharePreferences(context, "VIDEOS_DATA", contentData);
        }
        catch (Exception ex){
            Log.e("TAG_ERROR", "MainPresenter-->saveVideosData() : "+ex.getMessage());
        }
    }

    @Override
    public ArrayList<Video> getVideosData(Context context) {
        try {
            if (iVideo != null && context != null){
                ArrayList<Video> videos = null;
                String jsonString = getDataFromSharePreferences(context, "VIDEOS_DATA");
                if(jsonString != null && !jsonString.isEmpty()){
                    videos = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(jsonString.trim().replace(",}", "}"));
                    if(jsonArray.length() > 0){
                        for(int i = 0; i < jsonArray.length(); i++){
                            Video video = new Video();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            video.setId(Integer.parseInt(jsonObject.getString("id")));
                            video.setTitre(jsonObject.getString("titre"));
                            video.setDuree(jsonObject.getString("duree"));
                            video.setSrc(jsonObject.getString("src"));
                            video.setDate(jsonObject.getString("date"));
                            video.setType_libelle(jsonObject.getString("type_libelle"));
                            video.setType_shortcode(jsonObject.getString("type_shortcode"));
                            video.setAuteur(jsonObject.getString("auteur"));
                            video.setUrlacces(jsonObject.getString("urlacces"));
                            videos.add(video);
                            videos.add(video);
                        }
                    }
                }
                return videos;
            }
        }
        catch (Exception ex){
            Log.e("TAG_ERROR", "MainPresenter-->getVideosData() : "+ex.getMessage());
        }
        return null;
    }

    /**
     * Change format date : Year - Month - Day
     * @param date
     * @return
     */
    @Override
    public String changeFormatDate(String date){
        String dateReturn = null;
        if(date.contains("-")){
            dateReturn = date.split("-")[2].trim()+"/"+date.split("-")[1].trim()+"/"+date.split("-")[0].trim();
        }
        else{
            try {
                long longTime = Long.parseLong(date);
                dateReturn = DateFormat.format("MM/dd/yyyy", new Date(longTime)).toString();
            }
            catch (Exception ex){}
        }
        return dateReturn;
    }

    /**
     * Change formt duration
     * @param duration
     * @return
     */
    @Override
    public String changeFormatDuration(String duration){
        String tagDuree[] = duration.split(":");
        return ((Integer.parseInt(tagDuree[0])*60)+Integer.parseInt(tagDuree[1]))+"min";
    }

    /**
     * Save data in share preferences
     * @param context
     * @param key
     * @param contentData
     */
    private void saveDataInSharePreferences(Context context, String key, String contentData){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, contentData);
            editor.commit();
        }
        catch (Exception ex){
            Log.e("TAG_ERROR", "MainPresenter-->saveDataInSharePreferences() : "+ex.getMessage());
        }
    }


    /**
     * Retrieve data in share preferences
     * @param context
     * @param key
     * @return
     */
    private String getDataFromSharePreferences(Context context, String key){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        }
        catch (Exception ex){
            Log.e("TAG_ERROR", "MainPresenter-->getDataFromSharePreferences() : "+ex.getMessage());
        }
        return null;
    }

    /**
     * Is mobile connected
     * @param context
     * @return
     */
    private boolean isMobileConnected(Context context){
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.getType() == networkType) return true;
            }
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }
}
