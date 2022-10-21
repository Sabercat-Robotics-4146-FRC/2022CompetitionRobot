package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.ClimbConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.frcteam4146.c2022.Constants.ClimbConstants;
import org.frcteam4146.common.robot.input.Axis;

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

    public void extendAnchorArm(double up, double down) {
        anchorMotor.set((up>0)?-up:down);
    }

    public void extendAnchorArm() {
        anchorMotor.getPIDController().setReference(ClimbConstants.MAX_ANCHOR_HEIGHT, ControlType.kPosition);
    }
    
    public void extendArm(double x) {
        extensionMotor.set(x);    
    }

    public void extendArm() {
        extensionMotor.getPIDController().setReference(ClimbConstants.MAX_ARM_HEIGHT, ControlType.kPosition);
    }

    public void rotateArm(double x) {
        rotationMotor.set(-x);
    }

    public double getAnchorArmHeight() {
        return anchorMotor.getEncoder().getPosition();
    }

    public double getArmHeight() {
        return extensionMotor.getEncoder().getPosition();
    }

    public boolean anchorIsExtended() {
        return anchorMotor.getEncoder().getPosition() == ClimbConstants.MAX_ANCHOR_HEIGHT;
    }

    public boolean armIsExtended() {
        return extensionMotor.getEncoder().getPosition() == ClimbConstants.MAX_ANCHOR_HEIGHT;
    }

  @Override
  public void periodic() {
      SmartDashboard.putNumber("Anchor Arm Height", anchorMotor.getEncoder().getPosition());
      SmartDashboard.putNumber("Arm Extension Length", extensionMotor.getEncoder().getPosition());
      SmartDashboard.putNumber("Arm Rotation Length", rotationMotor.getEncoder().getPosition());
  }
}
