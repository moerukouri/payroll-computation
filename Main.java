import java.util.Scanner;

public class Main {

    //Overtime Checker
    public static int checkOvertime(int hours){
        int overtimeHours = 0;
        if (hours > 40) overtimeHours = hours - 40;

        return overtimeHours;
    }


    //Weekly Gross Pay
    public static double computeGP(double ratePerHour, int hours, int overtimeHours){
        double basePay = ratePerHour * (hours - overtimeHours);
        double overtimePay = overtimeHours * (ratePerHour * 1.25);

        return basePay + overtimePay;
    }


    //Deductions Computation
    public static double computeDed(double monthlyGP, int monthHours, int dependents) {
        double sss, pagIbig = (monthlyGP * 0.03), philHealth = 0, tax;

        // SSS and pagIbig
        if(monthlyGP <= 5000) {
            sss = 105.0;
            pagIbig = 100.0;
        } else if(monthlyGP <= 10000) {
            sss = monthlyGP * 0.05;
        } else if(monthlyGP <= 15000) {
            sss = (monthlyGP * 0.08) + 75;
        } else {
            sss = (monthlyGP * 0.12) + 110;
        }

        // Tax
        if(monthlyGP <= 10000) {
            tax = monthlyGP * 0.03;
        } else if(monthlyGP <= 25000) {
            tax = monthlyGP * 0.08;
        } else if(monthlyGP <= 40000) {
            tax = monthlyGP * 0.11;
        } else {
            tax = monthlyGP * 0.135;
        }

        //PhilHealth
        if(monthHours >= 10) philHealth = 120.0;


        //Dependents
        double dependDed = dependents * 1000;

        //Deductions
        return sss + pagIbig + philHealth + tax + dependDed;
    }


    //Net Pay Computation
    public static double computeNP(double grossPay, String position, double deductions, int weeksInMonth) {
        double netPay = grossPay - deductions;

        if (position.equalsIgnoreCase("manager")) netPay += (5000.0 * weeksInMonth);
        return netPay;
    }

    public static boolean tryAgain(Scanner sc){
        sc.nextLine();
        while (true) {
            System.out.print("Do you want to try again? Y/N: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")){
                return true;
            } else if (choice.equalsIgnoreCase("n")){
                System.out.println("\nPay Roll Computation Ended. Thank you for using!");
                return false;
            } else {
                System.out.println("That is not a valid input.");
            }
        }
    }

    public static void main(String[]args){
        Scanner sc = new Scanner (System.in);

        do {
            System.out.println("\n====================================");
            System.out.println("EMPLOYEE PAY ROLL COMPUTATION");
            System.out.println("====================================");
            System.out.print("\nFirst Name: ");
            String firstName = sc.nextLine();
            System.out.print("Middle Name: ");
            String middleName = sc.nextLine();
            System.out.print("Last Name: ");
            String lastName = sc.nextLine();
            System.out.print("Department: ");
            String dept = sc.nextLine();
            System.out.print("Position: ");
            String position = sc.nextLine();
            System.out.print("Rate per Hour: ₱ ");
            double ratePerHour = sc.nextDouble();
            sc.nextLine();
            System.out.print("Dependents: ");
            int dependents = sc.nextInt();
            sc.nextLine();

            int weeksInMonth = 4;
            int monthHours = 0, totalOtHours = 0;
            double monthGP = 0;
            int[] hours = new int[weeksInMonth];
            int[] overtimeHours = new int[weeksInMonth];
            double[] weekGP = new double[weeksInMonth];

            System.out.println("Enter hours worked per week:");
            for(int i = 0; i < weeksInMonth; i++) {
                System.out.print("Week " + (i+1) + ": ");
                hours[i] = sc.nextInt();
                overtimeHours[i] = checkOvertime(hours[i]);
                weekGP[i] = computeGP(ratePerHour, hours[i], overtimeHours[i]);

                monthGP += weekGP[i];
                monthHours += hours[i];
                totalOtHours += overtimeHours[i];
            }

            double totalDed = computeDed(monthGP, monthHours, dependents);
            double netPay = computeNP(monthGP, position, totalDed, weeksInMonth);

            System.out.println("\n====================================");
            System.out.println("EMPLOYEE PAY SLIP SUMMARY");
            System.out.println("====================================");
            System.out.println("Employee Information");
            System.out.println("-----------------------------------");
            System.out.printf("%-25s : %s %s %s%n", "Name", firstName, middleName, lastName);
            System.out.printf("%-25s : %s%n", "Department", dept);
            System.out.printf("%-25s : %s%n", "Position", position);
            System.out.println("-----------------------------------");
            System.out.println("Details of Salary Computation");
            System.out.println("-----------------------------------");
            System.out.printf("%-25s : ₱ %,.2f%n", "Rate per Hour", ratePerHour);
            System.out.printf("%-25s : %d%n", "Dependents", dependents);
            System.out.printf("%-25s : %d%n", "Hours Worked (Month)", monthHours);
            System.out.printf("%-25s : %d%n", "Overtime Hours", totalOtHours);
            System.out.printf("%-25s : ₱ %,.2f%n", "Deductions", totalDed);
            System.out.println("-----------------------------------");
            System.out.println("Total Pay");
            System.out.println("-----------------------------------");
            System.out.printf("%-25s : ₱ %,.2f%n", "Gross Pay", monthGP);
            System.out.printf("%-25s : ₱ %,.2f%n", "Net Pay", netPay);
            System.out.println("====================================");

        } while (tryAgain(sc));
        sc.close();
    }
}