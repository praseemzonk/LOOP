package com.zonk.fbtest.Utils;

import android.view.Window;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;

/**
 * Created by Ribbi on 11/20/2017.
 */

public class InfoWindowRefresh implements Callback {
    private Marker markerToRefresh;

    public InfoWindowRefresh(Marker markerToRefresh) {
        this.markerToRefresh = markerToRefresh;
    }

    @Override
    public void onSuccess() {
        markerToRefresh.showInfoWindow();
    }

    @Override
    public void onError() {}
}
