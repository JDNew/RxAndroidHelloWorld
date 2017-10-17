package com.edao.rxandroidhelloworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.edao.rxandroidhelloworld.bean.Lesson;
import com.edao.rxandroidhelloworld.bean.Student;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * description
 * Created by JD
 * on 2017/10/17.
 */

public class TransformingActivity extends AppCompatActivity {
    private final String TAG = "RxAndroid";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);

//        map();
        flatMap();
    }

    private void flatMap() {

        List<Lesson> lessons1 = new ArrayList<>();
        lessons1.add(new Lesson("语文"));
        lessons1.add(new Lesson("数学"));
        List<Lesson> lessons2 = new ArrayList<>();
        lessons1.add(new Lesson("英语"));
        lessons1.add(new Lesson("体育"));
        List<Lesson> lessons3 = new ArrayList<>();
        lessons1.add(new Lesson("音乐"));
        lessons1.add(new Lesson("美术"));

        List<Student> students = new ArrayList<>();
        students.add(new Student("张三" , 12 , lessons1));
        students.add(new Student("李四" , 16 , lessons2));
        students.add(new Student("王五" , 10 , lessons3));

        Observable.fromIterable(students).flatMap(new Function<Student, ObservableSource<Lesson>>() {
            @Override
            public ObservableSource<Lesson> apply(@NonNull Student student) throws Exception {
                return Observable.fromIterable(student.lessons);
            }
        }).subscribe(new Observer<Lesson>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull Lesson s) {
                Log.d(TAG, s.lessonName);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void map() {
        Observable.just(123).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return integer + "";
            }
        }).subscribe(new Observer<String>() {
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
}