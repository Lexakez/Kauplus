

package tools;

import java.util.Scanner;


public class InputProtection {
    
    public static int intInput(int beginRange, int endRange){
        Scanner scanner = new Scanner(System.in);
        int number = -1;
        do{
            try {
                number = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Please enter a number: ");
                continue;
            }
            if(number >= beginRange && number <= endRange){
                return number;
            }else{
                System.out.printf("Please enter a number (%d .. %d): ",beginRange, endRange);
                continue;
            }
        }while(true);
        
    }
        public static double doubleInput(double beginRange, double endRange) {
        Scanner scanner = new Scanner(System.in);
        double number = -1;
        do {
            try {
                number = scanner.nextDouble();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.print("Please enter a number: ");
                continue;
            }
            if (number >= beginRange && number <= endRange) {
                return number;
            } else {
                System.out.printf("Please enter a number (%.2f .. %.2f): ", beginRange, endRange);
                continue;
            }
        } while (true);
    }
}
