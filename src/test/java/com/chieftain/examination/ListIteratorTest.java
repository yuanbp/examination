package com.chieftain.examination;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-04-03
 * @time 09:04
 */
public class ListIteratorTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        ListIterator<Integer> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            Integer tmp = listIterator.next();
            if (tmp == 1) {
                listIterator.set(4);
            }
            System.out.println(tmp);
        }
        listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            Integer tmp = listIterator.next();
            System.out.println(tmp);
        }
    }
}
