package com.edao.rxandroidhelloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class CreatingActivity extends AppCompatActivity {

    private final String TAG = "RxAndroid";
    private String content = "Hello World";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        helloWorld();
//        just();
//        from();
        defer();


    }

    String content1 = "1";
    private void defer() {

        Observable observable = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return Observable.just(content1);
            }
        });

        content1 = "helloworld";
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, s);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    private void from() {
        Integer[] datas = new Integer[]{1, 2, 3, 4};
        List<String> dataList = new ArrayList<>();
        dataList.add("1");
        dataList.add("2");
        dataList.add("3");
        dataList.add("4");
        dataList.add("5");


        Observable.fromArray(datas).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, integer + "");
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable.fromIterable(dataList).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull String string) {
                Log.d(TAG, string);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void just() {
        Observable.just(content).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.d(TAG, s);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void helloWorld() {
        //创建被观察者
        Observable mObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext(content);
                observableEmitter.onComplete();
            }
        });
        //创建观察者
        Observer mObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull String string) {
                Log.d(TAG, string);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
        //订阅
        mObservable.subscribe(mObserver);
    }
}
