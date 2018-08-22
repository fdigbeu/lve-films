package org.levraievangile.films.Presenter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.levraievangile.films.Model.Video;
import org.levraievangile.films.R;
import org.levraievangile.films.View.Interfaces.MainView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainAsyntask extends AsyncTask<Void, Void, ArrayList<Video>>{

    private Context context;
    private ArrayList<Video> videos;
    private String urlVideo;
    private MainView.IPresenter iPresenter;
    private HttpURLConnection urlConnection;

    @Override
    protected ArrayList<Video> doInBackground(Void... voids) {
        urlVideo = context.getResources().getString(R.string.urlAdminVideos);
        videos = new ArrayList<>();
        //--
        StringBuilder responseHttp =  new StringBuilder();

        try {
            URL url = new URL(urlVideo);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 1.6; en-us; GenericAndroidDevice) AppleWebKit/528.5+ (KHTML, like Gecko) Version/3.1.2 Mobile Safari/525.20.1");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            // If connection failed
            if (urlConnection.getResponseCode() != 200) {
                return iPresenter.getVideosData(context);
            }
            //--
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null){
                responseHttp.append(line);
            }

        }
        catch (Exception e) {
            return iPresenter.getVideosData(context);
        }
        finally {
            urlConnection.disconnect();
        }
        //--

        try {
            JSONArray results = new JSONArray(responseHttp.toString());
            for (int i = 0; i < results.length(); i++){
                JSONObject jsonObject = results.getJSONObject(i);
                Video video = new Video();
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
            }
        }
        catch (JSONException ex) {
            return iPresenter.getVideosData(context);
        }
        //--
        return videos;
    }

    @Override
    protected void onPostExecute(ArrayList<Video> videos) {
        super.onPostExecute(videos);
        iPresenter.saveVideosData(context, videos.toString());
        iPresenter.onLoadVideoFinished(videos);
    }

    public void initialize(Context context, MainView.IPresenter iPresenter){
        this.context = context;
        this.iPresenter = iPresenter;
    }
}
