package frc.team967.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class VictorSPX967 extends VictorSPX implements MotorController {

    public VictorSPX967(int id) {
        super(id);
        super.setInverted(false);
    }

    @Override
    public void setPower(double power) {
        super.set(ControlMode.PercentOutput, power);
    }

    @Override
    public void follow(MotorController master) {
        if(master instanceof TalonSRX967) {
            super.follow((TalonSRX967) master);
        } else if(master instanceof VictorSPX967) {
            super.follow((VictorSPX967) master);
        }
    }

    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);
    }
}