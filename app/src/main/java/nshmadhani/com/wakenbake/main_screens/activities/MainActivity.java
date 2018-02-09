package nshmadhani.com.wakenbake.main_screens.activities;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

import java.util.ArrayList;
import java.util.List;

import nshmadhani.com.wakenbake.R;
import nshmadhani.com.wakenbake.main_screens.adapters.PlacesListAdapter;
import nshmadhani.com.wakenbake.main_screens.models.Places;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = MainActivity.class.getSimpleName();


    private List<Places> mPlacesList;
    private RecyclerView mPlacesRecyclerView;

    private PlacesListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlacesList = new ArrayList<>();
        mPlacesRecyclerView  = findViewById(R.id.rv);

        mPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mListAdapter = new PlacesListAdapter(mPlacesList);


        mPlacesRecyclerView.setAdapter(mListAdapter);

        Location location  = (Location) getIntent().getExtras().get("location");


        Log.d(TAG, "onCreate: "+location);

        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.google_api_key)).build();
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest(geoApiContext);

        nearbySearchRequest
                .location(new LatLng(location.getLatitude(), location.getLongitude()))
                .radius(1000)
                .type(PlaceType.FOOD)
                .openNow(true)
                .setCallback(new PendingResult.Callback<PlacesSearchResponse>() {
                    @Override
                    public void onResult(PlacesSearchResponse result) {
                        for (PlacesSearchResult place : result.results) {
                            Places places =  new Places();
                            places.setName(place.name);
                            Log.d(TAG, "onResult: "+places.getName());
                            mPlacesList.add(places);
                        }
                        Log.d(TAG, "onResult: "+mPlacesList.size());
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    @Override
                    public void onFailure(Throwable e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }

    }
