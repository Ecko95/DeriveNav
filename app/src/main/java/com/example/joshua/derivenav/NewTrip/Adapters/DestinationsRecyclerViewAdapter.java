package com.example.joshua.derivenav.NewTrip.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.example.joshua.derivenav.DestinationDetailsActivity;
import com.example.joshua.derivenav.NewTrip.DataManagers.StepDataManager;
import com.example.joshua.derivenav.NewTrip.Models.DestinationModel;
import com.example.joshua.derivenav.NewTrip.Models.UserModel;
import com.example.joshua.derivenav.R;
import com.squareup.picasso.Picasso;

import android.widget.CheckBox;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class DestinationsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DestinationsRecyclerVie";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private String mHeaderTitle;
    private OnHeaderClickListener mHeaderClickListener;
    private Context mContext;
    private ArrayList<DestinationModel> modelList;
    private OnItemClickListener mItemClickListener;
    private OnCheckedListener mOnCheckedListener;


    private Set<Integer> checkSet = new HashSet<>();



    public DestinationsRecyclerViewAdapter(Context context, ArrayList<DestinationModel> modelList, String headerTitle) {
        this.mContext = context;
        this.modelList = modelList;
        this.mHeaderTitle = headerTitle;
    }

    public void updateList(ArrayList<DestinationModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_item_recycler_list, parent, false);
            return new ViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

//        if (holder instanceof HeaderViewHolder) {
//            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
//
//            headerHolder.txtTitleHeader.setText(mHeaderTitle);
//
//        } else if (holder instanceof ViewHolder) {
//            final DestinationModel model = getItem(position - 1);
//            ViewHolder genericViewHolder = (ViewHolder) holder;
//
//
//            genericViewHolder.itemTxtTitle.setText(cityDestinationsModel.getTitle());
//            genericViewHolder.itemTxtMessage.setText(cityDestinationsModel.getId());
//            Picasso.with(genericViewHolder.imgItem.getContext()).load(cityDestinationsModel.getThumbnailUrl()).into(genericViewHolder.imgItem);
//
//            //in some cases, it will prevent unwanted situations
//            genericViewHolder.itemCheckList.setOnCheckedChangeListener(null);
//
//            //if true, your checkbox will be selected, else unselected
//            genericViewHolder.itemCheckList.setChecked(checkSet.contains(position));
//
//            genericViewHolder.itemCheckList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (isChecked) {
//                        checkSet.add(position);
//                    } else {
//                        checkSet.remove(position);
//                    }
//
//                    mOnCheckedListener.onChecked(buttonView, isChecked, position, model);
//
//                }
//            });
//
//
//        }


        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.txtTitleHeader.setText(mHeaderTitle);

        } else if (holder instanceof ViewHolder) {
            final DestinationModel model = getItem(position - 1);

            ViewHolder genericViewHolder = (ViewHolder) holder;

            //PHOTOS MODEL
//            genericViewHolder.itemTxtTitle.setText(model.getTitle());
//            Picasso.with(genericViewHolder.imgItem.getContext()).load(model.getThumbnailUrl()).into(genericViewHolder.imgItem);

//            //USERS MODEL
//            genericViewHolder.itemTxtTitle.setText(model.getName());
//            genericViewHolder.itemTxtMessage.setText(model.getAddress().getGeo().getLat());
//            Picasso.with(genericViewHolder.imgItem.getContext()).load("https://picsum.photos/100/100/?random").into(genericViewHolder.imgItem);

            //POINTS OF INTEREST MODEL
//            List<DestinationModel.Points_of_interest> PointOfInterest = model.getPoints_of_interest();
//            for(int i = 0; i < model.getPoints_of_interest().size(); i++){
//                DestinationModel.Points_of_interest points_of_interest = PointOfInterest.get(i);
//                genericViewHolder.itemTxtTitle.setText(points_of_interest.getTitle());
//                genericViewHolder.itemTxtMessage.setText(points_of_interest.getDetails().getDescription());
//            }

//            List<DestinationModel.Points_of_interest> pointsOfInterest = model.getPoints_of_interest();
//            for(int i = 0; i < pointsOfInterest.size(); i++){
//                genericViewHolder.itemTxtTitle.setText(model.getPoints_of_interest().get(i).getTitle());
//                genericViewHolder.itemTxtMessage.setText(model.getPoints_of_interest().get(i).getDetails().getShort_description());
//            }

//
//            for (int i = 0; i < model.getPoints_of_interest().size(); i++) {
            genericViewHolder.itemTxtTitle.setText(model.getPoints_of_interest().get(position - 1).getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getPoints_of_interest().get(position - 1).getDetails().getShort_description());
//            }
//            genericViewHolder.itemTxtTitle.setText(model.getPoints_of_interest().get(0).getTitle());
//            genericViewHolder.itemTxtMessage.setText(model.getPoints_of_interest().get(0).getDetails().getShort_description());



            //in some cases, it will prevent unwanted situations
            genericViewHolder.itemCheckList.setOnCheckedChangeListener(null);

            //if true, your checkbox will be selected, else unselected
            genericViewHolder.itemCheckList.setChecked(checkSet.contains(position));

            genericViewHolder.itemCheckList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        checkSet.add(position);
                    } else {
                        checkSet.remove(position);
                    }

                    mOnCheckedListener.onChecked(buttonView, isChecked, position, model);

                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @Override
    public int getItemCount() {

        return modelList.size() + 1;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnHeaderClickListener(final OnHeaderClickListener headerClickListener) {
        this.mHeaderClickListener = headerClickListener;
    }

    public void SetOnCheckedListener(final OnCheckedListener onCheckedListener) {
        this.mOnCheckedListener = onCheckedListener;

    }

    private DestinationModel getItem(int position) {
        return modelList.get(position);
    }

    public void addDestinations(DestinationModel destinationModel) {
        modelList.add(destinationModel);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, DestinationModel model);
    }


    public interface OnHeaderClickListener {
        void onHeaderClick(View view, String headerTitle);
    }

    public interface OnCheckedListener {
        void onChecked(View view, boolean isChecked, int position, DestinationModel model);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleHeader;
        ImageView imageView;

        public HeaderViewHolder(final View itemView) {
            super(itemView);
            this.txtTitleHeader = (TextView) itemView.findViewById(R.id.txt_header);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mHeaderClickListener.onHeaderClick(itemView, mHeaderTitle);

                }
            });

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.img_item)
        ImageView imgItem;
        @BindView(R.id.item_txt_title)
        TextView itemTxtTitle;
        @BindView(R.id.item_txt_message)
        TextView itemTxtMessage;
        @BindView(R.id.check_list)
        CheckBox itemCheckList;

        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getAdapterPosition();
                    final DestinationModel model = getItem(itemPosition - 1);
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition() - 1));
                    Intent intent = new Intent(mContext, DestinationDetailsActivity.class);
                    intent.putExtra("img_url", model.getThumbnailUrl());
                    intent.putExtra("destination_title", model.getTitle());
                    Log.d(TAG, model.getTitle());
                    Log.d(TAG, model.getThumbnailUrl());

                    mContext.startActivity(intent);

                }
            });

        }
    }


}

