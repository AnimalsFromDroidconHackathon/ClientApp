package pl.tajchert.cracowdroidconhackathon;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if(getIntent() != null) {
            if(getIntent().hasExtra("id")) {
                String catId = getIntent().getStringExtra("id");
                if(catId != null && catId.length() > 0 ) {
                    DroidconApplication.firebase.child("animals").child(catId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "onDataChange: ");
                            if(dataSnapshot.hasChild("lat") && dataSnapshot.hasChild("lnt")) {
                                Double lat = (Double) dataSnapshot.child("lat").getValue();
                                Double lnt = (Double) dataSnapshot.child("lnt").getValue();
                                setMapLocation(lat, lnt);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Log.d(TAG, "onCancelled: ");
                        }
                    });
                }
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("CAT!"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void setMapLocation(Double lat, Double lnt) {
        if(mMap == null || lat == null || lnt == null) {
            return;
        }
        LatLng sydney = new LatLng(lat, lnt);
        mMap.addMarker(new MarkerOptions().position(sydney).title("CAT!\n"+ (System.currentTimeMillis()/1000)));
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        mMap.animateCamera(zoom);
    }
}
