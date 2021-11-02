package com.nigagara.hawaii.kfhsdkjfchwaux;

import com.nigagara.hawaii.entity.TestEntity;

public class Overflow {

    public static void main(String[] args) {

        for(int i=9;i<31;i++) {
            System.out.println("TestEntity testEntity" + i + "= new TestEntity();");
            System.out.println("testEntity" + i + ".setTestName(\"" + i + "번 테스트\"); testEntity8.setView(11);");
            System.out.println("testEntity" + i + ".setTestType(TYPE3); em.persist(testEntity" + i + ");");
            System.out.println("Long testId" + i + " = testEntity" + i + ".getId();");
        }


    }
}
