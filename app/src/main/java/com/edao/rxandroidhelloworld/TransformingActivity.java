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
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;

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
//        flatMap();
//        groupBy();
//        buffer();
        scan();
    }

    private void scan() {
        Observable.just(1 , 2 , 3 , 4 , 5).scan(new BiFunction<Integer, Integer, Integer>() {

            //表现形式上是一个累加的效果。即把上一个函数的结果作为下一个函数的初始值，以此类推去得到一个最终的结果
            @Override
            public Integer apply(@NonNull Integer sum, @NonNull Integer item) throws Exception {
                return sum + item;
            }
        }).subscribe(new Observer<Integer>() {
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
    }

    private void buffer() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("张三", 12));
        students.add(new Student("李四", 16));
        students.add(new Student("王五", 15));

        Observable.fromIterable(students).buffer(2).subscribe(new Observer<List<Student>>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            //理解成分页请求，一次请求多少条数据；或是缓冲区之类的概念
            @Override
            public void onNext(@NonNull List<Student> students) {
                Log.d(TAG, students.toString());
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void groupBy() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("张三", 12));
        students.add(new Student("李四", 16));
        students.add(new Student("王五", 15));

        Observable.fromIterable(students).groupBy(new Function<Student, Integer>() {
            @Override
            public Integer apply(@NonNull Student student) throws Exception {
                //根据条件设定返回的不同的分组（这里是年龄大于15岁的分到组号为1，其余的组号为2）
                if (student.age > 15) {
                    return 1;
                } else {
                    return 2;
                }

            }
        }).subscribe(new Observer<GroupedObservable<Integer, Student>>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            //GroupedObservable里包含了多个Observable，根据运行结果在这里就是两个（1和2）
            @Override
            public void onNext(@NonNull final GroupedObservable<Integer, Student> integerStudentGroupedObservable) {
                integerStudentGroupedObservable.subscribe(new Observer<Student>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull Student student) {

                        Log.d(TAG, "组号为 " + integerStudentGroupedObservable.getKey() + " 的成员有 " + student.name);
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
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
        students.add(new Student("张三", 12, lessons1));
        students.add(new Student("李四", 16, lessons2));
        students.add(new Student("王五", 10, lessons3));

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
