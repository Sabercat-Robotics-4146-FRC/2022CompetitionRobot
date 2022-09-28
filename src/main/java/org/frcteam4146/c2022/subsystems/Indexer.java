package org.frcteam4146.c2022.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam4146.c2022.Constants;

public class Indexer implements Subsystem {
  public CANSparkMax indexerBottom;
  public CANSparkMax indexerTop;

  public DigitalInput indexerBottomSensor;
  public DigitalInput indexerTopSensor;

  public Indexer() {
    indexerBottom = new CANSparkMax(Constants.INDEXER_BOTTOM, MotorType.kBrushless);
    indexerTop = new CANSparkMax(Constants.INDEXER_TOP, MotorType.kBrushless);

    indexerBottomSensor = new DigitalInput(Constants.INDEXER_BOTTOM_SENSOR);
    indexerTopSensor = new DigitalInput(Constants.INDEXER_TOP_SENSOR);

    CANSparkMax[] sparkMaxs = {indexerBottom, indexerTop};

    for (var sparkMax : sparkMaxs) {
      sparkMax.setSmartCurrentLimit(80); // current limit (amps)
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

}
