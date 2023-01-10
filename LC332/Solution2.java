package LC332;

import java.util.*;

// https://leetcode.com/problems/reconstruct-itinerary/submissions/875645011/

//references: https://www.youtube.com/watch?v=WYqsg5dziaQ&t=6s
// https://www.youtube.com/watch?v=vFC5AzIS4Zs&t=2s
// https://www.youtube.com/watch?v=ZyB_gQ8vqGA&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=26

// The difference of Solution1 and Solution2 is that Solution2 using a explicity stack to track DFS path building 
// ,while Solution1 doesn't use stack, but use recursion to achieve the same result.

// The idea is to use DFS or Stack to traverse all the ticket edges. It's not exactly a backtracking strategy,
// because each traversed path will contribute the final result, and there is no "wrong" path which NOT
// contribute to the final result.
// This Solution's time complexity is O(n), n is the number of tickets (# of edges in graph), because our code basically traverse 
// each ticket once.   
class Solution2 {

    // Java doesn't have something like multiset in C++, Java PriorityQueue is a
    // good option,
    // but LinkedList is a much simpler solution than PriorityQueue. Java LinkedList
    // is also a Queue
    // and a Dequeue, and a Stack as welll!
    private Map<String, LinkedList<String>> flights = new HashMap<>();

    // "results" need to be inserted from front, so choose LinkedList, which supports
    // Dequeue.addFirst()
    private LinkedList<String> results = new LinkedList<>();

    public static void main(String[] args) {

        // Real test data copied from Leetcode test cases
        // bigger test case:
        // String[][] input = { { "EZE", "AXA" }, { "TIA", "ANU" }, { "ANU", "JFK" }, { "JFK", "ANU" }, { "ANU", "EZE" },
        //         { "TIA", "ANU" }, { "AXA", "TIA" }, { "TIA", "JFK" }, { "ANU", "TIA" }, { "JFK", "TIA" } };

        // smaller test case:
        String[][] input = {{"MUC","LHR"},{"JFK","MUC"},{"SFO","SJC"},{"LHR","SFO"}};

        // Build Leetcode input list from above array
        List<List<String>> tickets = new ArrayList<>();

        // Arrays.stream(input).forEach(e -> { List<String> l = new ArrayList<>();
        // l.add(e[0]); l.add(e[1]); tickets.add(l);} );
        // Make code more readable by using helper method "addTicket"
        // Stream and many other Collection classes have "forEach" method, like List, Set, Queue, Deque, Stack etc
        Arrays.stream(input).forEach(e -> addTicket(tickets, e));

        Solution2 s = new Solution2();
        s.findItinerary(tickets);
    }

    private static void addTicket(List<List<String>> tickets, String[] ticket) {
        List<String> l = new ArrayList<>();
        l.add(ticket[0]);
        l.add(ticket[1]);
        tickets.add(l);
    }

    public List<String> findItinerary(List<List<String>> tickets) {

        // Populate "flights" map from the "tickets" data
        tickets.forEach(t -> {
            flights.putIfAbsent(t.get(0), new LinkedList<>());
            flights.get(t.get(0)).add(t.get(1));
        });

        // Sort its neighbour destination list of each each city.
        // Note: Collections.sort() is a in-place stable sort
        flights.forEach((src, dsts) -> {
            Collections.sort(dsts);
        });

        buildItinerary("JFK");
        return results;
    }

    private void buildItinerary(String start) {

        // Java already has a Stack class, but it is legacy class inherited from Vector class, which should be avoided
        // Deques can also be used as LIFO (Last-In-First-Out) stacks. This interface should be used in preference to the legacy Stack class. When a deque is used as a stack, elements are pushed and popped from the beginning of the deque. Stack methods are precisely equivalent to Deque methods as indicated in the table below:
        // Comparison of Stack and Deque methods
        // Stack Method	Equivalent Deque Method
        // push(e)	addFirst(e)
        // pop()	removeFirst()
        // peek()	peekFirst()
        // https://docs.oracle.com/javase/10/docs/api/java/util/Deque.html

        // So in Java, LinkedList should be used if we need a Stack 
        // We use this stack to track the flight itinerary path, which is implicit done by recursion in Solution1
        LinkedList<String> stack = new LinkedList<>();
        
        stack.push(start);

        while (!stack.isEmpty()) {
            
            //"source" is either the starting point or a point backtracked from the previous "deadend" destination
            // Make sure we use "peek()", not "pop()". It's NOT the time to pop the city off the stack yet.
            // "peek" will tell us what is the current node we're checking now.
            String source = stack.peek();

            // Keep looping to the next destination by follow the corresponding ticket
            // edges.
            // When there is no "out" edge, it means this city is a "terminal" node without any "outgoing" edge, so we can add the current "source" node to the
            // "results" as a city which needs to be visited later, similar concept to Topological Sort.
            while (flights.get(source) != null && !flights.get(source).isEmpty()) {
                
                // LinkedList also has "poll()" method, just like PriorityQueue
                source = flights.get(source).poll();
                
                // "push" will be equivalenet to a recursion call
                stack.push(source);
                // dfs(destination);
            }

            // when reach here, it means current "source" doesn't have any remaining edge to
            // go out,
            // so it's time to add to current "source" to the front of "results".
            // Recursion rewind will automatically backtrack to the previous cities
            // currently on the stack
            // one by one.
            // Using recursion here is equivalent to the other Youtube solutions where they
            // use a "stack"
            // explicitly to track the current city traverse path.
            // If use an explicit stack, this is the only place where we need to pop out a city node of the stack.
            results.addFirst(source);
            stack.pop();            
        }
    }

}
