package com.challenge.code.gallery;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.challenge.code.gallery.api.ApiClient;
import com.challenge.code.gallery.api.ApiService;
import com.challenge.code.gallery.api.RetrofitCallback;
import com.challenge.code.gallery.models.Cat;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainViewModel  extends AndroidViewModel {
    private ApiService api = ApiClient.createService(ApiService.class);

    private MutableLiveData<Cat> cats = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private MutableLiveData<String> error = new MutableLiveData<>("");

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchCats() {
        loading.setValue(true);
        api.getCats().enqueue(new RetrofitCallback<Cat>() {
            @Override
            public void onSuccess(Cat model) {
                cats.setValue(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                error.setValue("onFailure: " + msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                error.setValue("onThrowable: " + t.toString());
            }

            @Override
            public void onFinish() {
                loading.setValue(false);
            }
        });
    }

    public MutableLiveData<Cat> getCats() {
        return cats;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
