package com.challenge.code.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.challenge.code.gallery.models.Cat;
import com.challenge.code.gallery.models.cat.Datum;
import com.challenge.code.gallery.models.cat.Image;
import com.challenge.code.gallery.utils.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private GridView gridView;
    private ProgressBar progressBar;

    private MainViewModel viewModel;
    private List<String> links = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gv);
        progressBar = findViewById(R.id.pb);

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
                GalleryAdapter galleryAdapter = new GalleryAdapter(MainActivity.this, links);
                gridView.setAdapter(galleryAdapter);
            }
        });
        viewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) progressBar.setVisibility(View.VISIBLE);
                else progressBar.setVisibility(View.GONE);
            }
        });
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this, "Error, verify connection.", Toast.LENGTH_LONG);
            }
        });
    }

}
