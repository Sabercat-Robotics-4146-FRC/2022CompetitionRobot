package org.frcteam4146.c2022;

import static org.frcteam4146.c2022.Constants.DriveConstants;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.frcteam4146.c2022.commands.drive.AimRobotCommand;
import org.frcteam4146.c2022.commands.commandGroups.ShootBallCommand;
import org.frcteam4146.c2022.commands.drive.DriveCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleIntakeCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleLimelightTrackingCommand;
import org.frcteam4146.c2022.subsystems.*;
import org.frcteam4146.common.drivers.Gyroscope;
import org.frcteam4146.common.robot.input.XboxController;

public class RobotContainer {

  private final Gyroscope gyroscope = new Pigeon(DriveConstants.PIGEON_PORT);
  private final XboxController primaryController =
      new XboxController(DriveConstants.PRIMARY_CONTROLLER_PORT);
  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem(gyroscope);

  private final Flywheel flywheel = new Flywheel();
  private final Indexer indexer = new Indexer();
  private final Servos servos = new Servos();
  private final Limelight limelight = new Limelight(servos);

  public final Intake intake = new Intake();

  public RobotContainer() {

    CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);
    CommandScheduler.getInstance().registerSubsystem(drivetrainSubsystem);
    CommandScheduler.getInstance().registerSubsystem(flywheel);
    CommandScheduler.getInstance().registerSubsystem(indexer);
    CommandScheduler.getInstance().registerSubsystem(servos);
    CommandScheduler.getInstance().registerSubsystem(limelight);

    CommandScheduler.getInstance().registerSubsystem(intake);

    CommandScheduler.getInstance()
        .setDefaultCommand(
            drivetrainSubsystem,
            new DriveCommand(
                drivetrainSubsystem,
                primaryController.getLeftYAxis(),
                primaryController.getLeftXAxis(),
                primaryController.getRightXAxis()));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    // TODO: Configure Button Bindings
    // primaryController.getAButton().whenPressed(() -> drivetrainSubsystem.toggleFieldOriented());
    primaryController.getAButton().toggleWhenPressed(new ToggleIntakeCommand(intake, true));
    primaryController.getXButton().toggleWhenPressed(new ToggleLimelightTrackingCommand(limelight));
    primaryController
        .getBButton()
        .toggleWhenPressed(new ShootBallCommand(limelight, flywheel, indexer));
    primaryController
        .getYButton()
        .toggleWhenPressed(new AimRobotCommand(drivetrainSubsystem, limelight));
    primaryController
        .getLeftBumperButton()
        .whenPressed(() -> drivetrainSubsystem.toggleFieldOriented());
    primaryController.getStartButton().whenPressed(() -> gyroscope.calibrate());
  }

  public DrivetrainSubsystem getDrivetrain() {
    return drivetrainSubsystem;
  }
  public Gyroscope getGyroscope() {
    return gyroscope;
  }
  public Limelight getLimelight() {
    return limelight;
  }
  public Flywheel getFlywheel() {
    return flywheel;
  }
  public Intake getIntake() {
    return intake;
  }
  public Indexer getIndexer() {
    return indexer;
  }
}
