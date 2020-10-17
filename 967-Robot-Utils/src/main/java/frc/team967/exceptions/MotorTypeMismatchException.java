package frc.team967.exceptions;

public class MotorTypeMismatchException extends Exception {
    public MotorTypeMismatchException(String errorMessage) {
        super(errorMessage);
    }
}