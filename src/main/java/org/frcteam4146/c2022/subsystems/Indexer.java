package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.IndexerConstants;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Indexer implements Subsystem {
  public CANSparkMax indexerBottom;
  public CANSparkMax indexerTop;

  public DigitalInput indexerBottomSensor;
  public DigitalInput indexerTopSensor;

  public Indexer() {
    indexerBottom = new CANSparkMax(IndexerConstants.INDEXER_BOTTOM, MotorType.kBrushless);
    indexerTop = new CANSparkMax(IndexerConstants.INDEXER_TOP, MotorType.kBrushless);

    indexerBottomSensor = new DigitalInput(IndexerConstants.INDEXER_BOTTOM_SENSOR);
    indexerTopSensor = new DigitalInput(IndexerConstants.INDEXER_TOP_SENSOR);

    CANSparkMax[] sparkMaxs = {indexerBottom, indexerTop};

    for (var sparkMax : sparkMaxs) {
      sparkMax.setSmartCurrentLimit(20); // current limit (amps)
      sparkMax.setOpenLoopRampRate(.5); // # seconds to reach peak throttle
      sparkMax.enableVoltageCompensation(12);
    }
  }

  public void indexerOn() {
    indexerBottom.setVoltage(4);
    indexerTop.setVoltage(4);
  }

  public void indexerOff() {
    indexerBottom.stopMotor();
    indexerTop.stopMotor();
  }

  public void toggleIndexer(boolean state) {
    if (state) indexerOn();
    else indexerOff();
  }

  public void loadTopBall() {
    indexerBottom.stopMotor();
    indexerTop.setVoltage(6);
  }

  public void loadBottomBall() {
    indexerTop.stopMotor();
    indexerBottom.setVoltage(6);
  }

  public boolean getBottomSensor() {
    return indexerBottomSensor.get();
  }

  public boolean getTopSensor() {
    return indexerTopSensor.get();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("TopSensor", getTopSensor());
    SmartDashboard.putBoolean("BottomSensor", getBottomSensor());
  }
}
