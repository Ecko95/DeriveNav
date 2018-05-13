package com.example.joshua.derivenav.NewTrip.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.example.joshua.derivenav.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<DestinationModel> modelList;
    private OnItemClickListener mItemClickListener;

    public MapRecyclerViewAdapter(Context context, ArrayList<DestinationModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<DestinationModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged(); //refreshes adapter

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.map_item_recycler_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final DestinationModel model = getItem(position); //MapModel
            ViewHolder genericViewHolder = (ViewHolder) holder;

            //bind DestinationList of Locations to Horizontal View

            //TEST
            //genericViewHolder.itemTxtTitle.setText(model.getTitle());
            //genericViewHolder.itemTxtMessage.setText(model.getMessage());

            //Uncomment for real data
            //Places of Interest Model
            genericViewHolder.itemTxtTitle.setText(model.getPoints_of_interest().get(position).getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getPoints_of_interest().get(position).getDetails().getShort_description());
            Picasso.with(genericViewHolder.imgItem.getContext()).load(model.getPoints_of_interest().get(position).getMain_image()).into(genericViewHolder.imgItem);

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private DestinationModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, DestinationModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item)
        ImageView imgItem;
        @BindView(R.id.item_txt_title)
        TextView itemTxtTitle;
        @BindView(R.id.item_txt_message)
        TextView itemTxtMessage;
        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });

        }
    }

    public void addDestinations(DestinationModel destinationModel) {
        modelList.add(destinationModel);
        notifyDataSetChanged(); //refreshes adapter
    }

}

