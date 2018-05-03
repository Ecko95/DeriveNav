/*
 * Copyright (c) 2015-2016 Filippo Engidashet. All Rights Reserved.
 * <p>
 *  Save to the extent permitted by law, you may not use, copy, modify,
 *  distribute or create derivative works of this material or any part
 *  of it without the prior written consent of Filippo Engidashet.
 *  <p>
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 */

package com.example.joshua.derivenav.com.joshua.api.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.com.joshua.api.Facade.apiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @date 1/22/2016
 */
public class POIAdapter extends RecyclerView.Adapter<POIAdapter.Holder> {

    private static final String TAG = POIAdapter.class.getSimpleName();
    private final POIClickListener mListener;
    private List<apiClient> mCities;

    public POIAdapter(POIClickListener listener) {
        mCities = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.points_of_interest, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        apiClient curlApi = mCities.get(position);
//
        holder.mName.setText(curlApi.getTitle());
        holder.mPrice.setText(curlApi.getId().toString());

        if (curlApi.isFromDatabase()) {
//            holder.mPhoto.setImageBitmap(curlApi.getPicture());
        } else {
//            Picasso.with(holder.itemView.getContext()).load(Constants.HTTP.BASE_URL + "/photos/" + currFlower.getPhoto()).into(holder.mPhoto);
            Picasso.with(holder.itemView.getContext()).load(curlApi.getThumbnailUrl()).into(holder.mPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public void addFlower(apiClient apiClient) {
        mCities.add(apiClient);
        notifyDataSetChanged();
    }

    /**
     * @param position
     * @return
     */
    public apiClient getSelectedFlower(int position) {
        return mCities.get(position);
    }

    public void reset() {
        mCities.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPhoto;
        private TextView mName, mPrice;

        public Holder(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView.findViewById(R.id.flowerPhoto);
            mName = (TextView) itemView.findViewById(R.id.flowerName);
            mPrice = (TextView) itemView.findViewById(R.id.flowerPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface POIClickListener {

        void onClick(int position);
    }
}
