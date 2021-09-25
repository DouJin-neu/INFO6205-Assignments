package edu.neu.coe.info6205.as2;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.Timer;

import java.io.IOException;
import java.util.Random;

public class BenchmarkInsertionSort {
    static Random random = new Random();
    static BaseHelper<Integer> helper = null;

    public static Integer[] generateRandomArray(int arraySize){
        Integer[] array = new Integer[arraySize];

        for (int i = 0; i < arraySize; i++)
        {
            array[i] = random.nextInt(1000);
        }

        return array;
    }

    public static Integer[] sort(Integer[] array){

        try {
            helper = new BaseHelper<>("InsertionSort", array.length, Config.load(BenchmarkInsertionSort.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GenericSort<Integer> sorter = new InsertionSort<>(helper);
        return sorter.sort(array);
    }

    //Get a partial sorted array
    public static Integer[] partialSort(Integer[] array){

        try {
            helper = new BaseHelper<>("InsertionSort", array.length, Config.load(BenchmarkInsertionSort.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GenericSort<Integer> sorter = new InsertionSort<>(helper);
//        int from = random.nextInt(array.length);
//        int to = random.nextInt(array.length);
//        if(from > to){
//            int temp = from;
//            from = to;
//            to = temp;
//        }
        int from = 0;
        int to = 50;
        sorter.sort(array, from, to);
        return array;
    }

    public static Integer[] reverseOrder(Integer[] sorted){
        Integer[] reversed = new Integer[sorted.length];
        int index = 0;
        for (int i = sorted.length - 1; i >= 0; i--) {
            reversed[index++] = sorted[i];
        }
        return reversed;
    }

    public static void benchmarking_sort(Integer[] array, String str){
        final Timer timer = new Timer();
        final Integer[] original_array = array;
        final int zzz = 20;
        final double mean = timer.repeat(50,()-> zzz, t->{
            sort(original_array);
            return null;
        });

        System.out.println("Sorting " + str + " Mean time: " + mean);
    }

    public static void main(String[] args) {

        int[] arraySize = {200, 400, 800, 1600, 3200, 6400};
        for (int size : arraySize) {
            final Integer[] array = generateRandomArray(size);
            System.out.println("Current array length: " + size);

            Integer[] sorted = sort(array);
            /*Ordered Array*/
            benchmarking_sort(sorted,"Ordered Array");

            /*Partial Ordered Array*/
            Integer[] partial_ordered = partialSort(array);
            benchmarking_sort(partial_ordered,"Partial Ordered Array");

            /*Reversed Array*/
            Integer[] reversed = reverseOrder(sorted);
            benchmarking_sort(reversed,"Reversed Order");

            /*Random Array*/
            benchmarking_sort(array,"Random Array");
            System.out.println("============================================");
        }
    }
}
