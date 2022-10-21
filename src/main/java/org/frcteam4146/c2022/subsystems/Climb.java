package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.ClimbConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Climb implements Subsystem {

  public CANSparkMax anchorMotor; // lowest position on winch assembly
  public CANSparkMax extensionMotor;  // middle position on winch assembly
  public CANSparkMax rotationMotor;  // top position on winch assembly

  public boolean extensionState; // TODO make variable live

  public Climb() {

    anchorMotor = new CANSparkMax(ClimbConstants.ANCHOR_MOTOR, MotorType.kBrushless);
    
    extensionMotor = new CANSparkMax(ClimbConstants.EXTENSION_MOTOR, MotorType.kBrushless);

    rotationMotor = new CANSparkMax(ClimbConstants.ROTATION_MOTOR, MotorType.kBrushless);
    rotationMotor.getEncoder().setPositionConversionFactor(42); // automatically converts from encoder ticks to rotations

    extensionState = false;
  }

  public void setAnchorArmHeight(double height) {
    if (height > ClimbConstants.MAX_HEIGHT || height < ClimbConstants.MIN_HEIGHT) {
      return;
    }
    anchorMotor.getPIDController().setReference(height, ControlType.kPosition); // TODO test *separately* to see if need to invert encoder
  }

  public void stopAnchorArm() {
    anchorMotor.set(0); // TODO make sure motor idle mode is set to brake
  }

  public void setArmExtension(double length) {}

  public void setArmRotation(double angle) {
    double setpointTicks =
        ClimbConstants.VERTICAL_DISTANCE_TO_MOTOR
            * Math.tan(angle)
            * 90
            / Math.PI; // TODO calculate vertical distance to motor and test
    rotationMotor.getPIDController().setReference(setpointTicks, ControlType.kPosition);
  }

  public double getAnchorArmHeight() {
    return anchorMotor.getEncoder().getPosition();
  }

  public double getArmHeight() {
    return extensionMotor.getEncoder().getPosition();
  }

  public boolean isExtended() {
    return extensionState;
  }

  @Override
  public void periodic() {
      SmartDashboard.putNumber("Anchor Arm Height", anchorMotor.getEncoder().getPosition());
      SmartDashboard.putNumber("Arm Extension Length", extensionMotor.getEncoder().getPosition());
      SmartDashboard.putNumber("Arm Rotation Length", rotationMotor.getEncoder().getPosition());
  }
}
