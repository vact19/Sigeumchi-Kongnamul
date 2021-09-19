package com.nigagara.hawaii.kfhsdkjfchwaux;

public class Overflow {

    public static void main(String[] args) {
        Long id = 3L;
        System.out.println(id+"번");
        String qjs = id+"번";
        System.out.println("qjs = " + qjs); // id String 자동 형변환?인듯

    }
}