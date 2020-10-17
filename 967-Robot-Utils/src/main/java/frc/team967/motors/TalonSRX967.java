package frc.team967.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.team967.exceptions.MotorTypeMismatchException;

public class TalonSRX967 extends TalonSRX implements MotorController {

    public TalonSRX967(int id) {
        super(id);
        super.setInverted(false);
    }

    @Override
    public void setPower(double power) {
        super.set(ControlMode.PercentOutput, power);
    }

    @Override
    public void follow(MotorController master) throws MotorTypeMismatchException {
        if(master instanceof TalonSRX967) {
            super.follow((TalonSRX967) master);
        } else if(master instanceof VictorSPX967) {
            super.follow((VictorSPX967) master);
        } else {
            throw new MotorTypeMismatchException("Incompatible motor type for Talon SRX");
        }
    }

    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);
    }
}