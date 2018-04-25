package com.example.joshua.derivenav.NewTrip.Binders;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahamed.multiviewadapter.BaseViewHolder;
import com.ahamed.multiviewadapter.ItemBinder;
import com.ahamed.multiviewadapter.util.ItemDecorator;
import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.com.joshua.api.model.City;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityBinder extends ItemBinder<City, CityBinder.ViewHolder> {

    @Override
    public ViewHolder create(LayoutInflater layoutInflater, ViewGroup parent) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_city, parent, false));
    }

    @Override
    public boolean canBindData(Object item) {
        return item instanceof City;
    }

    @Override
    public void bind(ViewHolder holder, City item) {
        holder.tv_place_name.setText(item.getName());
    }

    static class ViewHolder extends BaseViewHolder<City> {

        @Nullable
        @BindView(R.id.tv_place_name) TextView tv_place_name;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}