package com.nigagara.hawaii;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AnyTest {

    @Test
    public void 안녕(){

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        list.stream().forEach( l -> System.out.println("l = " + l));


    }
}
