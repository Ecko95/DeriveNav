package com.example.joshua.derivenav.NewTrip.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.example.joshua.derivenav.NewTrip.Models.MapModel;
import com.example.joshua.derivenav.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class MapRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    //    private ArrayList<MapModel> modelList;
    private ArrayList<DestinationModel> modelList;

    private OnItemClickListener mItemClickListener;


    public MapRecyclerViewAdapter(Context context, ArrayList<DestinationModel> modelList) { //MapModel
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<DestinationModel> modelList) { //MapModel
        this.modelList = modelList;
        notifyDataSetChanged();

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
//            genericViewHolder.itemTxtTitle.setText(model.getTitle());
//            Picasso.with(genericViewHolder.imgItem.getContext()).load(model.getThumbnailUrl()).into(genericViewHolder.imgItem);

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
    } //MapModel


    public interface OnItemClickListener {
        void onItemClick(View view, int position, DestinationModel model); //MapModel
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        private ImageView imgItem;
//        private TextView itemTxtTitle;
//        private TextView itemTxtMessage;


        @BindView(R.id.img_item)
        ImageView imgItem;
        @BindView(R.id.item_txt_title)
        TextView itemTxtTitle;
        @BindView(R.id.item_txt_message)
        TextView itemTxtMessage;
        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

//            this.imgItem = (ImageView) itemView.findViewById(R.id.img_user);
//            this.itemTxtTitle = (TextView) itemView.findViewById(R.id.item_txt_title);
//            this.itemTxtMessage = (TextView) itemView.findViewById(R.id.item_txt_message);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

    public void addDestinations(DestinationModel destinationModel) {
        modelList.add(destinationModel);
        notifyDataSetChanged();
    }

}

