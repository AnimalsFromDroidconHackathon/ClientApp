package pl.tajchert.cracowdroidconhackathon;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Tajchert on 06.12.2015.
 */
public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.ViewHolder> {
    private ArrayList<CatItem> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(v);
        }
    }

    public CatListAdapter(ArrayList<CatItem> catItems) {
        mDataset = catItems;
    }

    @Override
    public CatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CatItem catItem = mDataset.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
