import java.util.ArrayList;

public class TwoDArrayListExample {
    public static void main(String[] args) {
        // Create a 2D ArrayList
        ArrayList<ArrayList<Integer>> twoDArrayList = new ArrayList<>();

        // Add rows to the 2D ArrayList
        for (int i = 0; i < 3; i++) {
            twoDArrayList.add(new ArrayList<>());
        }

        // Add elements to the rows
        twoDArrayList.get(0).add(1);
        twoDArrayList.get(0).add(2);
        twoDArrayList.get(1).add(3);
        twoDArrayList.get(1).add(4);
        twoDArrayList.get(2).add(5);
        twoDArrayList.get(2).add(6);

        // Print the 2D ArrayList
        System.out.println(twoDArrayList);
    }
}