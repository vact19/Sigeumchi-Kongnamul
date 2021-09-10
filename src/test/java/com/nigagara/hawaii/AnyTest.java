package com.nigagara.hawaii;

import com.nigagara.hawaii.entity.Likes;
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

    @Test
    public void dfsdfdasga() throws Exception{

        Likes aaaa = aaaa();
        System.out.println("aaaa = " + aaaa);
//        모든 리턴 타입 메서드는,
//        null을 리턴하고, 그 값을 대입하는 데까지는 오류 발생 X.


    }
    public Likes aaaa(){
        return null;
    }

}
