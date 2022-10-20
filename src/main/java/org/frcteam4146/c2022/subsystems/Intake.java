package org.frcteam4146.c2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam4146.c2022.Constants.IntakeConstants;

public class Intake implements Subsystem {

  public Solenoid solenoid;
  public CANSparkMax intake;

  public Intake() {
    // solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.INTAKE_SOLENOID);
    intake = new CANSparkMax(IntakeConstants.INTAKE_MOTOR, MotorType.kBrushless);

    intake.setSmartCurrentLimit(15);
    intake.setOpenLoopRampRate(0.5);
    intake.enableVoltageCompensation(12);
  }

  public void extendIntake(boolean state) {
    // solenoid.set(state);
  }

  public void setIntake(boolean state) {
    if (state) intake.setVoltage(11);
    else intake.setVoltage(0);
  }

  public boolean pickedUpBall() {
    return false;
  }
}
