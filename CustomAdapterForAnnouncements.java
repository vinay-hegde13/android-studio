package com.shashank.platform.classroomappui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapterForAnnouncements extends RecyclerView.Adapter<CustomAdapterForAnnouncements.ViewHolder> {
    ArrayList<Announcement> announcementArrayList;
    Context context;
    boolean isStudent;
    View parent;
    DBHelper dbh = null;
    public CustomAdapterForAnnouncements(ArrayList<Announcement> arr, Context context,boolean isStudent,DBHelper dbh ){
        this.announcementArrayList = arr;
        this.context = context;
        this.isStudent=isStudent;
        this.parent = parent;
        this.dbh = dbh;
    }
    @NonNull
    @Override
    public CustomAdapterForAnnouncements.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View single_row = layoutInflater.inflate(R.layout.row_for_announcements,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(single_row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapterForAnnouncements.ViewHolder viewHolder, int position) {
Announcement an = announcementArrayList.get(position);
viewHolder.title.setText(an.getTitle());
viewHolder.content.setText(an.getContent());
viewHolder.time.setText(an.getTime());

if(!isStudent) {
    viewHolder.delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String id = dbh.getIdOfAnnouncement(an, context);

            dbh.deleteAnnouncement(id,context);
            setData(dbh.getAllAnnouncements());
        }
    });
}else{
    viewHolder.delete.setVisibility(View.GONE);

}
    }

    @Override
    public int getItemCount() {
       return announcementArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
public ImageButton delete;
public TextView time,title,content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.time = itemView.findViewById(R.id.time_of_announcement);
            this.delete = itemView.findViewById(R.id.delete_an_announcement);
            this.content= itemView.findViewById(R.id.info_for_announcement);
            this.title  = itemView.findViewById(R.id.title_for_announcement);


        }
    }
    public void setData(ArrayList<Announcement> ds) {
        this.announcementArrayList.clear();
        announcementArrayList.addAll(ds);
        notifyDataSetChanged();
    }
}
