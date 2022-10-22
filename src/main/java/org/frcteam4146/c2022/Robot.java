package org.frcteam4146.c2022;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.frcteam4146.common.math.RigidTransform2;
import org.frcteam4146.common.robot.UpdateManager;

public class Robot extends TimedRobot {
  private static Robot instance = null;
  private RobotContainer robotContainer = new RobotContainer();
  private Command autonomousCommand; // = robotContainer.getAutonomousCommand();
  private UpdateManager updateManager = new UpdateManager(robotContainer.getDrivetrain());
  public Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  public PowerDistribution pdh = new PowerDistribution(1, PowerDistribution.ModuleType.kRev);

  public Robot() {
    instance = this;
  }

  public static Robot getInstance() {
    return instance;
  }

  @Override
  public void robotInit() {
    updateManager.startLoop(5.0e-3);
    compressor.enableDigital();
    pdh.setSwitchableChannel(true);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    robotContainer.getDrivetrain().resetPose(RigidTransform2.ZERO);
    autonomousCommand = robotContainer.getAutonomousCommand();
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
