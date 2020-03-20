package com.andrewlaurien.imovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrewlaurien.imovies.Interface.IClickListener;
import com.andrewlaurien.imovies.R;
import com.andrewlaurien.imovies.databinding.DataItemBinding;
import com.andrewlaurien.imovies.model.Track;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private ArrayList<Track> mList = new ArrayList<>();
    private Context context;

    public TrackAdapter(Context c) {
        this.context = c;
    }

    IClickListener listener;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final DataItemBinding binding;

        public ViewHolder(DataItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Track item) {
            binding.setTrack(item);


            Glide.with(context).load(item.getArtworkUrl100()).apply(RequestOptions.placeholderOf(R.drawable.ic_musical_notes).error(R.drawable.ic_musical_notes))
                    .into(binding.profile);

            binding.executePendingBindings();
        }


        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClicl(getAdapterPosition());
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DataItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.data_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = mList.get(position);
        holder.bind(track);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setItem(List<Track> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setClickListener(IClickListener ls) {
        listener = ls;
    }

    public Track getItem(int position) {
        return mList.get(position);
    }

}


