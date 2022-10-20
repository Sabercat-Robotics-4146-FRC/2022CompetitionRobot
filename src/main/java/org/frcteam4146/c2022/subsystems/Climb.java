package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.ClimbConstants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.List;

public class Climb implements Subsystem {

    public CANSparkMax anchorMotor;
    public CANSparkMax middleMotor;
    public CANSparkMax topMotor;

    public boolean extensionState; // TODO make variable live

    public Climb() {

        anchorMotor = new CANSparkMax(ClimbConstants.ANCHOR_MOTOR, MotorType.kBrushless);
        middleMotor = new CANSparkMax(ClimbConstants.MIDDLE_MOTOR, MotorType.kBrushless); // controls extension
        topMotor = new CANSparkMax(ClimbConstants.TOP_MOTOR, MotorType.kBrushless); // controls rotation

        extensionState = false;

    }



    public void extendAnchorArm(double percent) {
        anchorMotor.setVoltage(percent*12);
    }
    
    public void extendMiddleMotor(double percent) {
        middleMotor.setVoltage(percent);
    }

    

    public double getAnchorArmHeight() {
        return anchorMotor.getEncoder().getPosition();
    }

    public double getArmHeight() {
        return middleMotor.getEncoder().getPosition();
    }

    public boolean isExtended() {
        return extensionState;
    }
    
}
