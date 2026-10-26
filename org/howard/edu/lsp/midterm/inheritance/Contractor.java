package org.howard.edu.lsp.midterm.inheritance;

/**
 * A contractor, paid a 15% premium on all hours worked.
 *
 * @Zoey Hall
 */
public class Contractor extends Employee {
    private static final double PREMIUM_MULTIPLIER = 1.15;

    /**
     * Creates a contractor.
     *
     * @param hoursWorked the hours worked in the week
     * @param hourlyRate  the hourly pay rate
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay with a 15% premium on all hours.
     *
     * @return hours worked times hourly rate times 1.15
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate() * PREMIUM_MULTIPLIER;
    }
}
