package org.frcteam4146.c2022;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.frcteam4146.c2022.commands.DriveCommand;
import org.frcteam4146.c2022.commands.auto.AutonomousFactory;
import org.frcteam4146.c2022.commands.auto.AutonomousSelector;
import org.frcteam4146.c2022.commands.auto.AutonomousTab;
import org.frcteam4146.c2022.commands.auto.TrajectoryTest;
import org.frcteam4146.c2022.subsystems.*;
import org.frcteam4146.common.robot.input.XboxController;

public class RobotContainer {

  private final XboxController primaryController =
      new XboxController(Constants.PRIMARY_CONTROLLER_PORT);
  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  Command m_autoCommand;
  AutonomousSelector autoSelector;

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

    autoSelector = new AutonomousSelector(this);

    AutonomousTab tab = new AutonomousTab(this);

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // TODO: Configure Button Bindings
    primaryController.getYButton().whenPressed(() -> drivetrainSubsystem.toggleFieldOriented());
    primaryController.getXButton().whenPressed(() -> drivetrainSubsystem.toggleDriveFlag());
  }

  public AutonomousSelector getAutoSelector() {
    return autoSelector;
  }

  public Command getAutoCommand() {
    return autoSelector.getCommand();
    //return new TrajectoryTest(getDrivetrainSubsystem());
  }

  public DrivetrainSubsystem getDrivetrainSubsystem() {
    return drivetrainSubsystem;
  }
}
