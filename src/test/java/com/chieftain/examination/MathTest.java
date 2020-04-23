package com.chieftain.examination;

import org.junit.Test;

import java.util.Random;

/**
 * com.chieftain.jtestcodes [workset]
 * Created by chieftain on 2018/9/19
 *
 * @author chieftain on 2018/9/19
 */
public class MathTest
{

    public static void main(String[] args)
    {
        decideTest();
    }

    public static void decideTest()
    {
        int i = 0;
        if(i < 1){
            System.out.println("i < 0");
        }else if(i == 0){
            System.out.println("i is 1");
        }
    }

    public static void randomTest()
    {
        Random random = new Random();
        for (int i = 0; i < 100; i++)
        {
            int rnum = random.nextInt(10000);
            System.out.println(rnum);
        }
    }

    @Test
    public void absTest () {
        System.out.println(~1000000000+1);
    }
}
