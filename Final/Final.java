package Project02.Final;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Final {

    private static int g = 10;     //gravity 10 m/s^2
    private static int m0 = 0;     //0 kg
    private static int v0 = 0;
    private static double m1, m2, m3;
    private static int t0 = 0;          //0 second
    private static int f0 = 0;
    private static double f1, f2, f3;   //friction
    private static double xd = 0;       //default case
    private static double yd = 0;       //default case
    private static double x1, y1, x2, y2, x3, y3;
    private static double force2, force3;
    private static double acc2, acc3;
    private static double rope, ropeH, ropeV;


    private static int timeChecker = 0;
    private static int M1DisChecker = 0;


    //arraylists


    private static ArrayList<Double> times = new ArrayList<>();

    private static ArrayList<Double> velocities = new ArrayList<>();

    private static ArrayList<Double> forces = new ArrayList<>();
    private static ArrayList<Integer> forceChangesTimes = new ArrayList<>();

    private static ArrayList<Double> force1 = new ArrayList<>();
    private static ArrayList<Double> acc1 = new ArrayList<>();
    private static ArrayList<Double> displacementByTimeOfM1 = new ArrayList<>();

    private static ArrayList<Double> x1Position = new ArrayList<>();
    private static ArrayList<Double> x1PositionAtForceChange = new ArrayList<>();
    private static ArrayList<Double> x2Position = new ArrayList<>();
    private static ArrayList<Double> x2PositionAtForceChange = new ArrayList<>();
    private static ArrayList<Double> x3Position = new ArrayList<>();
    private static ArrayList<Double> x3PositionAtForceChange = new ArrayList<>();
    private static ArrayList<Double> y3Position = new ArrayList<>();


    //scanner


    static Scanner scan = new Scanner(System.in);


    //utilities


    private static ArrayList swap(ArrayList<Double> arr, int a, int b) {
        double temp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, temp);

        return arr;
    }

    private static ArrayList sort(ArrayList<Double> arr) {

        int n = arr.size();

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (arr.get(j) > arr.get(j + 1)) {
                    swap(arr, j, j + 1);
                }

            }

        }

        return arr;

    }

    private static void duplicateTimeRemoveSorted(ArrayList<Double> arr) {

        for (int i = 1; i < arr.size(); i++) {

            if (arr.get(i) == arr.get(i - 1)) {

                i = i - 1;
                arr.remove(i);

            }

        }

    }


    //inputs


    private static void initialPosition1() {

        System.out.println("Input the initial position for x1:");
        double inpx1 = scan.nextDouble();
        x1 = inpx1;

        System.out.println("Input the initial position for y1:");
        double inpy1 = scan.nextDouble();
        y1 = inpy1;

    }

    private static void initialPosition2() {

        System.out.println("Input the initial position for x2:");
        double inpx2 = scan.nextDouble();
        if (inpx2 >= x1) {
            System.out.println("Wrong input, x2 cannot be the left side of (x1,y1)");
            initialPosition2();
        }
        else{
            x2 = inpx2;
        }

        y2 = y1;

    }

    private static void initialPosition3() {

        System.out.println("Input the initial position for y3:");
        double inpy3 = scan.nextDouble();
        if (inpy3 > y1) {
            System.out.println("Wrong input, y3 cannot be above (x2,y2)");
            initialPosition3();
        }
        y3 = inpy3;

//        System.out.println("Input the initial position for x3:");
//        double inpx3 = scan.nextDouble();
//        x3 = inpx3;
        x3 = x1;

    }

    private static double frictionOne() {

        System.out.println("Input the friction for object 1:");
        Double inp = scan.nextDouble();
        if (inp < 0 || inp > 0.5) {
            System.out.println("Friction has to be between/including 0 and 0.5");
            return frictionOne();
        }
        f1 = inp;

        return f1;
    }

    private static double frictionTwo() {

        System.out.println("Input the friction for object 2:");
        Double inp = scan.nextDouble();
        if (inp < 0 || inp > 0.5) {
            System.out.println("Friction has to be between/including 0 and 0.5");
            return frictionTwo();
        }
        f2 = inp;

        return f2;
    }

    private static double frictionThree() {

        System.out.println("Input the friction for object 3:");
        Double inp = scan.nextDouble();
        if (inp < 0 || inp > 0.5) {
            System.out.println("Friction has to be between/including 0 and 0.5");
            return frictionThree();
        }
        f3 = inp;

        return f3;
    }

    private static double massOne() {
        System.out.println("Input the mass for object 1:");
        Double inp = scan.nextDouble();
        if (inp < 0 || inp > 10) {
            System.out.println("mass has to be between/including 0kg and 10kg");
            return massOne();
        }
        m1 = inp;

        return m1;
    }

    private static double massTwo() {
        System.out.println("Input the mass for object 2:");
        Double inp = scan.nextDouble();
        if (inp < 0 || inp > 10) {
            System.out.println("mass has to be between/including 0kg and 10kg");
            return massTwo();
        }
        m2 = inp;

        return m2;
    }

    private static double massThree() {
        System.out.println("Input the mass for object 3:");
        Double inp = scan.nextDouble();
        if (inp < 0 || inp > 10) {
            System.out.println("mass has to be between/including 0kg and 10kg");
            return massThree();
        }
        m3 = inp;

        return m3;
    }

    private static void time() {

        System.out.println("Do you want to input a new time?(if you want to add answer with yes otherwise is no)");
        String answer = scan.next();
        if (answer.equals("yes")) {
            System.out.print("please input your number: ");
            Double inp = scan.nextDouble();
            if (inp < 0) {          // a case if user mistakenly writes minus time
                System.out.println("The time cannot be minus. Try again.");
                time();
            }
            times.add(inp);
            time();
        }

    }

    private static void force() {

        if (forces.size() == 0) {

            System.out.println("How much is the driven force of F(in N):");
            boolean limit = true;

            while (limit == true) {

                Double inp = scan.nextDouble();

                if (inp < -301 || inp > 301) {

                    System.out.println("The force has to be between -300N and 300N, please re-enter the force: ");
                    continue;

                }

                forces.add(inp);
                forceChangesTimes.add(0);
                limit = false;

            }

        }
        System.out.println("Do you want to change the amount of force at a given time?");
        String answer = scan.next();
        if (answer.equals("yes")) {
            System.out.print("At what time(second) do you want to change the force amount? ");
            int timeInput = scan.nextInt();

            if (timeInput < 0) {          // a case if user mistakenly writes minus time
                System.out.println("The time cannot be minus. Try again.");
                force();
            }

            if (timeInput <= timeChecker) {
                System.out.println("The time cannot be less or equal than the last time given.");
                force();
            }

            timeChecker = timeInput;

            System.out.println("How much is the amount of the force?");

            boolean limit = true;

            while (limit == true) {

                Double forceInput = scan.nextDouble();

                if (forceInput < -301 || forceInput > 301) {

                    System.out.println("The force has to be between -300N and 300N, please re-enter the force: ");
                    continue;

                }

                forceChangesTimes.add(timeInput);
                forces.add(forceInput);
                force();
                limit = false;

            }

        }
    }


    //formulas


    private static void ropeLengthCalculator() {

        ropeH = x1 - x2;

        ropeV = y1 - y3;

        rope = (x1 - x2) + (y1 - y3);

    }

    private static void initialVelocityOfM1Finder() {

        velocities.add((double) v0);

        double initial;

        double a, d;

        int t;

        for (int i = 1; i < forces.size(); i++) {

            a = acc1.get(i);
            t = forceChangesTimes.get(i);

            if (t == 0) {
                velocities.add((double) 0);
                continue;
            }

            d = displacementOfM1ByForceChange(t);

            initial = (d / t) - ((1 / 2) * (a) * (t));
            velocities.add(initial);

        }

    }

    private static Double displacementOfM1ByForceChange(int time) {

        double s = (velocities.get(M1DisChecker) * time) + ((0.5) * (time * time) * (acc1.get(M1DisChecker)));

        M1DisChecker = M1DisChecker + 1;

        displacementByTimeOfM1.add(s);

        return s;

    }


    private static void forceOfM1() {

        for (int i = 0; i < forces.size(); i++) {

            double ftemp = forces.get(i) - (forces.get(i) * f1);
            force1.add(ftemp);

        }

    }

    private static void accOfM1() {

        for (int i = 0; i < force1.size(); i++) {

            double acc = force1.get(i) / (m1 + m2 + m3);
            acc1.add(acc);

        }

    }

    private static double displacementOfM1(double time) {

        int finder = -1;

        for (int i = 0; i < forceChangesTimes.size() - 1; i++) {

            if (time >= forceChangesTimes.get(i)) {

                finder = finder + 1;
                continue;

            }
            if (time < forceChangesTimes.get(i)) {
                break;
            }
        }

        double s = (velocities.get(finder) * time) + ((0.5) * (time * time) * (acc1.get(finder)));

        return s;

    }


    private static void forceOfM2() {

        double ftemp = m2 * acc2 * f2;      //no need
        force2 = ftemp;

    }

    private static void accOfM2() {

        double acc = g * (m3 - (f3 * m3) - (f2 * m2)) / ((2 * m2) + (m3));
        acc2 = acc;
        if (m2 >= m3) {
            acc2 = 0;
        }

    }

    private static double displacementOfM2(double time) {

//        double s = (v0 * time) + ((0.5) * (time * time) * (acc2));
//
//        //double tempRopeH = ropeH;
//
//        if (s >= ropeH) {
//            return ropeH;
//        }
        double s = displacementOfM3(time);

        return s;

    }


    private static void forceOfM3() {

        double ftemp = f3 * m3 * g;         //wrong and no need
        force3 = ftemp;

    }

    private static void accOfM3() {

        double acc = g * (m3 - (f3 * m3) - (f2 * m2)) / ((2 * m2) + (m3));
        acc3 = acc;
        if (m2 >= m3) {
            acc3 = 0;
        }

    }

    private static double displacementOfM3(double time) {

        double s = (v0 * time) + ((0.5) * (time * time) * (acc3));

        //double tempRopeH = rope;

        if (s >= ropeH) {
            return ropeH;
        }

        return s;

    }


    //final position calculators


    private static void forceChangeTimeFixer() {
        forceChangesTimes.add(99999);
    }

    private static void x1DefaultPositionCalculator() {

        double tempP;

        if (x1PositionAtForceChange.size() == 0) {

            x1PositionAtForceChange.add(x1);

        }

        for (int i = 1; i < forceChangesTimes.size() - 1; i++) {

            tempP = x1PositionAtForceChange.get(i - 1) - displacementOfM1(forceChangesTimes.get(i));
            x1PositionAtForceChange.add(tempP);

        }
    }

    private static void x2DefaultPositionCalculator() {

        double tempP;

        if (x2PositionAtForceChange.size() == 0) {

            x2PositionAtForceChange.add(x2);

        }

        for (int i = 1; i < forceChangesTimes.size() - 1; i++) {

            tempP = x2PositionAtForceChange.get(i - 1) + displacementOfM2(times.get(i)) - displacementOfM1(forceChangesTimes.get(i));
            x2PositionAtForceChange.add(tempP);

        }
    }

    private static void x3DefaultPositionCalculator() {

        double tempP;

        if (x3PositionAtForceChange.size() == 0) {

            x3PositionAtForceChange.add(x3);

        }

        for (int i = 1; i < forceChangesTimes.size() - 1; i++) {

            tempP = x3PositionAtForceChange.get(i - 1) - displacementOfM1(forceChangesTimes.get(i));
            x3PositionAtForceChange.add(tempP);

        }
    }

    private static void x1PositionCalculator() {

        double tempP;

        for (int i = 0; i < times.size(); i++) {

            for (int j = 1; j < forceChangesTimes.size(); j++) {

                if (times.get(i) >= forceChangesTimes.get(j - 1) && times.get(i) < forceChangesTimes.get(j)) {
                    tempP = x1PositionAtForceChange.get(j - 1) - displacementOfM1(times.get(i));
                    x1Position.add(tempP);
                    break;
                }
            }

        }


    }

    private static void x2PositionCalculator() {

        double tempP;

        for (int i = 0; i < times.size(); i++) {

            for (int j = 1; j < forceChangesTimes.size(); j++) {

                if (times.get(i) >= forceChangesTimes.get(j - 1) && times.get(i) < forceChangesTimes.get(j)) {
                    tempP = x2PositionAtForceChange.get(j - 1) + displacementOfM2(times.get(i)) - displacementOfM1(times.get(i));
                    x2Position.add(tempP);
                    break;
                }
            }


        }

    }

    private static void x3PositionCalculator() {

        double tempP;

        for (int i = 0; i < times.size(); i++) {

            for (int j = 1; j < forceChangesTimes.size(); j++) {

                if (times.get(i) >= forceChangesTimes.get(j - 1) && times.get(i) < forceChangesTimes.get(j)) {
                    tempP = x3PositionAtForceChange.get(j - 1) - displacementOfM1(times.get(i));
                    x3Position.add(tempP);
                    break;
                }
            }
        }
    }

    private static void y3PositionCalculator() {

        double tempP;

        for (int i = 0; i < times.size(); i++) {

            tempP = y3 - displacementOfM3(times.get(i));
            y3Position.add(tempP);

        }
    }


    //printer methods


    private static void massPrint() {

        System.out.print("The mass of object 1 is: ");
        System.out.println(m1 + "kg");
        System.out.print("The mass of object 2 is: ");
        System.out.println(m2 + "kg");
        System.out.print("The mass of object 3 is: ");
        System.out.println(m3 + "kg");

        System.out.println();

    }

    private static void frictionPrint() {

        System.out.print("The friction of object 1 is: ");
        System.out.println(f1);
        System.out.print("The friction of object 2 is: ");
        System.out.println(f2);
        System.out.print("The friction of object 3 is: ");
        System.out.println(f3);

        System.out.println();

    }

    private static void timesPrint() {

        System.out.println("The given times are:");
        String tPrinter = "t";
        for (int i = 0; i < times.size(); i++) {
            System.out.print(tPrinter + (i) + ": ");
            System.out.print(times.get(i));
            System.out.println(" seconds");
        }

        System.out.println();

    }

    private static void forceChangePrint() {

        System.out.println("Forces amount and time changes are at:");
        String NPrinter = "N";
        for (int i = 0; i < forceChangesTimes.size() - 1; i++) {
            System.out.print("At second " + forceChangesTimes.get(i) + ": ");
            System.out.print(forces.get(i));
            System.out.println(NPrinter);
        }

        System.out.println();

    }

    private static void defaultPositionPrint() {

        System.out.println("All (x,y)  default positions are: ");
        System.out.println("1 : " + "(" + x1 + "," + y1 + ")");
        System.out.println("2 : " + "(" + x2 + "," + y2 + ")");
        System.out.println("3 : " + "(" + x3 + "," + y3 + ")");

        System.out.println();

    }

    private static void velocityPrint() {

        System.out.println("Initial Velocities at force changing times are: ");
        String VPrinter = "m/s";
        for (int i = 0; i < forceChangesTimes.size() - 1; i++) {
            System.out.print("At second " + forceChangesTimes.get(i) + " is: ");
            System.out.print(velocities.get(i));
            System.out.println(VPrinter);
        }

        System.out.println();

    }

    private static void ropePrint() {

        System.out.println("The length of the rope is: " + rope + "m");

        System.out.println();

    }

    private static void positionsPrint() {

        System.out.println();
        System.out.println("The positions at needed times are: ");
        String tPrinter = "t";
        for (int i = 0; i < times.size(); i++) {
            System.out.println();
            System.out.print("Positions at " + times.get(i) + "s are: ");

            System.out.print("1:(" + x1Position.get(i) + "," + y1 + "), ");
            System.out.print("2:(" + x2Position.get(i) + "," + y2 + "), ");
            System.out.println("3:(" + x3Position.get(i) + "," + y3Position.get(i) + "), ");

        }

    }


    //final printer


    public static void resultPrinter() {

        massPrint();          //masses

        frictionPrint();        //frictions

        timesPrint();           //times

        forceChangePrint();      //Force change times and forces

        ropePrint();            //rope

        defaultPositionPrint();       //default positions

        velocityPrint();     //Force change times initial velocity

        positionsPrint();       //printing positions

    }


    //run


    public static void run() {

        massOne();
        massTwo();
        massThree();


        frictionOne();
        frictionTwo();
        frictionThree();


        initialPosition1();
        initialPosition2();
        initialPosition3();


        ropeLengthCalculator();


        force();
        forceChangeTimeFixer();
        forceOfM1();
        forceOfM3();
        forceOfM2();


        accOfM1();
        accOfM2();
        accOfM3();

        initialVelocityOfM1Finder();


        time();
        sort(times);
        duplicateTimeRemoveSorted(times);

        x1DefaultPositionCalculator();
        x2DefaultPositionCalculator();
        x3DefaultPositionCalculator();

        x1PositionCalculator();
        x2PositionCalculator();
        x3PositionCalculator();
        y3PositionCalculator();


        resultPrinter();

    }


    //main


    public static void main(String[] args) {

        run();


    }
}