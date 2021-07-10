import java.util.Scanner;

class MarslanderTwo {
    private static final int MARGIN_TO_SURFACE = 20;
    private static final int SPEED_MARGIN = 5;
    private static final int MAX_VERTICAL_SPEED = 40;
    private static final int MAX_HORIZONTAL_SPEED = 20;
    private static final double GRAVITY_ON_MARS = 3.711;

    private int x, y, horizontalSpeed, verticalSpeed, fuel, angle, thrustPower;
    private int landingSurfaceStartX, landingSurfaceEndX, landingSurfaceY;

    public MarslanderTwo() {}

    public void init (int x, int y, int horizontalSpeed, int verticalSpeed, int fuel, int angle, int power) {
        this.x = x;
        this.y = y;
        this.horizontalSpeed = horizontalSpeed;
        this.verticalSpeed = verticalSpeed;
        this.fuel = fuel;
        this.angle = angle;
        this.thrustPower = power;
    }

    public void setLandingTarget(int landingSurfaceStartX, int landingSurfaceEndX, int landingSurfaceY) {
        this.landingSurfaceStartX = landingSurfaceStartX;
        this.landingSurfaceEndX = landingSurfaceEndX;
        this.landingSurfaceY = landingSurfaceY;
    }

    public boolean isOverTarget() {
        return landingSurfaceStartX <= x && x <= landingSurfaceEndX;
    }

    public boolean isFinishing() {
        return y < landingSurfaceY + MARGIN_TO_SURFACE;
    }

    public boolean hasSafeSpeed() {
        return Math.abs(MarslanderTwo.this.horizontalSpeed) <= MAX_HORIZONTAL_SPEED - SPEED_MARGIN && Math.abs(verticalSpeed) <= MAX_VERTICAL_SPEED - SPEED_MARGIN;
    }

    public boolean goesInWrongDirection() {
        return (x < landingSurfaceStartX && MarslanderTwo.this.horizontalSpeed < 0) || (landingSurfaceEndX < x && MarslanderTwo.this.horizontalSpeed > 0);
    }

    public boolean goesTooFastHorizontally() {
        return Math.abs(MarslanderTwo.this.horizontalSpeed) > 4 * MAX_HORIZONTAL_SPEED;
    }

    public boolean goesTooSlowHorizontally() {
        return Math.abs(MarslanderTwo.this.horizontalSpeed) < 2 * MAX_HORIZONTAL_SPEED;
    }

    /**
     * Calculate the angle to slow down.
     * First determine the global speed with Pythagoras based on the given horizontal and  vertical speed.
     * Dividing the horizontal speed by this global speed, returns the sinus of the slowing-down angle.
     * Now we need to inverse this sinus and cast it into degrees.
     */
    public int angleToSlow() {
        double speed = Math.sqrt(MarslanderTwo.this.horizontalSpeed * MarslanderTwo.this.horizontalSpeed + verticalSpeed * verticalSpeed);
        return (int) Math.toDegrees(Math.asin((double)MarslanderTwo.this.horizontalSpeed / speed));
    }

    /**
     * Calculate the angle to neutralise gravity while moving horizontally to the landing zone.
     */
    public int angleToAimTarget() {
        int angle = (int) Math.toDegrees(Math.acos(GRAVITY_ON_MARS / 4.0));
        if (x < landingSurfaceStartX)
            return -angle;
        else if (landingSurfaceEndX < x)
            return angle;
        else
            return 0;
    }

    /**
     * returns the thrust power needed to aim a null vertical speed
     */
    public int powerToHover() {
        return (verticalSpeed >= 0) ? 3 : 4;
    }
}

class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        MarslanderTwo ship = new MarslanderTwo();
        int N = in.nextInt();

        // Looking for the landing area
        int landX, landY, prevX, prevY;
        prevX = prevY = -1;
        for (int i = 0; i < N; i++) {
            landX = in.nextInt();
            landY = in.nextInt();
            if (landY == prevY) {
                ship.setLandingTarget(prevX, landX, landY);
            } else {
                prevX = landX;
                prevY = landY;
            }
        }

        while (true) {

            /* The flight follows 2 steps :
            - first the rover goes over the landing zone by
                -- slowing if it goes faster than 4*MAX_HS, or in the wrong direction
                -- accelerating while hovering until it reaches 2*MAX_HS if it goes in the right direction
                -- waiting hovering if it has a speed between 2*MAX_HS and 4*MAX_HS

            - then it slows down to meet speed specification (going back
            to step 1 if it goes out of the landing zone)
            */

            ship.init(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());

            //
            if (!ship.isOverTarget()) {
                if (ship.goesInWrongDirection() || ship.goesTooFastHorizontally())
                    System.out.println(ship.angleToSlow() + " 4");
                else if (ship.goesTooSlowHorizontally())
                    System.out.println(ship.angleToAimTarget() + " 4");
                else
                    System.out.println("0 " + ship.powerToHover());
            }
            else {
                if (ship.isFinishing())
                    System.out.println("0 3");
                else if (ship.hasSafeSpeed())
                    System.out.println("0 2");
                else
                    System.out.println(ship.angleToSlow() + " 4");
            }
        }
    }
}






