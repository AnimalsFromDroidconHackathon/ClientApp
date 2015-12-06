package pl.tajchert.cracowdroidconhackathon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentCatList extends Fragment {
    private static final String TAG = FragmentCatList.class.getSimpleName();

    @Bind(R.id.recyclerCatList)
    RecyclerView mRecyclerCatList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CatItem> catItems;

    public FragmentCatList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main, container, false);
        ButterKnife.bind(this, view);
        catItems = new ArrayList<>();
        getOwnCats();
        mRecyclerCatList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerCatList.setLayoutManager(mLayoutManager);
        mAdapter = new CatListAdapter(catItems);
        mRecyclerCatList.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getOwnCats();
    }

    private void getOwnCats() {
        if(DroidconApplication.firebase != null) {
            DroidconApplication.firebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange: ");
                    refreshCatList(dataSnapshot);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d(TAG, "onCancelled: ");
                }
            });
        }
    }

    private void refreshCatList(DataSnapshot dataSnapshot) {
        catItems.clear();
        String deviceId = DroidconApplication.telecomManager.getDeviceId();
        if (dataSnapshot != null && dataSnapshot.hasChild("animals")) {
            for (DataSnapshot data : dataSnapshot.child("animals").getChildren()) {
                addCatToList(deviceId, data);
            }
        }
    }

    private void addCatToList(String deviceId, DataSnapshot data) {
        String catOwner = null;
        if(data.hasChild("ownerId")) {
            catOwner = data.child("ownerId").getValue().toString();
        }
        if(catOwner != null && catOwner.equals(deviceId)) {
            final CatItem catItem = data.getValue(CatItem.class);
            catItem.id = data.getKey();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    catItems.add(catItem);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
