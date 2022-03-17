package org.frcteam2910.c2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam2910.c2020.Constants;

public class EndLift implements Subsystem {

  private final CANSparkMax liftMotorLeader;
  private final CANSparkMax liftMotorFollower;

  private final Solenoid liftPin;

  private boolean pinToggle;

  public EndLift() {
    liftMotorLeader = new CANSparkMax(Constants.endLiftLeft, MotorType.kBrushless);
    liftMotorFollower = new CANSparkMax(Constants.endLiftRight, MotorType.kBrushless);

    liftMotorLeader.setInverted(true);
    liftMotorFollower.follow(liftMotorLeader, false);

    liftPin = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.liftPin);

    pinToggle = false;
  }

  public void reverseSpool() {
    liftMotorLeader.set(.07);
  }

  public void SendSpool() {
    liftMotorLeader.set(-.2);
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
