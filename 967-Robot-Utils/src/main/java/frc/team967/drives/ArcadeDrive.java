package frc.team967.drives;

import frc.team967.motors.*;

public class ArcadeDrive {
    
    private boolean usingLookup = false;
    private MotorController[] rightControllers;
    private MotorController[] leftControllers;

    // creates a new arcade drive with the given motor ids on each side
    // only works if all of the drive uses the same type of motors
    public ArcadeDrive(int[] leftIds, int[] rightIds, MotorTypes type) {
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
            }
        }
    }
}