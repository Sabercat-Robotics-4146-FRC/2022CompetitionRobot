package org.frcteam4146.c2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam4146.c2022.Constants.ClimbConstants;

public class Climb implements Subsystem {
  public CANSparkMax anchorExtMotor;
  public CANSparkMax armExtMotor;
  public CANSparkMax armRotMotor;

  public double[] offset;

  public Solenoid rotBreaks;

  public Climb() {
    anchorExtMotor = new CANSparkMax(ClimbConstants.ANCHOR_EXT_MOTOR, MotorType.kBrushless);
    armExtMotor = new CANSparkMax(ClimbConstants.ARM_EXT_MOTOR, MotorType.kBrushless);
    armRotMotor = new CANSparkMax(ClimbConstants.ARM_ROT_MOTOR, MotorType.kBrushless);

    rotBreaks = new Solenoid(PneumaticsModuleType.CTREPCM, 0);

    CANSparkMax[] motors = {anchorExtMotor, armExtMotor, armRotMotor};
    for (CANSparkMax motor : motors) {
      motor.setSmartCurrentLimit(30);
      // motor.setOpenLoopRampRate(0.5);
      motor.enableVoltageCompensation(12);
      motor.getEncoder().setPositionConversionFactor(42);
    }

    offset = new double[3];

    SmartDashboard.putNumber("Anchor Arm Offset", getAnchorExtension());
    SmartDashboard.putNumber("Arm Extension Offset", getArmExtension());
    SmartDashboard.putNumber("Arm Rotation Offset", getRotation());

    SmartDashboard.putNumber("Anchor Arm", 1);
    SmartDashboard.putNumber("Arm Extension", 1);
    SmartDashboard.putNumber("Arm Rot", 1);
  }

  public void setRotation(double speed) {
    armRotMotor.set(speed);
  }

  public void setArmExtension(double speed) {
    armExtMotor.set(speed);
  }

  public void setAnchorExtension(double speed) {
    anchorExtMotor.set(speed);
  }

  public double getAnchorExtension() {
    return anchorExtMotor.getEncoder().getPosition() + offset[0];
  }

  public double getArmExtension() {
    return armExtMotor.getEncoder().getPosition() + offset[1];
  }

  public double getRotation() {
    return armRotMotor.getEncoder().getPosition() + offset[2];
  }

  @Override
  public void periodic() {

    if (SmartDashboard.getNumber("Anchor Arm", 1) == 0) {
      offset[0] = -anchorExtMotor.getEncoder().getPosition();
      SmartDashboard.putNumber("Anchor Arm", 1);
    }
    if (SmartDashboard.getNumber("Arm Extension", 1) == 0) {
      offset[1] = -armExtMotor.getEncoder().getPosition();
      SmartDashboard.putNumber("Arm Extension", 1);
    }
    if (SmartDashboard.getNumber("Arm Rot", 1) == 0) {
      offset[2] = -armExtMotor.getEncoder().getPosition();
      SmartDashboard.putNumber("Arm Rot", 1);
    }

    SmartDashboard.putNumber("Anchor Arm Offset", getAnchorExtension());
    SmartDashboard.putNumber("Arm Extension Offset", getArmExtension());
    SmartDashboard.putNumber("Arm Rotation Offset", getRotation());
  }

  public void setBreaks(boolean toggle) {
    rotBreaks.set(toggle);
  }
}
