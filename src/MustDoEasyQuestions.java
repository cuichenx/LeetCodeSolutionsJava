import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/** 13. Roman to Integer
 *
 * Given a roman numeral, convert it to an integer.
 **/
class Q13 {
    public int romanToInt(String s) {
        int ret = 0;
        short idx = 0;

        while (idx < s.length()){
            switch (s.charAt(idx)){
                case 'M':
                    ret += 1000; idx++; break;
                case 'D':
                    ret += 500; idx++; break;
                case 'C':
                    if (idx < s.length()-1){
                        if (s.charAt(idx+1) == 'D'){
                            ret += 400; idx += 2; break;
                        }
                        if (s.charAt(idx+1) == 'M'){
                            ret += 900; idx += 2; break;
                        }
                    }
                    ret += 100; idx++; break;
                case 'L':
                    ret += 50; idx++; break;
                case 'X':
                    if (idx < s.length()-1){
                        if (s.charAt(idx+1) == 'L'){
                            ret += 40; idx += 2; break;
                        }
                        if (s.charAt(idx+1) == 'C'){
                            ret += 90; idx += 2; break;
                        }
                    }
                    ret += 10; idx++; break;
                case 'V':
                    ret += 5; idx++; break;
                case 'I':
                    if (idx < s.length()-1){
                        if (s.charAt(idx+1) == 'V'){
                            ret += 4; idx += 2; break;
                        }
                        if (s.charAt(idx+1) == 'X'){
                            ret += 9; idx += 2; break;
                        }
                    }
                    ret += 1; idx++; break;
                default: return -1;  // invalid char
            }
        }

        return ret;
    }

    public void tests(){
        Helpers.printAssert(romanToInt("III"), 3);
        Helpers.printAssert(romanToInt("IV"), 4);
        Helpers.printAssert(romanToInt("IX"), 9);
        Helpers.printAssert(romanToInt("XIX"), 19);
        Helpers.printAssert(romanToInt("LVIII"), 58);
        Helpers.printAssert(romanToInt("MCMXCIV"), 1994);
    }
}

/** 20. Valid Parentheses
 *
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
*/
class Q20 {
    public boolean isValid(String s) {
        ArrayDeque<Character> stack = new ArrayDeque<>();
        ArrayList<Character> lefties = new ArrayList<>(Arrays.asList('(', '[', '{'));
        HashMap<Character, Character> r2l = new HashMap<>();
        r2l.put(')', '(');
        r2l.put(']', '[');
        r2l.put('}', '{');


        for (char ch: s.toCharArray()){
            if (lefties.contains(ch)){
                stack.push(ch);
            } else if (stack.isEmpty() || stack.pop() != r2l.get(ch))
                return false;
        }
        return stack.isEmpty();
    }
    public void tests(){
        Helpers.printAssert(isValid("()"), true);
        Helpers.printAssert(isValid("()[]{}"), true);
        Helpers.printAssert(isValid("(]"), false);
        Helpers.printAssert(isValid("([)]"), false);
        Helpers.printAssert(isValid("{[]}"), true);
        Helpers.printAssert(isValid("["), false);
        Helpers.printAssert(isValid("]"), false);
        Helpers.printAssert(isValid("][][]"), false);
    }
}

/** 21. Merge Two Sorted Lists
 * Merge two sorted linked lists and return it as a sorted list. The list should be made by splicing together the nodes of the first two lists.
 */
