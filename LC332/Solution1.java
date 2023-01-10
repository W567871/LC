package LC332;

import java.util.*;

// https://leetcode.com/problems/reconstruct-itinerary/submissions/875646440/

//references: https://www.youtube.com/watch?v=WYqsg5dziaQ&t=6s
// https://www.youtube.com/watch?v=vFC5AzIS4Zs&t=2s
// https://www.youtube.com/watch?v=ZyB_gQ8vqGA&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=26

// The difference of Solution1 and Solution is replacing PriorityQueue with LinkedList in "flights" map,
// because LinkedList is simpler than PriorityQueue, and LinkedList is good enough to provide a sorted
// list of "desitination" cities which can be updated dynamically at runtime during "ticket edge" traversal. 

// The idea is to use DFS to traverse all the ticket edges. It's not exactly a backtracking strategy,
// because each traversed path will contribute the final result, and there is no "wrong" path which NOT
// contribute to the final result.
// This Solution's time complexity is O(n), because each node and edge will be traversed once.   
class Solution1 {

    // Java doesn't have something like multiset in C++, Java PriorityQueue is a good option,
    // but LinkedList is a much simpler solution than PriorityQueue. Java LinkedList is also a Queue
    // and a Dequeue.
    private Map<String, LinkedList<String>> flights = new HashMap<>();

    // "results" need to be inserted from fron, so choose LinkedList, which supports Dequeue.addFirst()
    private LinkedList<String> results = new LinkedList<>();

    public static void main(String[] args){

        // read test data copied from Leetcode test cases 
        String[][] input = 
        {{"EZE","AXA"},{"TIA","ANU"},{"ANU","JFK"},{"JFK","ANU"},{"ANU","EZE"},{"TIA","ANU"},{"AXA","TIA"},{"TIA","JFK"},{"ANU","TIA"},{"JFK","TIA"}};
       
        // smaller test case
        // {{"MUC","LHR"},{"JFK","MUC"},{"SFO","SJC"},{"LHR","SFO"}};
        
        //Build list from array
        List<List<String>> tickets = new ArrayList<>(); 
        
        // Arrays.stream(input).forEach(e -> { List<String> l = new ArrayList<>(); l.add(e[0]); l.add(e[1]); tickets.add(l);} );
        // Make code more readable by using helper method "addTicket" 
        Arrays.stream(input).forEach(e -> addTicket(tickets, e));

        Solution1 s = new Solution1();
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
        tickets.forEach(t -> { flights.putIfAbsent(t.get(0), new LinkedList<>());
             flights.get(t.get(0)).add(t.get(1)); });

        // Sort its neighbour destination list of each each city.
        // Note: Collections.sort() is a in-place stable sort      
        flights.forEach((src, dsts) -> { Collections.sort(dsts);});

        dfs("JFK");
        return results;    
    }

    private void dfs(String source) {

        // Keep recurse to the next destination by follow the corresponding ticket edges.
        // When there is no "out" edge, then add the current "source" node to the "results"
        while (flights.get(source) != null && !flights.get(source).isEmpty()) {
            // LinkedList also has "poll()" method, just like PriorityQueue
            String destination = flights.get(source).poll();
            dfs(destination);
        } 
        
        // when reach here, it means current "source" doesn't have any remaining edge to go out,
        // so it's time to add to current "source" to the front of "results".
        // Recursion rewind will automatically backtrack to the previous cities currently on the stack
        // one by one. 
        // Using recursion here is equivalent to the other Youtube solutions where they use a "stack" 
        // explicitly to track the current city traverse path
        results.addFirst(source);
    }

}
