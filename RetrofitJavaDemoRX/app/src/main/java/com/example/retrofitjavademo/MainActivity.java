package com.example.retrofitjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.txtView);

        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        ApiPostInterface apiPostInterface =  retrofit.create(ApiPostInterface.class);

        // we can pick here the id of the post
        Single<List<Post>> call = apiPostInterface.getPost("1");
        call.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<Post>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    Log.d(TAG,"onSubscribe");
                }
                @Override
                public void onSuccess(@NonNull List<Post> posts) {
                    // This Data will be processed to the MainThread to be able to populate later
                    textView.setText(posts.get(0).getTitle());

                }
                @Override
                public void onError(@NonNull Throwable e) {
                    Toast.makeText(getApplicationContext(),"Error has happened ",Toast.LENGTH_SHORT).show();

                }
            });
    }
}