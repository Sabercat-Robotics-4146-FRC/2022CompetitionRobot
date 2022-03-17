package org.frcteam2910.c2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.frcteam2910.c2020.Constants;

public class EndLift implements Subsystem {

  private final CANSparkMax liftMotorLeader;
  private final CANSparkMax liftMotorFollower;

  public EndLift() {
    liftMotorLeader = new CANSparkMax(Constants.endLiftLeft, MotorType.kBrushless);
    liftMotorFollower = new CANSparkMax(Constants.endLiftRight, MotorType.kBrushless);

    liftMotorLeader.setInverted(true);
    liftMotorFollower.follow(liftMotorLeader, false);
  }

  public void reverseSpool() {
    liftMotorLeader.set(.2);
  }
}
