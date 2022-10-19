package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.ClimbConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Climb implements Subsystem {

  public CANSparkMax anchorMotor;
  public CANSparkMax middleMotor;
  public CANSparkMax topMotor;

  public boolean extensionState; // TODO make variable live

  public Climb() {

    anchorMotor = new CANSparkMax(ClimbConstants.ANCHOR_MOTOR, MotorType.kBrushless);
    middleMotor =
        new CANSparkMax(ClimbConstants.MIDDLE_MOTOR, MotorType.kBrushless); // controls extension
    topMotor = new CANSparkMax(ClimbConstants.TOP_MOTOR, MotorType.kBrushless); // controls rotation

    extensionState = false;
  }

  public void setAnchorArmHeight(double height) {
    if (height > ClimbConstants.MAX_HEIGHT || height < ClimbConstants.MIN_HEIGHT) {
      return;
    }
    anchorMotor.getPIDController().setReference(height, ControlType.kPosition);
  }

  public void setArmRotation(double angle) {
    double setpointTicks =
        ClimbConstants.VERTICAL_DISTANCE_TO_MOTOR
            * Math.tan(angle)
            * 90
            / Math.PI; // TODO calculate vertical distance to motor and test
    topMotor.getPIDController().setReference(setpointTicks, ControlType.kPosition);
  }

  /* public void setExtensionSpeed(double speed) {
      List.of(anchorMotor, middleMotor).forEach(

          if (anchorMotor.getEncoder())

      )
  } */

  public double getAnchorArmHeight() {
    return anchorMotor.getEncoder().getPosition();
  }

  public double getArmHeight() {
    return middleMotor.getEncoder().getPosition();
  }

  public boolean isExtended() {
    return extensionState;
  }
}
