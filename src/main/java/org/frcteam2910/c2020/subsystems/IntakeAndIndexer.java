package org.frcteam2910.c2020.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
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

  public boolean enabled;
  public boolean pressureSwitch;
  public double current;

  public boolean intakeflag;

  public Compressor compressor;

  public Solenoid intakePiston;

  private boolean indexerToggle;

  public IntakeAndIndexer() {
    indexerBottom = new CANSparkMax(Constants.INDEXER_BOTTOM, MotorType.kBrushless);
    indexerTop = new CANSparkMax(Constants.INDEXER_TOP, MotorType.kBrushless);
    indexerBottomSensor = new DigitalInput(Constants.INDEXER_BOTTOM_SENSOR);
    indexerTopSensor = new DigitalInput(Constants.INDEXER_TOP_SENSOR);
    intakeMotor = new TalonSRX(Constants.INTAKE_MOTOR_PORT);
    intakeActive = false;

    intakePiston = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    intakePistonExtended = false;

    intakePiston.set(intakePistonExtended);

    indexerToggle = false;

    CANSparkMax[] sparkMaxs = {indexerBottom, indexerTop};

    for (var sparkMax : sparkMaxs) {
      sparkMax.setSmartCurrentLimit(80); // current limit (amps)
      sparkMax.setOpenLoopRampRate(.5); // # seconds to reach peak throttle
      sparkMax.enableVoltageCompensation(
          12);
    }
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

  public void toggleIndexer() {
    indexerToggle = !indexerToggle;
  }

  @Override
  public void periodic() {
    intakePiston.set(intakePistonExtended);

    if (indexerToggle) {
      indexerAlwaysOn();
    } else {
      indexerOff();
    }
    // indexerAlwaysOn();
    // if PID coefficients on SmartDashboard have changed, write new values to controller
  }
}
