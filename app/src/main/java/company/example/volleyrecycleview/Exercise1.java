package company.example.volleyrecycleview;

/**
 * Created by JoseLuis on 30/03/2016.
 */
public class Exercise1 {

//Write an algorithm to check if an array of characters is a palindrome
// example: anitalavalatina —> output: true
// example: anitavalane —> output: false

    public static void main(String []args) {
        char[] mArray = {'a', 'n', 'i', 't', 'a', 'l', 'a', 'v', 'a', 'l', 'a', 't', 'i', 'n', 'a'};
        int tamano = mArray.length-1;
        for (int i = 0; i<=mArray.length-1; i++) {

            if (mArray[i] != mArray[tamano - i]) {
                System.out.println("false");
            }else if (i==tamano){
                System.out.println("true");
                return;
            }
        }
    }
}
