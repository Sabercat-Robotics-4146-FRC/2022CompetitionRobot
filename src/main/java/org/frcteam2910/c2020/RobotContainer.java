package org.frcteam2910.c2020;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import java.io.IOException;
import org.frcteam2910.c2020.commands.*;
import org.frcteam2910.c2020.subsystems.*;
import org.frcteam2910.c2020.util.AutonomousChooser;
import org.frcteam2910.c2020.util.AutonomousTrajectories;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.robot.input.Axis;
import org.frcteam2910.common.robot.input.XboxController;

public class RobotContainer {

  private final XboxController primaryController =
      new XboxController(Constants.PRIMARY_CONTROLLER_PORT);

  private final XboxController secondaryController =
      new XboxController(Constants.secondaryControllerPort);

  private final Superstructure superstructure = new Superstructure();

  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  private final IntakeAndIndexer intakeAndIndexer = new IntakeAndIndexer();
  private final EndLift endLift = new EndLift();
  private final CompressorClass compressorClass = new CompressorClass();
  private final Limelight limelight = new Limelight();

  private AutonomousTrajectories autonomousTrajectories;
  private final AutonomousChooser autonomousChooser;

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
    CommandScheduler.getInstance().registerSubsystem(intakeAndIndexer);
    CommandScheduler.getInstance().registerSubsystem(endLift);
    CommandScheduler.getInstance().registerSubsystem(compressorClass);
    CommandScheduler.getInstance().registerSubsystem(limelight);

    CommandScheduler.getInstance()
        .setDefaultCommand(
            drivetrainSubsystem,
            new DriveCommand(
                drivetrainSubsystem,
                getDriveForwardAxis(),
                getDriveStrafeAxis(),
                getDriveRotationAxis()));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    primaryController
        .getBackButton()
        .whenPressed(() -> drivetrainSubsystem.resetGyroAngle(Rotation2.ZERO));

    // primaryController.getAButton().whenPressed(
    //         new BasicDriveCommand(drivetrainSubsystem, new Vector2(-0.5, 0.0), 0.0,
    // false).withTimeout(0.3)
    // );

    // primaryController
    //     .getRightJoystickButton()
    //     .whileHeld(
    //         new BasicDriveCommand(
    //             drivetrainSubsystem, new Vector2(0.0, 0.0), limelight.adjustHeading(), false));

    secondaryController.getYButton().whenPressed(() -> intakeAndIndexer.loadTopBall());

    secondaryController.getYButton().whenPressed(() -> intakeAndIndexer.toggleFlywheel());

    primaryController.getAButton().whenPressed(() -> intakeAndIndexer.toggleIntake());

    primaryController.getYButton().whenPressed(() -> intakeAndIndexer.extendIntakeSubsystem());

    secondaryController.getRightBumperButton().whileHeld(() -> endLift.reverseSpool());

    // // primaryController.getStartButton().whenPressed(() -> endLift.togglePin());

    secondaryController.getRightBumperButton().whenReleased(() -> endLift.stopLift());

    // // primaryController.getStartButton().whenReleased(() -> endLift.togglePin());

    secondaryController.getLeftBumperButton().whileHeld(() -> endLift.SendSpool());

    // // primaryController.getXButton().whenPressed(() -> endLift.togglePin());

    secondaryController.getLeftBumperButton().whenReleased(() -> endLift.stopLift());

    // primaryController.getXButton().whenReleased(() -> endLift.togglePin());

    secondaryController.getBButton().whenPressed(() -> endLift.togglePin());

    secondaryController.getStartButton().whenPressed(() -> limelight.toggle());

    secondaryController.getAButton().whenPressed(() -> intakeAndIndexer.toggleIndexer());
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

  public IntakeAndIndexer getIntakeAndIndexerSubsystem() {
    return intakeAndIndexer;
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
