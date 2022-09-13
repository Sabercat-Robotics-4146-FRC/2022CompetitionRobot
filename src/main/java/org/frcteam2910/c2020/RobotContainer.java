package org.frcteam2910.c2020;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import java.io.IOException;

import org.frcteam2910.c2020.commands.drive.DriveCommand;
import org.frcteam2910.c2020.subsystems.*;
import org.frcteam2910.c2020.util.AutonomousChooser;
import org.frcteam2910.c2020.util.AutonomousTrajectories;
import org.frcteam2910.c2020.util.DriverReadout;
import org.frcteam2910.common.robot.input.Axis;
import org.frcteam2910.common.robot.input.XboxController;

public class RobotContainer {

  private final XboxController primaryController =
      new XboxController(Constants.PRIMARY_CONTROLLER_PORT);

  private final Superstructure superstructure = new Superstructure();

  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();

  private AutonomousTrajectories autonomousTrajectories;
  private final AutonomousChooser autonomousChooser;

  private final DriverReadout driverReadout;

  public RobotContainer() {
    try {
      autonomousTrajectories =
          new AutonomousTrajectories(DrivetrainSubsystem.TRAJECTORY_CONSTRAINTS);
    } catch (IOException e) {
      e.printStackTrace();
    }
    autonomousChooser = new AutonomousChooser(autonomousTrajectories);

    primaryController.getLeftXAxis().setInverted(true);
    primaryController.getRightXAxis().setInverted(true);

    CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);

    CommandScheduler.getInstance().setDefaultCommand(drivetrainSubsystem,
            new DriveCommand(
                drivetrainSubsystem,
                getDriveForwardAxis(),
                getDriveStrafeAxis(),
                getDriveRotationAxis()));

    driverReadout = new DriverReadout(this);

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    //TODO: Configure Button Bindings


  }

  public double getBatteryVoltage() {
    return RobotController.getBatteryVoltage();
  }

  public Command getAutonomousCommand() {
    return autonomousChooser.getCommand(this);
  }

  private Axis getDriveForwardAxis() {
    return primaryController.getLeftYAxis();
  }

  private Axis getDriveStrafeAxis() {
    return primaryController.getLeftXAxis();
  }

  private Axis getDriveRotationAxis() {
    return primaryController.getRightXAxis();
  }

  public DrivetrainSubsystem getDrivetrainSubsystem() {
    return drivetrainSubsystem;
  }

  public Superstructure getSuperstructure() {
    return superstructure;
  }

  public XboxController getPrimaryController() {
    return primaryController;
  }

  public AutonomousChooser getAutonomousChooser() {
    return autonomousChooser;
  }
}
