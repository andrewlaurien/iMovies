package com.andrewlaurien.imovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.andrewlaurien.imovies.Interface.IClickListener;
import com.andrewlaurien.imovies.adapter.TrackAdapter;
import com.andrewlaurien.imovies.databinding.ActivityMainBinding;
import com.andrewlaurien.imovies.model.Track;
import com.andrewlaurien.imovies.model.TrackModel;
import com.andrewlaurien.imovies.viewmodel.TrackViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TrackViewModel trackViewModel;
    TrackAdapter adapter;
    RecyclerView recyclerView;
    Gson gson;

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;
    public static final String SHARED_PREF_NAME = "Seaches";
    public static final String Detail = "Movies";
    String Actions = "ACTIONS";
    Context context;
    String userActions = "0";

    /**
     * Click Listener for the RecyclerView
     */
    IClickListener listener = new IClickListener() {
        @Override
        public void OnItemClicl(int position) {

            if (isTabDevice) {
                DetailsFragment fragment = DetailsFragment.newInstance(adapter.getItem(position));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Track", gson.toJson(adapter.getItem(position)));
                startActivityForResult(intent, 01);
            }

            userActions += String.valueOf(position);
            Log.d("ACTIONS",""+userActions);
            saveActions(userActions);

        }
    };

    boolean isTabDevice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.item_detail_container) != null) {
            isTabDevice = true;
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        context = this;
        gson = new Gson();
        mPrefs = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        prefsEditor = mPrefs.edit();


        recyclerView = findViewById(R.id.recyclerview);
        adapter = new TrackAdapter(this);
        trackViewModel = ViewModelProviders.of(this).get(TrackViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(listener);

        getSaveInShared();
        initActions();
        displayLastActions();

    }

    /**
    * Display the last Action saved
    * */
    void displayLastActions(){

        if(userActions.length() > 1){
            int lastChar = Integer.parseInt(userActions.substring(userActions.length() - 1));
            if (isTabDevice) {
                DetailsFragment fragment = DetailsFragment.newInstance(adapter.getItem(lastChar));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Track", gson.toJson(adapter.getItem(lastChar)));
                startActivityForResult(intent, 01);
            }


        }

    }


    /**
     * User starts a search
     *
     * @param searchkey is the String that is provided by the user
     */
    void initSearch(String searchkey) {

        trackViewModel.getTracks(searchkey, "au", "movie").observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                if (tracks.size() > 0) {
                    prefsEditor.putString(Detail, gson.toJson(tracks));
                } else {
                    prefsEditor.remove(Detail);
                }
                prefsEditor.commit();
                adapter.setItem(tracks);
            }
        });
    }

    /**
     * Get the last search result by the user if there is any
     */
    void getSaveInShared() {

        Gson gson = new Gson();
        String json = mPrefs.getString(Detail, "");
        if (json.equalsIgnoreCase("")) {
            return;
        }
        Type listType = new TypeToken<List<Track>>() {
        }.getType();

        List<Track> navaids = gson.fromJson(json, listType);
        adapter.setItem(navaids);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                initSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userActions = userActions.substring(0, userActions.length() - 1);
        saveActions(userActions);
    }

    /**
     * Save the actions done by the user
     *
     * @param actions is a string that corresponds tha actions of the user
     */
    void saveActions(String actions) {
        prefsEditor.putString(Actions, actions);
        prefsEditor.commit();
    }

    /**
     * get the last actions of the user and display it on the app
     *
     */
    void initActions(){
        String strActions = mPrefs.getString(Actions, "");
        if (strActions.equalsIgnoreCase("")) {
            return;
        }

        userActions = strActions;
    }
}
