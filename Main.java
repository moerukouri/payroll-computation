import java.util.Scanner;

public class Main {

    // Payroll Constants
    private static final int STANDARD_WORK_HOURS_PER_WEEK = 40;
    private static final double OVERTIME_MULTIPLIER = 1.25;
    private static final int WEEKS_IN_MONTH = 4;

    // SSS (Social Security System) Constants
    private static final double SSS_TIER1_THRESHOLD = 5000.0;
    private static final double SSS_TIER1_FIXED = 105.0;
    private static final double SSS_TIER2_THRESHOLD = 10000.0;
    private static final double SSS_TIER2_RATE = 0.05;
    private static final double SSS_TIER3_THRESHOLD = 15000.0;
    private static final double SSS_TIER3_RATE = 0.08;
    private static final double SSS_TIER3_FIXED = 75.0;
    private static final double SSS_TIER4_RATE = 0.12;
    private static final double SSS_TIER4_FIXED = 110.0;

    // Pag-IBIG (Home Development Mutual Fund) Constants
    private static final double PAG_IBIG_RATE = 0.03;
    private static final double PAG_IBIG_MINIMUM = 100.0;

    // PhilHealth Constants
    private static final int PHILHEALTH_MINIMUM_HOURS = 10;
    private static final double PHILHEALTH_CONTRIBUTION = 120.0;

    // Tax Constants
    private static final double TAX_TIER1_THRESHOLD = 10000.0;
    private static final double TAX_TIER1_RATE = 0.03;
    private static final double TAX_TIER2_THRESHOLD = 25000.0;
    private static final double TAX_TIER2_RATE = 0.08;
    private static final double TAX_TIER3_THRESHOLD = 40000.0;
    private static final double TAX_TIER3_RATE = 0.11;
    private static final double TAX_TIER4_RATE = 0.135;

    // Dependents and Position Constants
    private static final double DEDUCTION_PER_DEPENDENT = 1000.0;
    private static final double MANAGER_WEEKLY_BONUS = 5000.0;
    private static final String MANAGER_POSITION = "manager";

    //Overtime Checker
    public static int checkOvertime(int hoursWorked){
        int overtimeHours = 0;
        if (hoursWorked > STANDARD_WORK_HOURS_PER_WEEK) {
            overtimeHours = hoursWorked - STANDARD_WORK_HOURS_PER_WEEK;
        }
        return overtimeHours;
    }


    //Weekly Gross Pay
    public static double computeWeeklyGrossPay(double ratePerHour, int hoursWorked, int overtimeHours){
        double regularHours = hoursWorked - overtimeHours;
        double basePay = ratePerHour * regularHours;
        double overtimePay = overtimeHours * (ratePerHour * OVERTIME_MULTIPLIER);

        return basePay + overtimePay;
    }


    // SSS Contribution Calculation
    private static double calculateSSSContribution(double monthlyGrossPay) {
        if (monthlyGrossPay <= SSS_TIER1_THRESHOLD) {
            return SSS_TIER1_FIXED;
        }
        if (monthlyGrossPay <= SSS_TIER2_THRESHOLD) {
            return monthlyGrossPay * SSS_TIER2_RATE;
        }
        if (monthlyGrossPay <= SSS_TIER3_THRESHOLD) {
            return (monthlyGrossPay * SSS_TIER3_RATE) + SSS_TIER3_FIXED;
        }
        return (monthlyGrossPay * SSS_TIER4_RATE) + SSS_TIER4_FIXED;
    }

    // Pag-IBIG Contribution Calculation
    private static double calculatePagIbigContribution(double monthlyGrossPay) {
        if (monthlyGrossPay <= SSS_TIER1_THRESHOLD) {
            return PAG_IBIG_MINIMUM;
        }
        return monthlyGrossPay * PAG_IBIG_RATE;
    }

    // PhilHealth Contribution Calculation
    private static double calculatePhilHealthContribution(int monthlyHours) {
        if (monthlyHours >= PHILHEALTH_MINIMUM_HOURS) {
            return PHILHEALTH_CONTRIBUTION;
        }
        return 0.0;
    }

    // Withholding Tax Calculation
    private static double calculateWithholdingTax(double monthlyGrossPay) {
        if (monthlyGrossPay <= TAX_TIER1_THRESHOLD) {
            return monthlyGrossPay * TAX_TIER1_RATE;
        }
        if (monthlyGrossPay <= TAX_TIER2_THRESHOLD) {
            return monthlyGrossPay * TAX_TIER2_RATE;
        }
        if (monthlyGrossPay <= TAX_TIER3_THRESHOLD) {
            return monthlyGrossPay * TAX_TIER3_RATE;
        }
        return monthlyGrossPay * TAX_TIER4_RATE;
    }

