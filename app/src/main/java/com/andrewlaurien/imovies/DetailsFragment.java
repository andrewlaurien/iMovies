package com.andrewlaurien.imovies;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrewlaurien.imovies.databinding.FragmentDetailsBinding;
import com.andrewlaurien.imovies.model.Track;
import com.andrewlaurien.imovies.model.TrackModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static Track selectedTrack;

    public static DetailsFragment newInstance(Track track) {


        DetailsFragment fragment = new DetailsFragment();
        fragment.selectedTrack = track;
        return fragment;
    }

    FragmentDetailsBinding binding;
    View view;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        view = binding.getRoot();

        context = getActivity();


        binding.trackname.setText(selectedTrack.getTrackName());
        binding.description.setText(selectedTrack.getLongDescription());
        Glide.with(context).load(selectedTrack.getArtworkUrl100()).apply(RequestOptions.placeholderOf(R.drawable.ic_musical_notes).error(R.drawable.ic_musical_notes))
                .into(binding.profile);

        return view;
    }

}
