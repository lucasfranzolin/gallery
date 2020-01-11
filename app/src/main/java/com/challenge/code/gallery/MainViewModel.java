package com.challenge.code.gallery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.challenge.code.gallery.api.ApiClient;
import com.challenge.code.gallery.api.ApiService;
import com.challenge.code.gallery.api.RetrofitCallback;
import com.challenge.code.gallery.models.Cat;

import java.util.List;

public class MainViewModel  extends AndroidViewModel {
    private ApiService api = ApiClient.createService(ApiService.class);

    private MutableLiveData<List<Cat>> cats = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> error = new MutableLiveData<>(false);

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchCats() {
        loading.setValue(true);
        api.getCats().enqueue(new RetrofitCallback<List<Cat>>() {
            @Override
            public void onSuccess(List<Cat> model) {
                cats.setValue(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                error.setValue(true);
            }

            @Override
            public void onThrowable(Throwable t) {
                error.setValue(true);
            }

            @Override
            public void onFinish() {
                loading.setValue(false);
            }
        });
    }

    public MutableLiveData<List<Cat>> getCats() {
        return cats;
    }


}