class Q21 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode cur;
        if (l1 == null && l2 == null){
            return null;
        } else if (l1 == null){
            cur = new ListNode(l2.val);
            l2 = l2.next;
        } else if (l2 == null || l1.val < l2.val){
            cur = new ListNode(l1.val);
            l1 = l1.next;
        }
        else {
            cur = new ListNode(l2.val);
            l2 = l2.next;
        }
        ListNode head = cur;
        while (l1 != null || l2 != null){
            int addVal;
            if (l1 == null){
                addVal = l2.val;
                l2 = l2.next;
            } else if (l2 == null){
                addVal = l1.val;
                l1 = l1.next;
            } else if (l1.val < l2.val) {
                addVal = l1.val;
                l1 = l1.next;
            } else {
                addVal = l2.val;
                l2 = l2.next;
            }
            cur.next = new ListNode(addVal);
            cur = cur.next;
        }

        return head;
    }
    public void tests(){
        ListNode l1, l2;
        l1 = Helpers.makeLinkedList(new int[]{1, 2, 4});
        l2 = Helpers.makeLinkedList(new int[]{1, 3, 4});
        Helpers.printAssert(Helpers.printLinkedList(mergeTwoLists(l1, l2)), new int[]{1, 1, 2, 3, 4, 4});
        l1 = Helpers.makeLinkedList(new int[]{});
        l2 = Helpers.makeLinkedList(new int[]{});
        Helpers.printAssert(Helpers.printLinkedList(mergeTwoLists(l1, l2)), new int[]{});
        l1 = Helpers.makeLinkedList(new int[]{});
        l2 = Helpers.makeLinkedList(new int[]{0});
        Helpers.printAssert(Helpers.printLinkedList(mergeTwoLists(l1, l2)), new int[]{0});
        l1 = Helpers.makeLinkedList(new int[]{1});
        l2 = Helpers.makeLinkedList(new int[]{});
        Helpers.printAssert(Helpers.printLinkedList(mergeTwoLists(l1, l2)), new int[]{1});
    }
}

/** 53. Maximum Subarray
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 *
 */
class Q53 {
    public int maxSubArray(int[] nums) {
        // step 1: calculate cumulative sum
        int[] cumul = new int[nums.length+1];
        cumul[0] = 0;
        for (int i = 0; i < nums.length; i++){
            cumul[i+1] = cumul[i] + nums[i];
        }

        int lowest = 0;
        int highest = Integer.MIN_VALUE;
        int curBest = cumul[1];
        for (int i = 1; i < nums.length+1; i++){
            int partialSum = cumul[i];
            if (partialSum > highest){
                highest = partialSum;
                curBest = Math.max(highest - lowest, curBest);
            }
            if (partialSum < lowest){
                lowest = partialSum;
                highest = Integer.MIN_VALUE;  // reset highest, after encountering a new low
            }
        }
        return curBest;

    }
    public void tests(){
        Helpers.printAssert(maxSubArray(new int[] {-2,1,-3,4,-1,2,1,-5,4}), 6);
        Helpers.printAssert(maxSubArray(new int[] {1}), 1);
        Helpers.printAssert(maxSubArray(new int[] {5,4,-1,7,8}), 23);
        Helpers.printAssert(maxSubArray(new int[] {-2, -1}), -1);
    }
}

/** 88. Merge Sorted Array
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n,
 * representing the number of elements in nums1 and nums2 respectively.
 *
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 * The final sorted array should not be returned by the function, but instead be stored inside the array nums1.
 * To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be
 * merged, and the last n elements are set to 0 and should be ignored. nums2 has a length of n.
 *
 */
class Q88 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] nums1Copy = Arrays.copyOfRange(nums1, 0, m);
        int p1 = 0, p2 = 0;
        while (p1 + p2 < m+n){
            if (p1 == m) {
                nums1[p1 + p2] = nums2[p2];
                p2++;
            } else if (p2 == n){
                nums1[p1+p2] = nums1Copy[p1];
                p1++;
            } else if (nums1Copy[p1] < nums2[p2]){
                nums1[p1+p2] = nums1Copy[p1];
                p1++;
            } else {
                nums1[p1 + p2] = nums2[p2];
                p2++;
            }
        }
    }
    public void tests(){
        int[] nums1, nums2;

        nums1 = new int[]{1,2,3,0,0,0};
        nums2 = new int[]{2, 5, 6};
        merge(nums1, 3, nums2, 3);
        Helpers.printAssert(nums1, new int[]{1,2,2,3,5,6});

        nums1 = new int[]{1};
        nums2 = new int[]{};
        merge(nums1, 1, nums2, 0);
        Helpers.printAssert(nums1, new int[]{1});

        nums1 = new int[]{0};
        nums2 = new int[]{1};
        merge(nums1, 0, nums2, 1);
        Helpers.printAssert(nums1, new int[]{1});
    }
}

