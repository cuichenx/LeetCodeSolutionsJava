public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    public String toString(){
        if (next == null)
            return String.format("Node (val=%d, next=null)", val);
        else
            return String.format("Node (val=%d, next=%d)", val, next.val);
    }
}
