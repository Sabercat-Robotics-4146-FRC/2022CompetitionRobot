package org.frcteam2910.c2020.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam2910.c2020.Constants;

public class Flywheel implements Subsystem {

  public CANSparkMax flywheelLeader;
  public CANSparkMax flywheelfollower;

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, setPoint;

  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_encoder;

  private boolean flywheelToggle;

  private Servo servoLeft;
  private Servo servoRight;
  private double kHood;

  public Flywheel() {
    kHood = .40;

    flywheelLeader = new CANSparkMax(Constants.FLYWHEEL_LEADER, MotorType.kBrushless);
    flywheelLeader.restoreFactoryDefaults();

    flywheelfollower = new CANSparkMax(Constants.FLYWHEEL_FOLLOWER, MotorType.kBrushless);
    flywheelfollower.restoreFactoryDefaults();

    flywheelLeader.setInverted(true);
    flywheelfollower.follow(flywheelLeader, true);

    // CANSparkMax[] motors = {flywheelLeader, flywheelfollower};
    // for (var motor : motors) {
    //   motor.setSmartCurrentLimit(80); // current limit (amps)
    //   motor.setOpenLoopRampRate(.5); // # seconds to reach peak throttle
    //   motor.enableVoltageCompensation(12);
    // }

    m_pidController = flywheelLeader.getPIDController();

    m_encoder = flywheelLeader.getEncoder();
    /*
    servoLeft = new Servo(0);
    servoRight = new Servo(1);

    servoRight.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    servoRight.setSpeed(1.0); // to open
    servoRight.setSpeed(-1.0); // to close
    servoRight.set(kHood);
    servoLeft.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    servoLeft.setSpeed(1.0); // to open
    servoLeft.setSpeed(-1.0); // to close
    servoLeft.set(kHood);
    */

    kP = 0.0004;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = 0.000;
    kMaxOutput = 1;
    kMinOutput = -1;
    setPoint = 4000;

    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    flywheelLeader.burnFlash();
    flywheelfollower.burnFlash();

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
    SmartDashboard.putNumber("Setpoint", setPoint);

    flywheelToggle = false;
  }

  public void toggleFlywheel() {
    flywheelToggle = !flywheelToggle;
  }

  public void toggleFlywheel(boolean state) {
    flywheelToggle = state;
  }

  @Override
  public void periodic() {
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);
    double setPoint = SmartDashboard.getNumber("Setpoint", 0);

    if ((p != kP)) {
      m_pidController.setP(p);
      kP = p;
    }
    if ((i != kI)) {
      m_pidController.setI(i);
      kI = i;
    }
    if ((d != kD)) {
      m_pidController.setD(d);
      kD = d;
    }
    if ((iz != kIz)) {
      m_pidController.setIZone(iz);
      kIz = iz;
    }
    if ((ff != kFF)) {
      m_pidController.setFF(ff);
      kFF = ff;
    }
    if ((max != kMaxOutput) || (min != kMinOutput)) {
      m_pidController.setOutputRange(min, max);
      kMinOutput = min;
      kMaxOutput = max;
    }

    if (flywheelToggle) {
      m_pidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);

    } else {
      flywheelLeader.stopMotor();
    }

    SmartDashboard.putNumber("ProcessVariable", m_encoder.getVelocity());
  }
}
