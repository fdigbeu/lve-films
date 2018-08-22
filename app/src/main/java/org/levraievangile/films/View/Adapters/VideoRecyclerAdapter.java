package org.levraievangile.films.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.levraievangile.films.Model.Video;
import org.levraievangile.films.Presenter.MainPresenter;
import org.levraievangile.films.R;
import org.levraievangile.films.View.Interfaces.MainView;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Video> videoItems;
    private Hashtable<Integer, MyViewHolder> mViewHolder;
    private MainView.IVideo iVideo;
    private Video videoSelected;
    private MainPresenter presenter;
    private int positionSelected = -1;

    public VideoRecyclerAdapter(Context context, ArrayList<Video> videoItems, MainView.IVideo iVideo) {
        this.context = context;
        this.videoItems = videoItems;
        mViewHolder = new Hashtable<>();
        this.iVideo = iVideo;
        presenter = new MainPresenter(iVideo);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Video video = videoItems.get(position);
        mViewHolder.put(position, holder);
        holder.container.setBackgroundResource(positionSelected == position ? R.color.colorBlackOpacity15 : android.R.color.transparent);
        holder.positionItem = position;
        String dateFormat = presenter.changeFormatDate(video.getDate());
        String durationFormat = presenter.changeFormatDuration(video.getDuree());
        holder.itemTitle.setText(video.getTitre());
        holder.itemSubTitle.setText(dateFormat+" | "+durationFormat+" | "+video.getAuteur());
    }

    @Override
    public int getItemCount() {
        return videoItems.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        int positionItem;
        View container;
        TextView itemTitle;
        TextView itemSubTitle;

        public MyViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemSubTitle = itemView.findViewById(R.id.item_subtitle);

            // Event
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    videoSelected = videoItems.get(positionItem);
                    //--
                    String urlVideo = videoItems.get(positionItem).getUrlacces()+videoItems.get(positionItem).getSrc();
                    Log.i("TAG_URL", "urlVideo = "+urlVideo);
                    File file = new File(urlVideo);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "video/*");
                    context.startActivity(Intent.createChooser(intent, "Lecture { "+videoItems.get(positionItem).getTitre()+" }"));
                    //--
                    positionSelected = positionItem;
                    addFocusToContainerItemSelection(view);
                }
            });
        }
    }

    //--
    private void addFocusToContainerItemSelection(View view){
        for (int i=videoItems.size()-1; i>=0; i--){
            if(mViewHolder.containsKey(i)){
                mViewHolder.get(i).container.setBackgroundResource(android.R.color.transparent);
            }
        }
        view.setBackgroundResource(R.color.colorBlackOpacity15);
    }
}