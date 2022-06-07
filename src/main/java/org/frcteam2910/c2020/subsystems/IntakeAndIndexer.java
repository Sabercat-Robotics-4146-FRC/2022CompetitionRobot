package org.frcteam2910.c2020.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.*;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam2910.c2020.Constants;

public class IntakeAndIndexer implements Subsystem {
  public static CANSparkMax indexerBottom;
  public CANSparkMax indexerTop;
  public DigitalInput indexerBottomSensor;
  public DigitalInput indexerTopSensor;

  public TalonSRX intakeMotor;
  public boolean intakeActive;
  public boolean intakePistonExtended;

  public CANSparkMax flywheelLeader;
  public CANSparkMax flywheelFollower;

  public boolean enabled;
  public boolean pressureSwitch;
  public double current;

  public boolean intakeflag;

  public Compressor compressor;

  public Solenoid intakePiston;

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, setPoint;

  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_encoder;

  private boolean flywheelToggle;

  private boolean indexerToggle;

  private Servo servoLeft;
  private Servo servoRight;
  private double kHood;

  public IntakeAndIndexer() {
    kHood = .40;
    indexerBottom = new CANSparkMax(Constants.INDEXER_BOTTOM, MotorType.kBrushless);
    indexerTop = new CANSparkMax(Constants.INDEXER_TOP, MotorType.kBrushless);
    indexerBottomSensor = new DigitalInput(Constants.INDEXER_BOTTOM_SENSOR);
    indexerTopSensor = new DigitalInput(Constants.INDEXER_TOP_SENSOR);
    intakeMotor = new TalonSRX(Constants.INTAKE_MOTOR_PORT);
    intakeActive = false;

    intakePiston = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    intakePistonExtended = false;

    intakePiston.set(intakePistonExtended);

    flywheelLeader = new CANSparkMax(Constants.FLYWHEEL_LEADER, MotorType.kBrushless);
    flywheelLeader.restoreFactoryDefaults();
    flywheelFollower = new CANSparkMax(Constants.FLYWHEEL_FOLLOWER, MotorType.kBrushless);
    flywheelFollower.restoreFactoryDefaults();

    flywheelLeader.setInverted(true);
    flywheelFollower.follow(flywheelLeader, true);

    CANSparkMax[] sparkMaxs = {indexerBottom, indexerTop, flywheelLeader, flywheelFollower};

    for (var sparkMax : sparkMaxs) {
      sparkMax.setSmartCurrentLimit(80); // current limit (amps)
      sparkMax.setOpenLoopRampRate(.5); // # seconds to reach peak throttle
      sparkMax.enableVoltageCompensation(
          12);
    }

    m_pidController = flywheelLeader.getPIDController();

    m_encoder = flywheelLeader.getEncoder();

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

    kP = 45e-5;
    kI = 0;
    kD = 0;
    kIz = 0;
    kFF = .0002;
    kMaxOutput = 1;
    kMinOutput = -1;
    maxRPM = 5874;
    setPoint = 5000;

    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);

    flywheelToggle = false;

    indexerToggle = false;
  }

  public void indexerAlwaysOn() {
    if (indexerTopSensor.get() == true) {
      indexerBottom.setVoltage(4);
      indexerTop.setVoltage(4);
    } else if (indexerTopSensor.get() == false && indexerBottomSensor.get() == true) {
      indexerBottom.setVoltage(4);
      indexerTop.stopMotor();
      ;
    } else {
      indexerBottom.stopMotor();
      indexerTop.stopMotor();
    }
  }

  public void indexerOff() {
    indexerBottom.stopMotor();
    indexerTop.stopMotor();
  }

  public void loadTopBall() {
    indexerBottom.stopMotor();
    indexerTop.setVoltage(6);
  }

  public void toggleIntake() {
    if (intakeActive == false) {
      intakeMotor.set(ControlMode.PercentOutput, .5);
    } else if (intakeActive == true) {
      intakeMotor.set(ControlMode.PercentOutput, 0.0);
    }
    intakeActive = !intakeActive;
  }

  public void extendIntakeSubsystem() {
    intakePistonExtended = !intakePistonExtended;
  }

  public void toggleFlywheel() {
    flywheelToggle = !flywheelToggle;
    if (flywheelToggle == false) {
      flywheelLeader.stopMotor();
    } else {
      m_pidController.setReference(5000, ControlType.kVelocity);
    }
  }

  public void toggleIndexer() {
    indexerToggle = !indexerToggle;
  }

  @Override
  public void periodic() {

    SmartDashboard.putNumber("indexer bus voltage", indexerTop.getBusVoltage());
    SmartDashboard.putNumber("flywheel bus voltage", flywheelLeader.getBusVoltage());

    intakePiston.set(intakePistonExtended);

    if (indexerToggle) {
      indexerAlwaysOn();
    } else {
      indexerOff();
    }

    // indexerAlwaysOn();

    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
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

    SmartDashboard.putNumber("ProcessVariable", m_encoder.getVelocity());
  }
}
