package frc.team967.controls;

import java.util.HashMap;
import java.util.Iterator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;

/*
* @author nastark123
* @version 1.0
* @since 2020-10-22
*/
public class XBoxController extends Joystick {
    public static final int RIGHT_X_AXIS = 0;
    public static final int RIGHT_Y_AXIS = 1;
    public static final int RIGHT_TRIGGER = 2;
    public static final int LEFT_TRIGGER = 3;
    public static final int LEFT_X_AXIS = 4;
    public static final int LEFT_Y_AXIS = 5;

    //this controls how far you have to press the trigger for it to register as pressed
    private double triggerTolerance = 0.25;

    private HashMap<String, JoystickButton> buttonMap;
    private HashMap<String, POVButton> povMap;

    /*
    * Creates a new XBoxController with the specified id.  The id's are usually assigned in the same order that the
    * controllers were plugged in.
    * @param id The id of the controller being created.
    * @return A new XBoxController instance with the specified id.
    */
    public XBoxController(int id) {
        super(id);
        buttonMap = new HashMap<>();
        povMap = new HashMap<>();

        //initialize the buttons on the controller
        buttonMap.put("X", new JoystickButton(this, 1));
        buttonMap.put("Y", new JoystickButton(this, 2));
        buttonMap.put("A", new JoystickButton(this, 3));
        buttonMap.put("B", new JoystickButton(this, 4));
        buttonMap.put("LBUMP", new JoystickButton(this, 5));
        buttonMap.put("RBUMP", new JoystickButton(this, 6));
        buttonMap.put("SELECT", new JoystickButton(this, 7));
        buttonMap.put("START", new JoystickButton(this, 8));

        // this (hopefully) stops the issue where the program crashes if a button with no assigned command is pressed
        Iterator buttonIterator = buttonMap.entrySet().iterator();
        while(buttonIterator.hasNext()) {
            JoystickButton button = (JoystickButton) buttonIterator.next();
            button.whenPressed(new DoNothingCommand());
            button.whenReleased(new DoNothingCommand());
        }

        //initialize the POV buttons (the buttons on the dpad of the controller)
        //these constructor calls could be modified to take a 3rd argument that is their "POV value", which returns from another wpilib function, which I'm not using
        povMap.put("N", new POVButton(this, 0));
        povMap.put("NE", new POVButton(this, 45));
        povMap.put("E", new POVButton(this, 90));
        povMap.put("SE", new POVButton(this, 135));
        povMap.put("S", new POVButton(this, 180));
        povMap.put("SW", new POVButton(this, 225));
        povMap.put("W", new POVButton(this, 270));
        povMap.put("NW", new POVButton(this, 315));

        Iterator povIterator = povMap.entrySet().iterator();
        while(povIterator.hasNext()) {
            POVButton button = (POVButton) povIterator.next();
            button.whenPressed(new DoNothingCommand());
            button.whenPressed(new DoNothingCommand());
        }
    }

    /*
    * Assigns a command to the button specified to be executed when the button is pressed.
    * @param button The string representation of the button, e.g. "A", "B", "X", "Y", etc.
    * @param command The command being assigned, see the wpilib docs for more details on commands.
    * @return Nothing
    */
    public void whenButtonPressed(String button, Command command) {
        buttonMap.get(button).whenPressed(command);
    }

    /*
    * Assigns a command to the button specified to be executed when the button is released.
    * @param button The string representation of the button, e.g. "A", "B", "X", "Y", etc.
    * @param command The command being assigned, see the wpilib docs for more details on commands.
    * @return Nothing
    */
    public void whenButtonReleased(String button, Command command) {
        buttonMap.get(button).whenReleased(command);
    }

    /*
    * Assigns a command to the POV button (the buttons on the DPad of the controller) specified to be executed when the button is pressed.
    * @param button The string representation of the POV button, e.g. "N", "S", "E", "W", "NE", etc.
    * @param command The command being assigned, see the wpilib docs for more details on commands.
    * @return Nothing
    */
    public void whenPOVButtonPressed(String button, Command command) {
        povMap.get(button).whenPressed(command);
    }

