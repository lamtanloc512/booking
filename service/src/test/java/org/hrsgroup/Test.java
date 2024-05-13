package org.hrsgroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {


    public static void main(String[] args) {
//        int[] security = {3, 5, -2, -4, 9, 16};
        int[] security = {2, -3, 4, 6, 1};
        int k = 2;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < security.length; i++) {
            int currMax = 0;
            for (int j = i; j < security.length; j += k) {
                currMax += security[j];
            }
            max = Math.max(max, currMax);
        }

        System.out.println(max);
    }

    static int max = Integer.MIN_VALUE;
    static List<List<Integer>> result = new ArrayList<>();

    static int gainMaxValue(List<Integer> nums, int k) {
        backtrack(nums, 0, k, new ArrayList<>());
        System.out.println(result);
        return max;
    }

    static void backtrack(List<Integer> nums, int index, int k, List<Integer> sol) {
        if (index >= nums.size()) {
            return;
        }
        if (index == nums.size() - 1) {
            sol.add(nums.get(index));
            result.add(new ArrayList<>(sol));
        }


        for (int i = index; i < nums.size(); i++) {
//            if (i + k > nums.size()) continue;

            sol.add(nums.get(i));
            backtrack(nums, i + k, k, sol);
            sol.remove(sol.size() - 1);
        }
    }
}
