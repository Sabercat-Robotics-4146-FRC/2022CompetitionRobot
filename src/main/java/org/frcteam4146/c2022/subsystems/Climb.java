package org.frcteam4146.c2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.*;
import org.frcteam4146.c2022.Constants.ClimbConstants;

public class Climb implements Subsystem {
  public CANSparkMax anchorExtMotor;
  public CANSparkMax armExtMotor;
  public CANSparkMax armRotMotor;

  public Solenoid brakeSolenoid;

  Map<String, Double> offsets = new HashMap<String, Double>();

  public Climb() {
    offsets.put("Anchor_EXT", 0.0);
    offsets.put("Arm_EXT", 0.0);
    offsets.put("Arm_ROT", 0.0);

    anchorExtMotor = new CANSparkMax(ClimbConstants.ANCHOR_EXT_MOTOR, MotorType.kBrushless);
    armExtMotor = new CANSparkMax(ClimbConstants.ARM_EXT_MOTOR, MotorType.kBrushless);
    armRotMotor = new CANSparkMax(ClimbConstants.ARM_ROT_MOTOR, MotorType.kBrushless);

    brakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ClimbConstants.BRAKE_SOLENOID);

    CANSparkMax[] motors = {anchorExtMotor, armExtMotor, armRotMotor};
    for (CANSparkMax motor : motors) {
      motor.setSmartCurrentLimit(30);
      motor.setOpenLoopRampRate(0.5);
      motor.enableVoltageCompensation(12);
    }
    armRotMotor.getEncoder().setPositionConversionFactor(42);
    display();
  }

  public Climb(boolean zeroStart) {
    this();
    if (zeroStart) {
      zeroAnchor();
      zeroArm();
      zeroRotation();
    }
  }

  public void setClimbBrakes(boolean toggle) {
    brakeSolenoid.set(toggle);
  }

  public void setAnchorExtension(double speed) {
    anchorExtMotor.set(speed);
  }

  public void setArmExtension(double speed) {
    armExtMotor.set(speed);
  }

  public void setRotation(double speed) {
    armRotMotor.set(speed);
  }

  public double getAnchorExtension() {
    return anchorExtMotor.getEncoder().getPosition() - offsets.get("Anchor_EXT");
  }

  public double getArmExtension() {
    return armExtMotor.getEncoder().getPosition() - offsets.get("Arm_EXT");
  }

  public double getRotation() {
    return armRotMotor.getEncoder().getPosition() - offsets.get("Arm_ROT");
  }

  public void zeroAnchor() {
    offsets.replace("Anchor_EXT", anchorExtMotor.getEncoder().getPosition());
  }

  public void zeroArm() {
    offsets.replace("Arm_EXT", armExtMotor.getEncoder().getPosition());
  }

  public void zeroRotation() {
    offsets.replace("Arm_ROT", armRotMotor.getEncoder().getPosition());
  }

  public void display() {
    SmartDashboard.putNumber("AnchorExt", getAnchorExtension());
    SmartDashboard.putNumber("ArmExt", getArmExtension());
    SmartDashboard.putNumber("RotExt", getRotation());
  }

  public void periodic() {
    display();
    SmartDashboard.putData("Zero Anchor", new InstantCommand(() -> zeroAnchor()));
    SmartDashboard.putData("Zero Arm", new InstantCommand(() -> zeroArm()));
    SmartDashboard.putData("Zero Rotation", new InstantCommand(() -> zeroRotation()));
  }
}
