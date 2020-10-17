package frc.team967.drives;

import frc.team967.exceptions.*;
import frc.team967.motors.*;

public class TankDrive {
    
    private boolean usingLookup = false;
    private double[] lookupTable;

    private MotorController[] rightControllers;
    private MotorController[] leftControllers;

    // creates a new TankDrive with the given motor ids on each side
    // only works if all of the drive uses the same type of motors
    // the MotorController at index 0 will be treated as the master
    public TankDrive(int[] leftIds, int[] rightIds, MotorTypes type) throws MotorTypeMismatchException {
        leftControllers = new MotorController[leftIds.length];
        rightControllers = new MotorController[rightIds.length];

        // probably a better way to do this
        for(int i = 0; i < leftIds.length; i++) {
            switch(type) {
                case TALONSRX:
                    leftControllers[i] = new TalonSRX967(leftIds[i]);
                    break;

                case VICTORSPX:
                    leftControllers[i] = new VictorSPX967(leftIds[i]);
                    break;

                case SPARKMAXBRUSHLESS:
                    leftControllers[i] = new SparkMaxBrushless967(leftIds[i]);
                    break;

                default:
                    break;
            }
        }

        for(int i = 0; i < rightIds.length; i++) {
            switch(type) {
                case TALONSRX:
                    rightControllers[i] = new TalonSRX967(rightIds[i]);
                    break;

                case VICTORSPX:
                    rightControllers[i] = new VictorSPX967(rightIds[i]);
                    break;

                case SPARKMAXBRUSHLESS:
                    rightControllers[i] = new SparkMaxBrushless967(rightIds[i]);
                    break;

                default:
                    break;
            }
        }

        for(int i = 1; i < leftControllers.length; i++) leftControllers[i].follow(leftControllers[0]);
        for(int i = 1; i < rightControllers.length; i++) rightControllers[i].follow(rightControllers[0]);

    }

    // constructor to create an TankDrive with already initialized MotorController objects
    // use this if your drive train uses a mix of controllers, such as Talons with Victors
    // the MotorController at index 0 will be treated as the master controller
    public TankDrive(MotorController[] leftControllers, MotorController[] rightControllers) throws MotorTypeMismatchException {
        this.leftControllers = leftControllers;
        this.rightControllers = rightControllers;

        for(int i = 1; i < leftControllers.length; i++) leftControllers[i].follow(leftControllers[0]);
        for(int i = 1; i < rightControllers.length; i++) rightControllers[i].follow(rightControllers[0]);
    }

    // enables the lookup table in this instance
    public void enableLookup() {
        this.usingLookup = true;
    }

    //disables the lookup table in this instance
    public void disableLookup() {
        this.usingLookup = false;
    }

    public boolean usingLookup() {
        return usingLookup;
    }

    public void setLookup(double[] lookup) {
        this.lookupTable = lookup;
    }

    public double[] getLookup() {
        return lookupTable;
    }

    public MotorController[] getLeftControllers() {
        return leftControllers;
    }

    public MotorController[] getRightControllers() {
        return rightControllers;
    }

    public void tankDrive(double r, double l) {

        if(usingLookup) {
            r = ((r > 0)) ? lookupTable[(int) Math.floor(Math.abs(r) * 100)] : -lookupTable[(int) Math.floor(Math.abs(r) * 100)];
            l = ((l > 0)) ? lookupTable[(int) Math.floor(Math.abs(l) * 100)] : -lookupTable[(int) Math.floor(Math.abs(l) * 100)];
        }

        rightControllers[0].setPower(r);
        leftControllers[0].setPower(l);
    }
}