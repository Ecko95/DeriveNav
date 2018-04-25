package com.example.joshua.derivenav.NewTrip;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahamed.multiviewadapter.DataListManager;
import com.ahamed.multiviewadapter.RecyclerAdapter;
import com.ahamed.multiviewadapter.SelectableAdapter;
import com.example.joshua.derivenav.NewTrip.Binders.CityBinder;
import com.example.joshua.derivenav.R;
import com.example.joshua.derivenav.com.joshua.api.controller.RestManager;
import com.example.joshua.derivenav.com.joshua.api.model.City;
import com.example.joshua.derivenav.com.joshua.api.model.Facade.apiClient;
import com.example.joshua.derivenav.com.joshua.api.model.adapter.POIAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment2 extends Fragment implements POIAdapter.POIClickListener{

    @BindView(R.id.rv_places) RecyclerView cityList;
    private POIAdapter mPOIAdapter;
    private ProgressDialog mDialog;
    private RestManager mManager;

    public StepFragment2() {
        // Required empty public constructor
    }

    public void getFeed() {
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading apiClient Data...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);
        mManager = new RestManager();
        Call<List<apiClient>> listCall = mManager.getFlowerService().getAllPointsOfInterest();
        listCall.enqueue(new Callback<List<apiClient>>() {
            @Override
            public void onResponse(Call<List<apiClient>> call, Response<List<apiClient>> response) {
                if (response.isSuccessful()) {

                    List<apiClient> flowerList = response.body();

                    for (int i = 0; i < flowerList.size(); i++) {
                        apiClient flower = flowerList.get(i);

                        mPOIAdapter.addFlower(flower);
                    }
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<apiClient>> call, Throwable t) {
                mDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_fragment2, container, false);
        ButterKnife.bind(this,view);

        cityList.setHasFixedSize(true);
        cityList.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        cityList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mPOIAdapter = new POIAdapter(this);

        cityList.setAdapter(mPOIAdapter);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //getFeed();
            }
        });

        // Start the thread
        t.start();


        // Inflate the layout for this fragment
        return view;

    }
//    private void setUpAdapter(List<City> cityList) {
//        SelectableAdapter adapter = new SelectableAdapter();
//        adapter.setSelectionMode(SelectableAdapter.SELECTION_MODE_MULTIPLE);
//    }

    @Override
    public void onClick(int position) {
        Toast.makeText(getContext(), "you pressed: " + position, Toast.LENGTH_SHORT).show();
    }
}
