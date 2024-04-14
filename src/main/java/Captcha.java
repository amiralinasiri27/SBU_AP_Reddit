import java.util.Random;
import java.util.Scanner;

public class Captcha {
    private String code;
    private Random random;
    private int numOfCapitals;
    private int situation = 0;

    public Captcha() {
        int[] nums = new int[2];
        char[] chars = new char[4];

        this.code = "";
        this.random = new Random();
        this.numOfCapitals = (int)(Math.random() * 3) + 1;

        for (int i = 0; i < 2; i++) {
            nums[i] = (int)(Math.random() * 10);
            code += nums[i];
        }

        for (int i = 0; i < numOfCapitals; i++) {
            // 65 - 90
            int temp = random.nextInt(26) + 65;
            chars[i] = (char)(temp);
            code += chars[i];
        }

        for (int i = numOfCapitals; i < 4 ; i++) {
            // int temp = 97 - 122
            int temp = random.nextInt(28) + 97;
            chars[i] = (char)(temp);
            code += chars[i];
        }
    }

    public int getSituation() {
        return this.situation;
    }

    public boolean validateCaptcha(Scanner scanner) {
        System.out.println("Captcha Code : " + this.code);
        System.out.print("Enter The CaptchaCode (0 For Ending Program) %$#--->: ");
        String input = scanner.nextLine();

        if (this.code.equals(input)) {
            return true;
        }
        if (input.equals("0")) {
            this.situation = 1;
            return true;
        }

        return false;
    }
}