/** 141 Linked list Cycle
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 */
class Q141 {
    public boolean hasCycle(ListNode head) {
        // Floyd's tortoise and hare algorithm
        if (head == null || head.next == null) return false;
        // there are at least two elements in the linked list
        ListNode tortoise = head, hare = head.next;
        while (tortoise != null && hare != null && hare.next != null){
            if (tortoise == hare)
                return true;  // found a cycle
            tortoise = tortoise.next;
            hare = hare.next.next;
        }
        return false;
    }
    public void tests(){
        Helpers.printAssert(hasCycle(Helpers.makeLinkedListCycle(new int[]{3, 2, 0, -4}, 1)), true);
        Helpers.printAssert(hasCycle(Helpers.makeLinkedListCycle(new int[]{1, 2}, 0)), true);
        Helpers.printAssert(hasCycle(Helpers.makeLinkedListCycle(new int[]{1}, -1)), false);
        Helpers.printAssert(hasCycle(Helpers.makeLinkedListCycle(new int[]{1, 1, 1, 1}, -1)), false);
    }

}

/** 155. Min Stack
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 */
class MinStack {
    private final ArrayList<Integer> data;
    private final ArrayList<Integer> minBelow;
    /** initialize your data structure here. */
    public MinStack() {
        data = new ArrayList<>();
        minBelow = new ArrayList<>();
        minBelow.add(Integer.MAX_VALUE);
    }

    public void push(int val) {
        data.add(val);
        minBelow.add(Math.min(minBelow.get(minBelow.size()-1), val));
    }

    public void pop() {
        data.remove(data.size()-1);
        minBelow.remove(minBelow.size()-1);
    }

    public int top() {
        return data.get(data.size()-1);
    }

    public int getMin() {
        return minBelow.get(minBelow.size()-1);
    }

    public void tests(){
        push(10);
        push(20);
        push(30);
        pop();
        Helpers.printAssert(top(), 20);
        Helpers.printAssert(getMin(), 10);
        push(40);
        Helpers.printAssert(top(), 40);
        Helpers.printAssert(getMin(), 10);
        push(5);
        Helpers.printAssert(top(), 5);
        Helpers.printAssert(getMin(), 5);
        pop();
        Helpers.printAssert(top(), 40);
        Helpers.printAssert(getMin(), 10);
    }
}


/** 160. Intersection of Two Linked Lists
 * Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect.
 * If the two linked lists have no intersection at all, return null.
 */
class Q160 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode curA = headA, curB = headB;
        int countA = 0, countB = 0;
        while (curA.next != null){
            curA = curA.next;
            countA++;
        }
        while (curB.next != null){
            curB = curB.next;
            countB++;
        }
        if (curA != curB) return null;  // two lists do not intersect
        // if they do, give longer list a head start, and advance both lists together
        curA = headA; curB = headB;
        if (countA > countB){
            for (int i=0; i<countA-countB; i++){
                curA = curA.next;
            }
        }
        else {
            for (int i=0; i<countB-countA; i++){
                curB = curB.next;
            }
        }
        while (curA != curB){
            curA = curA.next;
            curB = curB.next;
        }
        return curA;

    }
    public void tests(){
        ListNode[] nodes;
        nodes = Helpers.makeLinkedListIntersect(new int[]{4, 1, 8, 4, 5}, new int[]{5, 6, 1, 8, 4, 5}, 2, 3);
        Helpers.printAssert(getIntersectionNode(nodes[0], nodes[1]), nodes[2]);
        nodes = Helpers.makeLinkedListIntersect(new int[]{1, 9, 1, 2, 4}, new int[]{3, 2, 4}, 3, 1);
        Helpers.printAssert(getIntersectionNode(nodes[0], nodes[1]), nodes[2]);
        nodes = Helpers.makeLinkedListIntersect(new int[]{2, 6, 4}, new int[]{1, 5}, 3, 2);
        Helpers.printAssert(getIntersectionNode(nodes[0], nodes[1]), nodes[2]);
        nodes = Helpers.makeLinkedListIntersect(new int[]{}, new int[]{5, 6, 1, 8, 4, 5}, 0, 6);
        Helpers.printAssert(getIntersectionNode(nodes[0], nodes[1]), nodes[2]);
        nodes = Helpers.makeLinkedListIntersect(new int[]{}, new int[]{}, 0, 0);
        Helpers.printAssert(getIntersectionNode(nodes[0], nodes[1]), nodes[2]);
    }
}


/** This is a template
 *
 */
class Template {
    public void template(String s) {
    }
    public void tests(){
        Helpers.printAssert(0, 0);
    }
}

public class MustDoEasyQuestions {
    // list https://leetcode.com/list/5byh2dij
    public static void main(String[] args){
        new Q160().tests();
    }

}
