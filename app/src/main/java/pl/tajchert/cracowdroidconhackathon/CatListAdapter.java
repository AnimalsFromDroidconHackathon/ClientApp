package pl.tajchert.cracowdroidconhackathon;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tajchert on 06.12.2015.
 */
public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.ViewHolder> {
    private ArrayList<CatItem> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        @Bind(R.id.kotelIcon)
        public ImageView catProfile;
        @Bind(R.id.catName)
        public TextView catName;
        @Bind(R.id.statusText)
        public TextView statusText;

        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            ButterKnife.bind(this, v);
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
        if(catItem != null) {
            if(catItem.pictureUrl != null && catItem.pictureUrl.length() > 0) {
                Glide.with(holder.context).load(catItem.pictureUrl).into(holder.catProfile);
            }
            if(catItem.name != null) {
                holder.catName.setText(catItem.name);
            }
            if(catItem.lost) {
                holder.statusText.setText("LOST");
                holder.statusText.setTextColor(Color.RED);
            } else {
                holder.statusText.setText("NOT LOST");
                holder.statusText.setTextColor(Color.GREEN);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
