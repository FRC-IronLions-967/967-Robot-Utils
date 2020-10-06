package frc.team967.motors;

public interface MotorController {

    public void setPower(double power);

    public void follow(MotorController master);

    public void setInverted(boolean inverted);
}