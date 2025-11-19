import java.util.*;

public class finalCode_c1a_GRP4 {
    // Class variables
    public static Scanner sc = new Scanner (System.in);
    public static boolean isValidInput = true;

    //Overtime Checker
    public static int checkOvertime(int weekHours){
        if (weekHours > 40) {
            return weekHours - 40;
        }
        return 0;
    }

    //Weekly Gross Pay
    public static double computeGP(double ratePerHour, String position, int weekHours, int overtimeHours){
        double basePay = ratePerHour * (weekHours - overtimeHours);
        if (position.equalsIgnoreCase("manager")) basePay += 5000;
        double overtimePay = overtimeHours * (ratePerHour * 1.25);

        return basePay + overtimePay;
    }

    //SSS Computation
    public static double computeSSS(double monthlyGP){
        if(monthlyGP <= 5000) {
            return 105.0;
        } else if(monthlyGP <= 10000) {
            return monthlyGP * 0.05;
        } else if(monthlyGP <= 15000) {
            return (monthlyGP * 0.08) + 75;
        } else {
            return (monthlyGP * 0.12) + 110;
        }
    }

    //PagIbig Computation
    public static double computePagIbig(double monthlyGP){
        if(monthlyGP <= 5000) {
            return 100.0;
        } else {
            return monthlyGP * 0.03;
        }
    }

    //Tax Computation
    public static double computeTax(double monthlyGP){
        if(monthlyGP <= 10000) {
            return monthlyGP * 0.03;
        } else if(monthlyGP <= 25000) {
            return monthlyGP * 0.08;
        } else if(monthlyGP <= 40000) {
            return monthlyGP * 0.11;
        } else {
            return monthlyGP * 0.135;
        }
    }

    //PhilHealth Computation
    public static double computePhilHealth(int monthlyHours){
        if(monthlyHours < 10) {
            return 0.0;
        } else {
            return 120.0;
        }
    }
    public static double computeDependents(int nDependents){
        return nDependents * 1000;
    }

    //Deductions Computation
    public static double computeDed(double monthlyGP, int monthlyHours, int dependents) {
        double sssDeduction = computeSSS(monthlyGP);
        double pagIbigDeduction = computePagIbig(monthlyGP);
        double philHealthDeduction = computePhilHealth(monthlyHours);
        double taxDeduction = computeTax(monthlyGP);
        double dependentsDeduction = computeDependents(dependents);

        //Deductions
        return sssDeduction + pagIbigDeduction + philHealthDeduction + taxDeduction + dependentsDeduction;
    }


    //Net Pay Computation
    public static double computeNP(double grossPay, double deductions) {
        return grossPay - deductions;
    }

