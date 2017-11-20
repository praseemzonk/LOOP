package com.zonk.fbtest.Views;

import android.view.View;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;

public class ScaleTransformation implements GalleryLayoutManager.ItemTransformer {


    @Override
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        item.setPivotX(item.getWidth() / 2.f);
        item.setPivotY(item.getHeight()/2.0f);
        float scale = 1 - 0.3f * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
    }
}