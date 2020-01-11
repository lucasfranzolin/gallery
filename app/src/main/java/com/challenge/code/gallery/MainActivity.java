package com.challenge.code.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.challenge.code.gallery.models.Cat;
import com.challenge.code.gallery.models.cat.Datum;
import com.challenge.code.gallery.models.cat.Image;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;
    private List<String> links = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewModel();
        viewModel.fetchCats();
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getCats().observe(this, new Observer<Cat>() {
            @Override
            public void onChanged(Cat cats) {
                // update view
                List<Datum> list = new ArrayList<>(cats.getData());
                for (int i = 0; i < list.size(); i++) {
                    List<Image> images = list.get(i).getImages();
                    if (images != null) {
                        for (int j = 0; j < images.size(); j++) {
                            Image item = images.get(j);
                            if (item.getType().startsWith("image") && !item.getLink().endsWith("gif")) {
                                links.add(item.getLink());
                                Log.d(TAG, "getCats() onChanged: " + item.getLink());
                            }
                        }
                    }
                }
            }
        });
        viewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "getLoading() onChanged: " + aBoolean);
            }
        });
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "getError() onChanged: " + s);
            }
        });
    }

}
