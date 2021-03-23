package frc.team967.drives;

import frc.team967.exceptions.MotorTypeMismatchException;
import frc.team967.motors.*;
import frc.team967.utils.Utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArcadeDrive {
    
    private boolean usingLookup = false;
    private double[] lookupTable;

    private MotorController[] rightControllers;
    private MotorController[] leftControllers;

    private double v = 0.0;

    // rate of turning when the forward velocity is zero
    // used with the differential arcade drive method
    private double zeroTurn = 0.5;

    // rate of turning relative to the forward velocity of the robot
    // uses a simple linear relationship where the turning axis is 
    // multiplied by this scale and the throttle axis
    private double scale = 0.5;

    // maximum acceleration that the code will allow in one loop of the code
    // at the 50 Hz default frequency for the periodic() methods in the
    // subsystems, this equates to 1 second to go from rest to top speed
    // assuming the drive setup is mechanically capable of this feat
    private double maxAccel = 0.02;

    // deadband value in drive functions
    private double deadband = 0.1;

    // creates a new arcade drive with the given motor ids on each side
    // only works if all of the drive uses the same type of motors
    // the MotorController at index 0 will be treated as the master
    public ArcadeDrive(int[] leftIds, int[] rightIds, MotorTypes type) throws MotorTypeMismatchException {
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

    // constructor to create an ArcadeDrive with already initialized MotorController objects
    // use this if your drive train uses a mix of controllers, such as Talons with Victors
    // the MotorController at index 0 will be treated as the master controller
    public ArcadeDrive(MotorController[] leftControllers, MotorController[] rightControllers) throws MotorTypeMismatchException {
        this.leftControllers = leftControllers;
        this.rightControllers = rightControllers;

        for(int i = 1; i < leftControllers.length; i++) leftControllers[i].follow(leftControllers[0]);
        for(int i = 1; i < rightControllers.length; i++) rightControllers[i].follow(rightControllers[0]);
    }

    // change the scale factor for turning in the differential method
    public void setScale(double scale) {
        this.scale = scale;
    }

    // get the current turning scale factor
    public double getScale() {
        return scale;
    }

    // change the turning rate when not moving
    public void setZeroTurn(double zeroTurn) {
        this.zeroTurn = zeroTurn;
    }

    // get the current turning rate when not moving
    public double getZeroTurn() {
        return zeroTurn;
    }

    // change the maximum acceleration allowed in a single loop
    public void setMaxAccel(double maxAccel) {
        this.maxAccel = maxAccel;
    }

    // get the current maximum acceleration value
    public double getMaxAccel() {
        return maxAccel;
    }

    // change the deadband value
    public void setDeadband(double deadband) {
        this.deadband = deadband;
    }

    // get the current deadband value
    public double getDeadband() {
        return deadband;
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

    // standard arcade drive, is the horizontal (steering) axis, and y is the vertical (drive) axis
    // bear in mind that most controllers, such as XBox controllers are inverted on the vertical axis
    // of the analog sticks, so you may have to adjust for that
    public void arcadeDrive(double x, double y, boolean usingDeadband) {
        if(usingDeadband) {
            x = Utils.deadband(x, deadband);
            y = Utils.deadband(y, deadband);
        }

        double r, l;

        if(usingLookup) {
            r = ((y > 0) ? lookupTable[(int) Math.floor(Math.abs(y) * 100)] : -lookupTable[(int) Math.floor(Math.abs(y) * 100)]) - ((x > 0) ? lookupTable[(int) Math.floor(Math.abs(x) * 100)] : -lookupTable[(int) Math.floor(Math.abs(x) * 100)]);
            l = ((y > 0) ? lookupTable[(int) Math.floor(Math.abs(y) * 100)] : -lookupTable[(int) Math.floor(Math.abs(y) * 100)]) + ((x > 0) ? lookupTable[(int) Math.floor(Math.abs(x) * 100)] : -lookupTable[(int) Math.floor(Math.abs(x) * 100)]);
        } else {
            r = y - x;
            l = y + x;
        }

        v = r + x;

        rightControllers[0].setPower(r);
        leftControllers[0].setPower(l);
    }

    // puts a max limit on how fast the robot can accelerate, both positively and negatively
    // currently written so that the robot can accelerate from rest to full speed in 1 second
    // the turning speed also varies with the speed of the robot, turning slower at lower speeds
    // for better control when lining up
    // there is also a special case when turning below 0.1 power, where the robot will turn at 0.25
    // its maximum speed
    public void arcadeDriveDifferential(double x, double y, boolean usingDeadband) {
        if(usingDeadband) {
            x = Utils.deadband(x, deadband);
            y = Utils.deadband(y, deadband);
        }
    
        // difference between current velocity and commanded velocity in the y direction
        double difV = y - v;
        double maxDifV = SmartDashboard.getNumber("maxAccel", 0.02d);
    
        if(difV > 0) {
          v += (difV > maxDifV) ? maxDifV : difV;
        } else {
          v -= (Math.abs(difV) > maxDifV) ? maxDifV : Math.abs(difV);
        }
    
        double s = (Math.abs(v) < 0.1) ? scale * x * zeroTurn : scale * x * v;
    
        double l = v - s;
        double r = v + s;
    
    
        rightControllers[0].setPower(r);
        leftControllers[0].setPower(l);
    }

}