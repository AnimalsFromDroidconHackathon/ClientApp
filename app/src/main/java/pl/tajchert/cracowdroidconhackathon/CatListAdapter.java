package pl.tajchert.cracowdroidconhackathon;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        @Bind(R.id.buttonFind)
        public Button buttonFind;
        public View view;

        public ViewHolder(View v) {
            super(v);
            context = v.getContext();
            view = v;
            ButterKnife.bind(this, v);
        }
    }

    public interface AdapterCallback {
        void onPhotoRequest(CatItem catItem);
    }

    private AdapterCallback adapterCallback;




    public CatListAdapter(ArrayList<CatItem> catItems) {
        mDataset = catItems;
    }

    public void setAdapterCallback(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }

    @Override
    public CatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CatItem catItem = mDataset.get(position);
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
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogCatOptions(mDataset.get(holder.getAdapterPosition()), holder.context);
                }
            });
            holder.buttonFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DroidconApplication.firebase.child("animals").child(catItem.id).child("lost").setValue(!catItem.lost);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public void showDialogCatOptions(final CatItem catItem, final Context context) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle("Action:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.select_dialog_item);
        arrayAdapter.add("Change cat name");
        arrayAdapter.add("Change cat picture");

        builderSingle.setNegativeButton(
                "CLOSE",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        if(which == 0) {
                            changeCatName(catItem, context);
                        } else if (which == 1) {
                            if(adapterCallback != null){
                                adapterCallback.onPhotoRequest(catItem);
                            }
                        }
                    }
                });
        builderSingle.show();
    }

    private void changeCatName(final CatItem catItem, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cat name");
        final EditText input = new EditText(context);
        builder.setView(input);

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                input.getText();
                DroidconApplication.firebase.child("animals").child(catItem.id).child("name").setValue(input.getText().toString());
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
