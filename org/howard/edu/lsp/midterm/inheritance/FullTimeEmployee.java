package org.howard.edu.lsp.midterm.inheritance;

/**
 * A full-time employee. Hours beyond 40 per week are paid at 1.5 times
 * the hourly rate.
 *
 * @author Your Name
 */
public class FullTimeEmployee extends Employee {
    private static final double OVERTIME_THRESHOLD = 40.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    /**
     * Creates a full-time employee.
     *
     * @param hoursWorked the hours worked in the week
     * @param hourlyRate  the hourly pay rate
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay with overtime at 1.5x for hours beyond 40.
     *
     * @return the total pay
     */
    @Override
    public double calculatePay() {
        double hours = getHoursWorked();
        double rate = getHourlyRate();
        double regularHours = Math.min(hours, OVERTIME_THRESHOLD);
        double overtimeHours = Math.max(0.0, hours - OVERTIME_THRESHOLD);
        return regularHours * rate + overtimeHours * rate * OVERTIME_MULTIPLIER;
    }
}
