import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
  public static void main(String[] args) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter matrix size: ");
    try {
      int size = Integer.parseInt(reader.readLine());
      List<List<Boolean>> matrix1 = new ArrayList<List<Boolean>>();
      for (int i = 0; i < size; i++) {
        matrix1.add(new ArrayList<Boolean>());
        for (int j = 0; j < size; j ++) {
          matrix1.get(i).add(new Random().nextBoolean());
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
