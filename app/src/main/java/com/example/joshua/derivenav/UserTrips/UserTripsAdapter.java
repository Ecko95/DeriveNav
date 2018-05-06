package com.example.joshua.derivenav.UserTrips;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.joshua.derivenav.NewTrip.Models.TripModel;
import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.UserTripDetails.UserTripDetails;
import com.example.joshua.derivenav.UserTrips.Models.UserTrips;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class UserTripsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Context mContext;
    private ArrayList<UserTrips> modelList;

    private OnItemClickListener mItemClickListener;

    public UserTripsAdapter(Context context, ArrayList<UserTrips> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<UserTrips> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_trips_recycler_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final UserTrips model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.itemTxtTitle.setText(model.getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getDescription());

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private UserTrips getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, UserTrips model);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.img_trip) ImageView imgTrip;
        @BindView(R.id.item_txt_title) TextView itemTxtTitle;
        @BindView(R.id.item_txt_message) TextView itemTxtMessage;

        public ViewHolder(final View itemView) {
            super(itemView);

             ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                    int itemPosition = getAdapterPosition();

                    final UserTrips model = getItem(itemPosition);
//                    Toast.makeText(mContext, "clicked:" + (itemPosition - 1), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, UserTripDetails.class);

                    intent.putExtra("title", model.getTitle());
                    intent.putExtra("key", model.getKey());

                    mContext.startActivity(intent);

                }
            });

        }
    }

}

