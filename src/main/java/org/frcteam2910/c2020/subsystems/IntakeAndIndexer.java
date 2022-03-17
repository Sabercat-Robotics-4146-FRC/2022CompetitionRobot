package org.frcteam2910.c2020.subsystems;

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
  public CANSparkMax intakeMotor;
  public boolean intakeActive;
  public boolean intakePistonExtended;

  public boolean enabled;
  public boolean pressureSwitch;
  public double current;

  public boolean intakeflag;

  public Compressor compressor;

  public Solenoid intakePiston;

  public IntakeAndIndexer() {
    indexerBottom = new CANSparkMax(Constants.indexerBottom, MotorType.kBrushless);
    indexerTop = new CANSparkMax(Constants.indexerTop, MotorType.kBrushless);
    indexerBottomSensor = new DigitalInput(Constants.indexerBottomSensor);
    indexerTopSensor = new DigitalInput(Constants.indexerTopSensor);
    intakeMotor = new CANSparkMax(Constants.intakeMotor, MotorType.kBrushless);
    intakeActive = false;

    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    compressor.enableDigital();

    enabled = compressor.enabled();
    pressureSwitch = compressor.getPressureSwitchValue();
    current = compressor.getCurrent();

    intakePiston = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    intakePistonExtended = false;

    intakePiston.set(intakePistonExtended);
  }

  public void indexerAlwaysOn() {
    if (indexerTopSensor.get() == true) {
      indexerBottom.set(.1);
      indexerTop.set(.1);
    } else if (indexerTopSensor.get() == false && indexerBottomSensor.get() == true) {
      indexerBottom.set(.1);
      indexerTop.set(0.0);
    } else {
      indexerBottom.set(0.0);
      indexerTop.set(0.0);
    }
  }

  public void loadTopBall() {
    indexerBottom.stopMotor();
    indexerTop.set(.1);
  }

  public void toggleIntake() {
    if (intakeActive == false) {
      intakeMotor.set(-.5);
    } else if (intakeActive == true) {
      intakeMotor.stopMotor();
    }
    intakeActive = !intakeActive;
  }

  public void extendIntakeSubsystem() {
    intakePistonExtended = !intakePistonExtended;
  }

  public void setupCompressor() {
    compressor.enableDigital();
    if (!compressor.getPressureSwitchValue()) {
      compressor.disable();
    }
  }

  @Override
  public void periodic() {
    intakePiston.set(intakePistonExtended);
  }
}
