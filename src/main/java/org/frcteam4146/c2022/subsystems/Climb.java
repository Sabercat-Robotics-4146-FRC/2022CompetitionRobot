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
        extensionMotor = new CANSparkMax(ClimbConstants.ANCHOR_MOTOR, MotorType.kBrushless);
        extensionMotor = new CANSparkMax(ClimbConstants.EXTENSION_MOTOR, MotorType.kBrushless);
    
        rotationMotor = new CANSparkMax(ClimbConstants.ROTATION_MOTOR, MotorType.kBrushless);
        rotationMotor.getEncoder().setPositionConversionFactor(42); // automatically converts from encoder ticks to rotations
    
        extensionState = false;
    }

    public void extendAnchorArm(double percent) {
        anchorMotor.setVoltage(percent*12);
    }
    
    public void extendMiddleMotor(double percent) {
        extensionMotor.setVoltage(percent);
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
