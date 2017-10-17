package com.edao.rxandroidhelloworld.bean;

import java.util.List;

/**
 * description
 * Created by JD
 * on 2017/10/17.
 */

public class Student {

    public String name;
    public int age;
    public List<Lesson> lessons;

    public Student(String name , int age , List<Lesson> lessons){
        this.name = name;
        this.age = age;
        this.lessons = lessons;
    }


}
