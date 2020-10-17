package frc.team967.motors;

import com.revrobotics.CANSparkMax;

import frc.team967.exceptions.MotorTypeMismatchException;

public class SparkMaxBrushless967 extends CANSparkMax implements MotorController {

    public SparkMaxBrushless967(int id) {
        super(id, MotorType.kBrushless);
        super.setInverted(false);
    }

    @Override
    public void setPower(double power) {
        super.set(power);
    }

    @Override
    public void follow(MotorController master) throws MotorTypeMismatchException {
        if(master instanceof SparkMaxBrushless967) {
            super.follow((SparkMaxBrushless967) master);
        } else {
            throw new MotorTypeMismatchException("Incompatible motor type for SparkMAX");
        }
    }

    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);
    }
}