package com.example.movierating.recylerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movierating.R;
import com.example.movierating.modelclass.CastModelClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CastsAdapter extends RecyclerView.Adapter<CastsAdapter.ViewModel> {

    Context context;
    List<CastModelClass.Cast> casts = new ArrayList<>();

    public CastsAdapter(Context context, List<CastModelClass.Cast> casts) {
        this.context = context;
        this.casts = casts;
    }

    @NonNull
    @Override
    public CastsAdapter.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cast_layout,parent,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastsAdapter.ViewModel holder, int position) {
        String name = casts.get(position).getName();
        String castImage = casts.get(position).getProfile_path();
        holder.castName.setText(name);
        Picasso.get().load("https://image.tmdb.org/t/p/w200"+castImage).into(holder.castImg);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }
    public class ViewModel extends RecyclerView.ViewHolder{
        TextView castName;
        ImageView castImg;
        public ViewModel(@NonNull View itemView) {
            super(itemView);

            castImg = itemView.findViewById(R.id.cast_img);
            castName = itemView.findViewById(R.id.cast_name);
        }
    }
}