    /*
    * Assigns a command to the POV button (the buttons on the DPad of the controller) specified to be executed when the button is released.
    * @param button The string representation of the POV button, e.g. "N", "S", "E", "W", "NE", etc.
    * @param command The command being assigned, see the wpilib docs for more details on commands.
    * @return Nothing.
    */
    public void whenPOVButtonReleased(String button, Command command) {
        povMap.get(button).whenReleased(command);
    }

    /*
    * Checks whether the specified button is pressed
    * @param button The string representation of the button, e.g. "A", "B", "X", "Y", etc.
    * @return boolean This returns true if the button is pressed, false otherwise.
    */
    public boolean isButtonPressed(String button) {
        return buttonMap.get(button).get();
    }

    /*
    * Checks whether the specified POV button is pressed
    * @param button The string representation of the POV button, e.g. "N", "S", "E", "W", "NE", etc.
    * @return boolean This returns true if the button is pressed, false otherwise.
    */
    public boolean isPOVButtonPressed(String povButton) {
        return povMap.get(povButton).get();
    }

    //these get the value between -1.0 and 1.0 that represent the various joysticks
    //please note that the triggers only range between 0.0 and 1.0

    /*
    * Gets the value of the x-axis of the stick on the right of the controller.
    * @return double The value of the x-axis of the right stick, which is between -1.0 and 1.0.
    */
    public double getRightStickX() {
        return this.getRawAxis(RIGHT_X_AXIS);
    }

    /*
    * Gets the value of the y-axis of the stick on the right of the controller.
    * @return double The value of the y-axis of the right stick, which is between -1.0 and 1.0.
    */
    public double getRightStickY() {
        return this.getRawAxis(RIGHT_Y_AXIS);
    }

    /*
    * Gets the value of the trigger on the right of the controller.
    * @return double The value of the trigger, which is between 0.0 and 1.0.
    */
    public double getRightTrigger() {
        return this.getRawAxis(RIGHT_TRIGGER);
    }

    /*
    * Gets the value of the trigger on the left of the controller.
    * @return double The value of the trigger, which is between 0.0 and 1.0.
    */
    public double getLeftTrigger() {
        return this.getRawAxis(LEFT_TRIGGER);
    }

    /*
    * Gets the value of the x-axis of the stick on the left of the controller.
    * @return double The value of the x-axis of the left stick, which is between -1.0 and 1.0.
    */
    public double getLeftStickX() {
        return this.getRawAxis(LEFT_X_AXIS);
    }

    /*
    * Gets the value of the y-axis of the stick on the left of the controller.
    * @return double The value of the y-axis of the left stick, which is between -1.0 and 1.0.
    */
    public double getLeftStickY() {
        return this.getRawAxis(LEFT_Y_AXIS);
    }

    /*
    * Sets the value at which a trigger will return true from isTriggerPressed.
    * @param newTolerance The new value for the trigger tolerance.
    * @return Nothing.
    * @see isTriggerPressed()
    */
    public void setTriggerTolerance(double newTolerance) {
        newTolerance = (newTolerance < 0.0) ? 0.0 : (newTolerance > 1.0) ? 1.0 : newTolerance;
        triggerTolerance = newTolerance;
    }

    /*
    * Gets the current trigger tolerance
    * @return double The trigger tolerance.
    */
    public double getTriggerTolerance() {
        return triggerTolerance;
    }

    /*
    * Checks to see whether the trigger is pressed past the level of triggerTolerance.
    * @param trigger The axis number assigned to the trigger, can be LEFT_TRIGGER or RIGHT_TRIGGER, or int itself.
    * @return boolean true if the trigger is pressed past the tolerance, false otherwise.
    */
    public boolean isTriggerPressed(int trigger) {
        //the triggers on the controller show up as axes, but a common function is to check whether they are pressed or not
        //triggerTolerance can be set with setTriggerTolerance, and if the trigger is pressed that point, this returns true, otherwise false
        if(trigger != 2 && trigger != 3) return false;
        return (this.getRawAxis(trigger) > triggerTolerance) ? true : false;
    }

}