package org.frcteam4146.c2022.commands.drive;

import edu.wpi.first.wpilibj2.command.*;

import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.c2022.subsystems.Limelight;
import org.frcteam4146.common.math.Vector2;

public class AimRobotCommand extends CommandBase {
  public final DrivetrainSubsystem drivetrainSubsystem;
  public final Limelight limelight;
  public AimRobotCommand(
      DrivetrainSubsystem drivetrainSubsystem, Limelight limelight) {
      this.drivetrainSubsystem = drivetrainSubsystem;
      this.limelight = limelight;
  }
  @Override
  public void execute() {
    double driveAmt;
    if(limelight.getSeesTarget()) {
      driveAmt = limelight.getHorizontalOffset()/27;
      driveAmt = Math.min(driveAmt * Math.abs(Math.sin(Math.PI * driveAmt/2))/10,0.008);
    }
    else {
      driveAmt = 0.05;
    }
    drivetrainSubsystem.drive(Vector2.ZERO,driveAmt);
  }
  @Override
  public boolean isFinished() {
    return limelight.getSeesTarget() && Math.abs(limelight.getHorizontalOffset()) < 2;
  }
  @Override
  public void end(boolean interrupted) {
    if(interrupted) drivetrainSubsystem.drive(Vector2.ZERO,0, false);
    else drivetrainSubsystem.drive(drivetrainSubsystem.getTranslation(),0, drivetrainSubsystem.fieldOriented);
  }

}
