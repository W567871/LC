package remoteIPAddressLC93;
import java.util.*;

// https://leetcode.com/problems/restore-ip-addresses/submissions/848406332/
// Neetcode: https://www.youtube.com/watch?v=61tN4YEdiTM&list=PLot-Xpze53lf5C3HSjCnyFghlW0G1HHXo&index=15

// Didn't watch Neetcode video and completed Solution myself.

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        List<String> results = solution.restoreIpAddresses("25525511135");
        System.out.println(results);    
    }

    String ipStr;
    List<String> results = new ArrayList<>();
    StringBuilder current = new StringBuilder();

    public List<String> restoreIpAddresses(String s) {
        ipStr = s;
        buildIpAddresses(0, 1);
        return results;
    }   
    
    // pos is for current starting position, dotNum is the # of ip dot separator in a valid ip address (1,2,3)
    // a valid ip address has 3 dots in total, e.g.  12.34.09.38
    private void buildIpAddresses(int pos, int dotNum) {
        if (pos >= ipStr.length() || dotNum > 3) {
            
            // if dotNum == 4, it means all 3 dots have been added successfully, and all 3 ip sections have valid ip values (0-255)
            // Now we are checking if the last section, 4th section, of ip address is a valid value or not.
            // If 4th section is valid, append it to "current", and add this new ip address to "results"  
            if (dotNum >3 && pos < ipStr.length()) {
                String lastVal = ipStr.substring(pos);
                if (isValidIP(lastVal)) {
                    current.append(lastVal);
                    results.add(current.toString());
                }                
            }
            return;
        }

        // Backtracking to 3 possible positions: pos + 1, pos + 2, pos +3, because a valid ip address can only have up to 3 digits.
        String val;
        if (pos + 1 < ipStr.length()) {
            val = ipStr.substring(pos, pos + 1);
            if (isValidIP(val)) {

                int idx = current.length();  // remember current index postion before appending new ip section, so we can backtract later
                current.append(val + ".");

                buildIpAddresses(pos + 1, dotNum + 1);

                current.delete(idx, current.length()); // delete should start from idx, not idx -1 !!!
            }
        }

        if (pos + 2 < ipStr.length()) {
            val = ipStr.substring(pos, pos + 2);
            if (isValidIP(val)) {
                int idx = current.length();
                current.append(val + ".");
                buildIpAddresses(pos + 2, dotNum + 1);
                current.delete(idx, current.length());
            }    
        }

        if (pos + 3 < ipStr.length()) {
            val = ipStr.substring(pos, pos + 3);
            if (isValidIP(val)) {
                int idx = current.length();
                current.append(val + ".");
                buildIpAddresses(pos + 3, dotNum + 1);
                current.delete(idx, current.length());
            }
        }
    }

    private boolean isValidIP(String val) {
        if (val == null || val.isEmpty()) {
            return false;
        }

        // "0" is valid ip secontion, e.g. 10.0.2.35
        if ("0".equals(val)) {
            return true;
        }

        // a prefix with more than one "0" is not valid ip, e.g. "012.00.002.09" (all 4 sections are invalid)
        if (val.startsWith("0")) {
            return false;
        }

        // ip section value must be less than 256
        // Note: must use long, not int, because"1 <= s.length <= 20", which is over the range of integer 
        long num = Long.parseLong(val);
        if (num > 255) {
            return false;
        }
        return true;
    }

}
