package pl.tajchert.cracowdroidconhackathon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentCatList extends Fragment {

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
        catItems.add(new CatItem());
        catItems.add(new CatItem());
        catItems.add(new CatItem());
        catItems.add(new CatItem());
        catItems.add(new CatItem());
        catItems.add(new CatItem());
        catItems.add(new CatItem());
        catItems.add(new CatItem());
        catItems.add(new CatItem());
        mRecyclerCatList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerCatList.setLayoutManager(mLayoutManager);
        mAdapter = new CatListAdapter(catItems);
        mRecyclerCatList.setAdapter(mAdapter);
        return view;
    }
}
