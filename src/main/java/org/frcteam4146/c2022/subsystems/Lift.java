package org.frcteam4146.c2022.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.frcteam4146.c2022.Constants;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Lift implements Subsystem {

    private final CANSparkMax liftMotorLeader;
    private final CANSparkMax liftMotorFollower;

    private final Solenoid liftPin;

    private boolean pinToggle;

    public Lift() {
        liftMotorLeader = new CANSparkMax(Constants.END_LIFT_LEFT, MotorType.kBrushless);
        liftMotorFollower = new CANSparkMax(Constants.END_LIFT_RIGHT, MotorType.kBrushless);

        liftMotorLeader.setInverted(true);
        liftMotorFollower.follow(liftMotorLeader, true);

        liftPin = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.LIFT_PIN);

        pinToggle = false;
    }

    public void reverseSpool() {
        liftMotorLeader.setVoltage(-1.5);
    }

    public void SendSpool() {
        liftMotorLeader.setVoltage(12);
    }

    public void stopLift() {
        liftMotorLeader.stopMotor();
    }

    public void togglePin() {
        pinToggle = !pinToggle;
        liftPin.set(pinToggle);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("pin toggle", pinToggle);
    }
}