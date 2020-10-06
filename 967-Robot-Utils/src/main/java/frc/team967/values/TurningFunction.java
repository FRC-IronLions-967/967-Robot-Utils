package frc.team967.values;

public class TurningFunction {
    private double deadband, minPower, aimPower, highCutoff;
    private double table[] = new double[101];
    public TurningFunction(double deadband, double minPower, double aimPower, double highCutoff) {
        this.deadband = deadband;
        this.minPower = minPower;
        this.aimPower = aimPower;
        this.highCutoff = highCutoff;

        int tableIndex = 0;
        while(tableIndex < deadband * 100) {
            table[tableIndex] = 0;
            tableIndex++;
        }
        double aimSlope = (aimPower - minPower)/(highCutoff - deadband);
        while(tableIndex < highCutoff * 100) {
            table[tableIndex] = (aimSlope * (((double) tableIndex / 100) - deadband)) + minPower;
            tableIndex++;
        }
        double driveSlope = (1.0 - aimPower)/(1.0 - highCutoff);
        while(tableIndex < 101) {
            table[tableIndex] = (driveSlope * (((double) tableIndex / 100) - highCutoff)) + aimPower;
            tableIndex++;
        }
    }

    public void setDeadband(double d) {
        this.deadband = d;
    }

    public void setMinPower(double m) {
        this.minPower = m;
    }

    public void setAimPower(double a) {
        this.aimPower = a;
    }

    public void setHighCutoff(double h) {
        this.highCutoff = h;
    }

    public double[] recalcTable() {
        int tableIndex = 0;
        while(tableIndex < deadband * 100) {
            table[tableIndex] = 0;
            tableIndex++;
        }
        double aimSlope = (aimPower - minPower)/(highCutoff - deadband);
        while(tableIndex < highCutoff * 100) {
            table[tableIndex] = (aimSlope * (((double) tableIndex / 100) - deadband)) + minPower;
            tableIndex++;
        }
        double driveSlope = (1.0 - aimPower)/(1.0 - highCutoff);
        while(tableIndex < 100) {
            table[tableIndex] = (driveSlope * (((double) tableIndex / 100) - highCutoff)) + aimPower;
            tableIndex++;
        }

        return table;
    }

    public double[] getTable() {
        return table;
    }

}