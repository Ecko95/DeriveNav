package com.example.joshua.derivenav.com.joshua.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Joshua on 10/12/2017.
 */

public abstract class AbstractService implements Serializable, Runnable {
    private static ArrayList<ServiceListener> listeners;
    private boolean error;


    public AbstractService(){
        listeners = new ArrayList<>();
    }
    public static void addListener(ServiceListener serviceListener){
        listeners.add(serviceListener);
    }
    public void removeListener(ServiceListener serviceListener){
        listeners.remove(serviceListener);
    }
    public boolean hasError(){
        return error;
    }
    public void serviceCallComplete(boolean error){
        this.error = error;

        Message m = _handler.obtainMessage();
        Bundle b = new Bundle();
        b.putSerializable("service",this);
        m.setData(b);
        _handler.sendMessage(m);
    }

    final Handler _handler = new Handler(){
        public void handlerMessage (Message msg){
            AbstractService service = (AbstractService)msg.getData().getSerializable("service");
            for(ServiceListener serviceListener : service.listeners){
                serviceListener.serviceComplete(service);
            }
        }
    };

}