    //Retry Prompt
    public static boolean tryAgain(){
        sc.nextLine();
        boolean retry = true;
        do {
            System.out.print("Do you want to try again? Y/N: ");
            String choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")){
                isValidInput = true;
            } else if (choice.equalsIgnoreCase("n")){
                System.out.println("\nPay Roll Computation ended. Thank you for using!");
                retry = false;
                isValidInput = true;
            } else {
                System.out.println("Please enter a valid input.");
                isValidInput = false;
            }
        } while (!isValidInput);
        return retry;
    }


    //Main function
    public static void main(String[]args){
        do {
            String firstName, middleName, lastName, department, position;
            double ratePerHour = 0, monthlyGP = 0;
            int nDependents = 0, monthlyHours = 0, totalOtHours = 0;
            final int weeksInMonth = 4;

            int[] weekHours = new int[weeksInMonth];
            int[] weekOtHours = new int[weeksInMonth];
            double[] weekGP = new double[weeksInMonth];


            System.out.println("\n====================================");
            System.out.println("EMPLOYEE PAY ROLL COMPUTATION");
            System.out.println("====================================");

            //Employee Information Input
            System.out.print("\nFirst Name: ");
            firstName = sc.nextLine();
            System.out.print("Middle Name: ");
            middleName = sc.nextLine();
            System.out.print("Last Name: ");
            lastName = sc.nextLine();
            System.out.print("Department: ");
            department = sc.nextLine();
            System.out.print("Position: ");
            position = sc.nextLine();
            //Rate per hour
            do {
                try {
                    System.out.print("Rate per Hour: ₱ ");
                    ratePerHour = sc.nextDouble();
                    if (ratePerHour < 0) throw new InputMismatchException();
                    isValidInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid input.");
                    isValidInput = false;
                    sc.nextLine();
                }
            } while(!isValidInput);

            //Dependents
            do {
                try {
                    System.out.print("Dependents: ");
                    nDependents = sc.nextInt();
                    if (nDependents < 0) throw new InputMismatchException();
                    isValidInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid input.");
                    isValidInput = false;
                    sc.nextLine();
                }
            } while(!isValidInput);

            //Weekly calculation
            System.out.println("Enter hours worked per week:");
            for(int i = 0; i < weeksInMonth; i++) {
                do {
                    try {
                        System.out.print("Week " + (i + 1) + ": ");
                        weekHours[i] = sc.nextInt();
                        if (weekHours[i] < 0 || weekHours[i] > 168) {
                            isValidInput = false;
                            System.out.println("Weekly hours cannot go below 0 or over 168.");
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid input.");
                        isValidInput = false;
                        sc.nextLine();
                    }
                } while(!isValidInput);

                weekOtHours[i] = checkOvertime(weekHours[i]);
                weekGP[i] = computeGP(ratePerHour, position, weekHours[i], weekOtHours[i]);

                //Monthly calculation
                monthlyHours += weekHours[i];
                totalOtHours += weekOtHours[i];
                monthlyGP += weekGP[i];
            }

            double totalDed = computeDed(monthlyGP, monthlyHours, nDependents);
            double netPay = computeNP(monthlyGP, totalDed);


            //Pay Slip Output
            System.out.println("\n====================================");
            System.out.println("EMPLOYEE PAY SLIP SUMMARY");
            System.out.println("====================================");

            System.out.println("Employee Information");
            System.out.println("-----------------------------------");
            System.out.printf("%-25s : %s %s %s%n", "Name", firstName, middleName, lastName);
            System.out.printf("%-25s : %s%n", "Department", department);
            System.out.printf("%-25s : %s%n", "Position", position);

            System.out.println("-----------------------------------");
            System.out.println("Details of Salary Computation");
            System.out.println("-----------------------------------");
            System.out.printf("%-25s : ₱ %,.2f%n", "Rate per Hour", ratePerHour);
            System.out.printf("%-25s : %d%n", "Amount of Dependents", nDependents);
            System.out.printf("%-25s : %d%n", "Hours Worked (Month)", monthlyHours);
            System.out.printf("%-25s : %d%n", "Overtime Hours", totalOtHours);

            System.out.println("-----------------------------------");
            System.out.println("Deductions");
            System.out.println("-----------------------------------");
            System.out.printf("%-25s : ₱ %,.2f%n", "SSS", computeSSS(monthlyGP));
            System.out.printf("%-25s : ₱ %,.2f%n", "Pag-IBIG", computePagIbig(monthlyGP));
            System.out.printf("%-25s : ₱ %,.2f%n", "Tax", computeTax(monthlyGP));
            System.out.printf("%-25s : ₱ %,.2f%n", "PhilHealth", computePhilHealth(monthlyHours));
            System.out.printf("%-25s : ₱ %,.2f%n", "Dependents", computeDependents(nDependents));
            System.out.printf("%-25s : ₱ %,.2f%n", "Total Deductions", totalDed);

            System.out.println("-----------------------------------");
            System.out.println("Total Pay");
            System.out.println("-----------------------------------");
            System.out.printf("%-25s : ₱ %,.2f%n", "Gross Pay", monthlyGP);
            System.out.printf("%-25s : ₱ %,.2f%n", "Net Pay", netPay);
            System.out.println("====================================");

        } while (tryAgain());
        sc.close();
    }
}