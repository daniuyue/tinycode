import java.util.Arrays;

public class demo {

    public static void main(String[] args) {
        boolean flag = Solution.oneEditAway("a", "a");
        System.out.println(flag);
    }

}

class Solution {
    public static boolean oneEditAway(String first, String second) {
        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();
        Arrays.sort(firstChars);
        Arrays.sort(secondChars);
        int length = firstChars.length;
        int length2 = secondChars.length;
        if (length - length2 >= -1 && length - length2 <= 1) {
            if (length == length2){
                return true;
            }
            if (length - length2 > 0) {
                String firstSub = first.substring(0, length2);
                if (firstSub.equals(second)) {
                    return true;
                }
            } else {
                String secondSub = Arrays.toString(secondChars).substring(0, length);
                if (secondSub.equals(first)) {
                    return true;
                }
            }
        }
        return false;
    }
}



