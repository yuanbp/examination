package com.chieftain.examination;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.ListUtils;
import org.junit.Test;

import java.util.List;

/**
 * @author chieftain
 * @desc ...
 * @date 2019-04-03
 * @time 16:29
 */
public class ListTest {

    @Test
    public void givenList_whenParitioningIntoNSublists_thenCorrect() {
        List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        List<List<Integer>> subSets = ListUtils.partition(intList, 3);

        for (List<Integer> subSet : subSets) {
            for (Integer integer : subSet) {
                System.out.println(integer);
            }
            System.out.println("------------------------------");
        }

//        List<Integer> lastPartition = subSets.get(2);
//        List<Integer> expectedLastPartition = Lists.<Integer> newArrayList(7, 8);
//        assertThat(subSets.size(), equalTo(3));
//        assertThat(lastPartition, equalTo(expectedLastPartition));
    }

    @Test
    public void forTest () {
        List<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);
        List<List<Integer>> subSets = ListUtils.partition(intList, 3);

        for (List<Integer> subSet : subSets) {
            for (int i = 0, len = subSet.size(); i < len; i++) {
                if (i == 0) {
                    System.out.println(subSet.get(i));
                }
                if (i == len - 1) {
                    System.out.println(subSet.get(i));
                }
            }
            System.out.println("------------------------------");
        }
    }
}
