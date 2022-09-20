package org.frcteam4146.c2022.subsystems;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Intake implements Subsystem {
    //TODO: Complete once Intake has been assembled
    public Intake() {

    }
    public void intakeOn() {

    }
    public void intakeOff() {

    }
    public void extendIntake() {

    }
    public void retractIntake() {

    }
    public void toggleExtension(boolean state) {
        if(state) intakeOn();
        else intakeOff();
    }
    public void toggleIntake(boolean state) {
        if(state) extendIntake();
        else retractIntake();

    }
}
