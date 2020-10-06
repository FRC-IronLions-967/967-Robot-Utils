package frc.team967.vision;

import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightDefault {
    public static double getTX() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getNumber(0).doubleValue();
    }

    public static double getTY() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getNumber(0).doubleValue();
    }

    public static double getTV() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getNumber(0).doubleValue();
    }
}