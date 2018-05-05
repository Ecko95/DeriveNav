package com.example.joshua.derivenav.Search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Joshua on 04/05/2018.
 */

public abstract class AbstractService implements Serializable, Runnable {

    private ArrayList<ServiceListener> listeners;
    private boolean error;

    public AbstractService() {
        listeners = new ArrayList<>();
    }

    public void addListener(ServiceListener serviceListener) {
        listeners.add(serviceListener);
    }


    public void removeListeners(ServiceListener serviceListener) {
        listeners.remove(serviceListener);
    }

    public boolean hasError() {
        return error;
    }

    public void serviceCallComplete(boolean error) {
        this.error = error;

        Message m = _handler.obtainMessage();
        Bundle b = new Bundle();
        b.putSerializable("service", this);
        m.setData(b);
        _handler.sendMessage(m);
    }

    final Handler _handler = new Handler() {

        public void handleMessage(Message msg) {
            AbstractService service = (AbstractService) msg.getData().getSerializable("service");

            for (ServiceListener serviceListener : service.listeners) {
                serviceListener.serviceComplete(service);
            }
        }
    };

    @Override
    public void run() {
    }
}