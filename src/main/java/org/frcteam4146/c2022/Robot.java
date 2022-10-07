package org.frcteam4146.c2022;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.frcteam4146.common.math.RigidTransform2;
import org.frcteam4146.common.robot.UpdateManager;

public class Robot extends TimedRobot {
  private static Robot instance = null;
  private RobotContainer robotContainer = new RobotContainer();
  private Command autonomousCommand;
  private UpdateManager updateManager = new UpdateManager(robotContainer.getDrivetrainSubsystem());

  public Robot() {
    instance = this;
  }

  public static Robot getInstance() {
    return instance;
  }

  @Override
  public void robotInit() {
    updateManager.startLoop(5.0e-3);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    robotContainer.getDrivetrainSubsystem().resetPose(RigidTransform2.ZERO);

    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }
}
