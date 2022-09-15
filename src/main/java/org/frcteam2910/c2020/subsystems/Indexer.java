package org.frcteam2910.c2020.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam2910.c2020.Constants;

public class Indexer implements Subsystem{
    public static CANSparkMax indexerBottom;
    public CANSparkMax indexerTop;

    public Indexer() {
        indexerBottom = new CANSparkMax(Constants.INDEXER_BOTTOM, MotorType.kBrushless);
        indexerTop = new CANSparkMax(Constants.INDEXER_TOP, MotorType.kBrushless);

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

    public void loadTopBall() {
        indexerBottom.stopMotor();
        indexerTop.setVoltage(6);
    }

    public void loadBottomBall() {
        indexerTop.stopMotor();
        indexerBottom.setVoltage(6);
    }

    public void toggleIndexer(boolean state) {
        if(state) indexerOn();
        else indexerOff();
    }
}

