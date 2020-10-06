package frc.team967.drives;

import frc.team967.motors.*;

public class ArcadeDrive {
    
    private boolean usingLookup = false;
    private MotorController[] rightControllers;
    private MotorController[] leftControllers;

    // creates
    public ArcadeDrive(int[] leftIds, int[] rightIds, MotorTypes type) {
        leftControllers = leftIds;
        rightControllers = rightIds;
    }
}