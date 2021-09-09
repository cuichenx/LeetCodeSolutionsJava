import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Helpers {
    public static void printAssert(Object actual, Object expected){
        String symbol;
        boolean condition;
        if (actual == null && expected == null){
            condition = true;
        } else if (expected.getClass().isArray()){
            condition = Arrays.equals((int[]) actual, (int[]) expected);
            actual = Arrays.toString((int[]) actual);
            expected = Arrays.toString((int[]) expected);
        } else {
            condition = actual.equals(expected);
        }
        symbol = condition ? "✔️" :  "❌️";
        System.out.printf("%s Actual: %s   Expected: %s\n", symbol, actual, expected);
    }

    // Linked List related
    public static ListNode makeLinkedList(int[] nums){
        if (nums.length == 0) return null;

        ListNode head = new ListNode(nums[0]);
        ListNode cur = head;
        for (int i = 1; i < nums.length; i++){
            cur.next = new ListNode(nums[i]);
            cur = cur.next;
        }
        return head;
    }

    public static ListNode makeLinkedListCycle(int[] nums, int pos){
        // linked list with a cycle. pos indicates where the last element is connected to
        // pos == -1 means there is no cycle
        if (nums.length == 0) return null;
        ListNode memo = null;
        ListNode head = new ListNode(nums[0]);
        if (pos == 0) memo = head;
        ListNode cur = head;
        for (int i = 1; i < nums.length; i++){
            cur.next = new ListNode(nums[i]);
            cur = cur.next;
            if (i == pos){
                memo = cur;  // keep track of this node for cycle building
            }
        }
        cur.next = memo;
        return head;
    }

    public static ListNode[] makeLinkedListIntersect(int[] listA, int[] listB, int skipA, int skipB){
        assert listA.length - skipA == listB.length - skipB;

        ListNode listAhead = makeLinkedList(Arrays.copyOfRange(listA, 0, skipA));
        ListNode listAcur = listAhead;
        if (listAcur != null){
            while (listAcur.next != null){
                listAcur = listAcur.next;
            }
        }
        ListNode listBhead = makeLinkedList(Arrays.copyOfRange(listB, 0, skipB));
        ListNode listBcur = listBhead;
        if (listBcur != null){
            while (listBcur.next != null){
                listBcur = listBcur.next;
            }
        }
        ListNode sharedHead = null;
        if (skipA < listA.length && skipB < listB.length && listAcur != null && listBcur != null){
            sharedHead = makeLinkedList(Arrays.copyOfRange(listA, skipA, listA.length));
            listAcur.next = sharedHead;
            listBcur.next = sharedHead;
        }

        return new ListNode[]{listAhead, listBhead, sharedHead};
    }

    public static int[] printLinkedList(ListNode head){
        ListNode tmp = head;
        int len = 0;
        while (tmp != null) {
            len++;
            tmp = tmp.next;
        }
        int[] ret = new int[len];
        for (int i = 0; i<len; i++){
            ret[i] = head.val;
            head = head.next;
        }
        return ret;
    }
}
