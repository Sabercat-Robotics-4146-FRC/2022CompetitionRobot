package org.frcteam4146.c2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam4146.c2022.Constants;

public class Intake implements Subsystem {
  // TODO: Complete once Intake has been assembled
  public CANSparkMax intakeMotor;
  public Intake() {
    intakeMotor  = new CANSparkMax(Constants.INTAKE_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);


    intakeMotor.setSmartCurrentLimit(80); // current limit (amps)
    intakeMotor.setOpenLoopRampRate(.5); // # seconds to reach peak throttle
    intakeMotor.enableVoltageCompensation(12);


  }
  public void intakeOn() {
    intakeMotor.setVoltage(6);
  }

  public void intakeOff() {
    intakeMotor.stopMotor();
  }

  public void extendIntake() {}

  public void retractIntake() {}

  public void toggleExtension(boolean state) {
    if (state) extendIntake();
    else extendIntake();
  }

  public void toggleIntake(boolean state) {
    if (state) intakeOn();
    else retractIntake();
  }
}
