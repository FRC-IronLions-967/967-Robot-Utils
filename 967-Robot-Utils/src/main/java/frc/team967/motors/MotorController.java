package frc.team967.motors;

import frc.team967.exceptions.MotorTypeMismatchException;

public interface MotorController {

    public void setPower(double power);

    public void follow(MotorController master) throws MotorTypeMismatchException;

    public void setInverted(boolean inverted);
}