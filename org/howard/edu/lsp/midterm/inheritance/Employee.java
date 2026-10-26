package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class for all employee types. It holds the data common to
 * every employee and declares {@link #calculatePay()}, which each subclass
 * overrides with its own pay rule.
 *
 * @author Your Name
 */
public abstract class Employee {
    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Creates an employee.
     *
     * @param hoursWorked the hours worked in the week (nonnegative)
     * @param hourlyRate  the hourly pay rate (nonnegative)
     */
    protected Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the hours worked.
     *
     * @return the hours worked
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns the hourly rate.
     *
     * @return the hourly rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Calculates this employee's pay according to the rules of the
     * concrete employee type.
     *
     * @return the calculated pay
     */
    public abstract double calculatePay();
}