    // Dependents Deduction Calculation
    private static double calculateDependentsDeduction(int numberOfDependents) {
        return numberOfDependents * DEDUCTION_PER_DEPENDENT;
    }

    //Deductions Computation
    public static double computeMonthlyDeductions(double monthlyGrossPay, int monthlyHours, int numberOfDependents) {
        double sssContribution = calculateSSSContribution(monthlyGrossPay);
        double pagIbigContribution = calculatePagIbigContribution(monthlyGrossPay);
        double philHealthContribution = calculatePhilHealthContribution(monthlyHours);
        double withholdingTax = calculateWithholdingTax(monthlyGrossPay);
        double dependentsDeduction = calculateDependentsDeduction(numberOfDependents);

        return sssContribution + pagIbigContribution + philHealthContribution + withholdingTax + dependentsDeduction;
    }


    //Net Pay Computation
    public static double computeMonthlyNetPay(double monthlyGrossPay, String employeePosition, double totalDeductions, int weeksInMonth) {
        double netPay = monthlyGrossPay - totalDeductions;

        if (employeePosition.equalsIgnoreCase(MANAGER_POSITION)) {
            double managerBonus = MANAGER_WEEKLY_BONUS * weeksInMonth;
            netPay += managerBonus;
        }
        return netPay;
    }

    public static boolean tryAgain(Scanner scanner){
        scanner.nextLine();
        while (true) {
            System.out.print("Do you want to try again? Y/N: ");
            String userChoice = scanner.nextLine();
            if (userChoice.equalsIgnoreCase("y")){
                return true;
            } else if (userChoice.equalsIgnoreCase("n")){
                System.out.println("\nPay Roll Computation Ended. Thank you for using!");
                return false;
            } else {
                System.out.println("That is not a valid input.");
            }
        }
    }

    public static void main(String[]args){
        Scanner scanner = new Scanner (System.in);

        do {
            System.out.println("\n====================================");
            System.out.println("EMPLOYEE PAY ROLL COMPUTATION");
            System.out.println("====================================");
            System.out.print("\nFirst Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Middle Name: ");
            String middleName = scanner.nextLine();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Department: ");
            String department = scanner.nextLine();
            System.out.print("Position: ");
            String position = scanner.nextLine();
            System.out.print("Rate per Hour: ₱");
            double ratePerHour = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Dependents: ");
            int numberOfDependents = scanner.nextInt();
            scanner.nextLine();

            int monthlyTotalHours = 0;
            int monthlyTotalOvertimeHours = 0;
            double monthlyGrossPay = 0;
            int[] weeklyHours = new int[WEEKS_IN_MONTH];
            int[] weeklyOvertimeHours = new int[WEEKS_IN_MONTH];
            double[] weeklyGrossPay = new double[WEEKS_IN_MONTH];

            System.out.println("Enter hours worked per week:");
            for(int weekIndex = 0; weekIndex < WEEKS_IN_MONTH; weekIndex++) {
                System.out.print("Week " + (weekIndex + 1) + ": ");
                weeklyHours[weekIndex] = scanner.nextInt();
                weeklyOvertimeHours[weekIndex] = checkOvertime(weeklyHours[weekIndex]);
                weeklyGrossPay[weekIndex] = computeWeeklyGrossPay(ratePerHour, weeklyHours[weekIndex], weeklyOvertimeHours[weekIndex]);

                monthlyGrossPay += weeklyGrossPay[weekIndex];
                monthlyTotalHours += weeklyHours[weekIndex];
                monthlyTotalOvertimeHours += weeklyOvertimeHours[weekIndex];
            }

            double totalDeductions = computeMonthlyDeductions(monthlyGrossPay, monthlyTotalHours, numberOfDependents);
            double netPay = computeMonthlyNetPay(monthlyGrossPay, position, totalDeductions, WEEKS_IN_MONTH);

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
            System.out.printf("%-25s : ₱%,.2f%n", "Rate per Hour", ratePerHour);
            System.out.printf("%-25s : %d%n", "Dependents", numberOfDependents);
            System.out.printf("%-25s : %d%n", "Hours Worked (Month)", monthlyTotalHours);
            System.out.printf("%-25s : %d%n", "Overtime Hours", monthlyTotalOvertimeHours);
            System.out.printf("%-25s : ₱%,.2f%n", "Deductions", totalDeductions);
            System.out.println("-----------------------------------");
            System.out.println("Total Pay");
            System.out.println("-----------------------------------");
            System.out.printf("%-25s : ₱%,.2f%n", "Gross Pay", monthlyGrossPay);
            System.out.printf("%-25s : ₱%,.2f%n", "Net Pay", netPay);
            System.out.println("====================================");

        } while (tryAgain(scanner));
        scanner.close();
    }
}