package org.frcteam2910.c2020.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.frcteam2910.c2020.Constants;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class IntakeAndIndexer implements Subsystem {
  public CANSparkMax indexerBottom;
  public CANSparkMax indexerTop;
  public DigitalInput indexerBottomSensor;
  public DigitalInput indexerTopSensor;
  public CANSparkMax intakeMotor;
  public boolean intakeActive;
  public boolean intakePistonExtended;

  public Compressor compressor;

  public Solenoid intakePiston;

  public IntakeAndIndexer() {
    CANSparkMax indexerBottom = new CANSparkMax(Constants.indexerBottom, MotorType.kBrushless);
    CANSparkMax indexerTop = new CANSparkMax(Constants.indexerTop, MotorType.kBrushless);
    DigitalInput indexerBottomSensor = new DigitalInput(Constants.indexerBottomSensor);
    DigitalInput indexerTopSensor = new DigitalInput(Constants.indexerTopSensor);
    CANSparkMax intakeMotor = new CANSparkMax(Constants.intakeMotor, MotorType.kBrushless);
    boolean intakeActive = false;

    Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);
      compressor.enableDigital();
      compressor.disable();

      boolean enabled = compressor.enabled();
      boolean pressureSwitch = compressor.getPressureSwitchValue();
      double current = compressor.getCurrent();
  
    Solenoid intakePiston = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    intakePistonExtended = false;
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
      intakeMotor.set(.2);
      intakeActive = true;
    } else if (intakeActive == true) {
      intakeMotor.stopMotor();
      intakeActive = false;
    }
  }

  public void extendIntakeSubsystem() {
    if (intakePistonExtended == false) {
      intakePiston.set(true);
      intakePistonExtended = true;
    } else if (intakePistonExtended == true) {
      intakePiston.set(false);
      intakePistonExtended = false;
    }
  }

  public void setupCompressor() {
    compressor.enableDigital();
    if (!compressor.getPressureSwitchValue()) {
      compressor.disable();
    }
  }
}