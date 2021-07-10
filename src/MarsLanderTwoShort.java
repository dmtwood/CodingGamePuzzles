import java.util.Scanner;

public class MarsLanderTwoShort {

    public static void main(String args[]) {
        int previousX = 0;
        int previousY = 0;
        double targetX = 0, targetY = 0;
        double vertSpeed , horizSpeed = 0;

        Scanner in = new Scanner(System.in);
        int surfaceN = in.nextInt(); // the number of points used to draw the surface of Mars.
        for (int i = 0; i < surfaceN; i++) {
            int landX = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            int landY = in.nextInt(); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.

            // determine center of landing surface
            // assuming there is only one surface, otherwise you need to check landY - previousY >= 1_000
            if (landY == previousY) {
                targetX = (landX + previousX) * 0.5;
                targetY = landY;
            } else {
                previousX = landX;
                previousY = landY;
            }
        }

        while (true) {
            int X = in.nextInt();
            int Y = in.nextInt();
            int hSpeed = in.nextInt(); // the horizontal speed (in m/s), can be negative.
            int vSpeed = in.nextInt(); // the vertical speed (in m/s), can be negative.
            int fuel = in.nextInt(); // the quantity of remaining fuel in liters.
            int rotate = in.nextInt(); // the rotation angle in degrees (-90 to 90).
            int power = in.nextInt(); // the thrust power (0 to 4).

            horizSpeed = (targetX - X) - (hSpeed * Math.abs(hSpeed));
            vertSpeed = (targetY - Y) * 2;

            //
            rotate = (int) Math.toDegrees(Math.atan(vertSpeed /horizSpeed));

            if(Math.abs(vSpeed)>30 || Math.abs(hSpeed)>20) {
                power=4;
            }else {
                power=3;
            }

            if(horizSpeed>-200) {
                rotate=0;
            }

            System.out.println(rotate+" "+power);

        }
    }
}