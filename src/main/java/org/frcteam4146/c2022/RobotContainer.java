package org.frcteam4146.c2022;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.frcteam4146.c2022.commands.DriveCommand;
import org.frcteam4146.c2022.commands.auto.TrajectoryTest;
import org.frcteam4146.c2022.subsystems.*;
import org.frcteam4146.common.robot.input.XboxController;

public class RobotContainer {

  private final XboxController primaryController =
      new XboxController(Constants.PRIMARY_CONTROLLER_PORT);
  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  Command m_autoCommand;

  public RobotContainer() {

    // primaryController.getLeftXAxis().setInverted(true);
    // primaryController.getRightXAxis().setInverted(true);

    CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);

    CommandScheduler.getInstance()
        .setDefaultCommand(
            drivetrainSubsystem,
            new DriveCommand(
                drivetrainSubsystem,
                primaryController.getLeftYAxis(),
                primaryController.getLeftXAxis(),
                primaryController.getRightXAxis()));

    m_autoCommand = new TrajectoryTest(drivetrainSubsystem);
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // TODO: Configure Button Bindings
    primaryController.getYButton().whenPressed(() -> drivetrainSubsystem.toggleFieldOriented());
    primaryController.getXButton().whenPressed(() -> drivetrainSubsystem.toggleDriveFlag());
  }

  public Command getAutoCommand() {
    return m_autoCommand;
  }

  public DrivetrainSubsystem getDrivetrainSubsystem() {
    return drivetrainSubsystem;
  }
}
