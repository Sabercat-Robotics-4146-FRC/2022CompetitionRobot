package org.frcteam2910.c2020.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam2910.c2020.Constants;

public class Indexer implements Subsystem{
    public static CANSparkMax indexerBottom;
    public CANSparkMax indexerTop;
    private boolean indexerToggle;

    private boolean[] indexerStatus;

    public Indexer() {
        indexerBottom = new CANSparkMax(Constants.INDEXER_BOTTOM, MotorType.kBrushless);
        indexerTop = new CANSparkMax(Constants.INDEXER_TOP, MotorType.kBrushless);
        indexerToggle = false;
        indexerStatus = new boolean[2];

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
        indexerStatus[0] = true; indexerStatus[1] = true;
    }

    public void indexerOff() {
        indexerBottom.stopMotor();
        indexerTop.stopMotor();
        indexerStatus[0] = false; indexerStatus[1] = false;
    }

    public void loadTopBall() {
        indexerBottom.stopMotor();
        indexerTop.setVoltage(6);
        indexerStatus[0] = false; indexerStatus[1] = true;
    }

    public void loadBottomBall() {
        indexerTop.stopMotor();
        indexerBottom.setVoltage(6);
        indexerStatus[0] = true; indexerStatus[1] = false;
    }

    public void toggleIndexer() {
        indexerToggle = !indexerToggle;
    }

    @Override
    public void periodic() {
        if (indexerToggle) {
            if (!indexerStatus[0] || !indexerStatus[1]) indexerOn();
        } else {
            if(indexerStatus[0] || indexerStatus[1]) indexerOff();
        }
    }
}

