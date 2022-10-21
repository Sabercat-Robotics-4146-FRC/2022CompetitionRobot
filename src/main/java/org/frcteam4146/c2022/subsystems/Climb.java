package org.frcteam4146.c2022.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam4146.c2022.Constants.ClimbConstants;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;



public class Climb implements Subsystem {
    public CANSparkMax anchorExtMotor;
    public CANSparkMax armExtMotor;
    public CANSparkMax armRotMotor;

    public Climb() {
        anchorExtMotor = new CANSparkMax(ClimbConstants.ANCHOR_EXT_MOTOR, MotorType.kBrushless);
        armExtMotor = new CANSparkMax(ClimbConstants.ARM_EXT_MOTOR, MotorType.kBrushless);
        armRotMotor = new CANSparkMax(ClimbConstants.ARM_ROT_MOTOR, MotorType.kBrushless);

        CANSparkMax[] motors = {anchorExtMotor,armExtMotor,armRotMotor};
        for (CANSparkMax motor:motors) {
            motor.setSmartCurrentLimit(20);
            motor.setOpenLoopRampRate(0.5);
            motor.enableVoltageCompensation(12);
        }
        armRotMotor.getEncoder().setPositionConversionFactor(42);
    }
    public void setRotation(double speed) {
        anchorExtMotor.set(speed);
    }
    public void setArmExtension(double speed) {
        armExtMotor.set(speed);
    }
    public void setAnchorExtension(double speed) {
        armRotMotor.set(speed);
    }
    public void periodic() {
        SmartDashboard.putNumber("AnchorExt", anchorExtMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("RotExt", armRotMotor.getEncoder().getPosition());
        SmartDashboard.putNumber("ArmExt", armExtMotor.getEncoder().getPosition());
    }




}
