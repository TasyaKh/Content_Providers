package com.example.content_providers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private ArrayList<Images> images;
    private Context context;
    private int lastPositionAppear = -1;

    StateAdapter(Context context,Cursor cursor) {
        images = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        fromCursorToImages(cursor);
    }

    public void fromCursorToImages(Cursor cursor){
        cursor.moveToFirst();

        while(!cursor.isLast()){
            images.add(new Images(cursor.getString(1)));
            cursor.moveToNext();
        }
    }

    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        String namePerson = images.get(position).getTxt();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, holder.getLayoutPosition() + "  " +holder.getLayoutPosition() +" "
                        + holder.namePerson.getText().toString(), Toast.LENGTH_SHORT).show();
                images.remove(holder.getLayoutPosition());

                notifyItemRemoved(holder.getLayoutPosition());
                notifyItemRangeChanged(holder.getLayoutPosition(), images.size());
            }
        });

        holder.namePerson.setText(position + " " +namePerson);
        setAnimation(holder.itemView, position);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPositionAppear)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPositionAppear = position;
        }else{
            lastPositionAppear--;
        }
    }


    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView img;
        final TextView namePerson;

        ViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.imageView);
            namePerson = view.findViewById(R.id.textView3);
        }
    }
}

class Images{
    private String txt;
    //private String txt;


    public Images(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }
}