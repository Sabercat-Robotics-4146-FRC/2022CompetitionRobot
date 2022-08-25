package org.frcteam2910.c2020;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import java.io.IOException;
import org.frcteam2910.c2020.commands.*;
import org.frcteam2910.c2020.subsystems.*;
import org.frcteam2910.c2020.util.AutonomousChooser;
import org.frcteam2910.c2020.util.AutonomousTrajectories;
import org.frcteam2910.c2020.util.DriverReadout;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.input.Axis;
import org.frcteam2910.common.robot.input.DPadButton.Direction;
import org.frcteam2910.common.robot.input.XboxController;

public class RobotContainer {

  private final XboxController primaryController =
      new XboxController(Constants.PRIMARY_CONTROLLER_PORT);

  private final Superstructure superstructure = new Superstructure();

  private final DrivetrainSubsystem drivetrainSubsystem = new DrivetrainSubsystem();
  private final IntakeAndIndexer intakeAndIndexer = new IntakeAndIndexer();
  private final Flywheel flywheel = new Flywheel();
  private final EndLift endLift = new EndLift();
  private final CompressorClass compressorClass = new CompressorClass();
  private final Limelight limelight = new Limelight();

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
    CommandScheduler.getInstance().registerSubsystem(intakeAndIndexer);
    CommandScheduler.getInstance().registerSubsystem(flywheel);
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

    driverReadout = new DriverReadout(this);

    configureButtonBindings();
  }

  private void configureButtonBindings() {

    primaryController
        .getBackButton()
        .whenPressed(() -> drivetrainSubsystem.resetGyroAngle(Rotation2.ZERO));

    primaryController
        .getDPadButton(Direction.UP)
        .whileHeld(
            new BasicDriveCommand(
                drivetrainSubsystem, new Vector2(0.0, 0.0), limelight.adjustHeading(), false));

    // primaryController.getBButton().whenPressed(() -> intakeAndIndexer.loadTopBall());

    primaryController
        .getBButton()
        .whenPressed(
            () -> {
              // limelight.varyServos();
              // try {
              //   TimeUnit.SECONDS.sleep(0);
              // } catch (InterruptedException e) {
              //   e.printStackTrace();
              // }

              flywheel.toggleFlywheel();
              // if (getBatteryVoltage() <= 10) {
              //   drivetrainSubsystem.reduceCurrentDraw();
              // }
            });

    primaryController
        .getBButton()
        .whenReleased(
            () -> {
              // limelight.varyServos();
              // try {
              //   TimeUnit.SECONDS.sleep(5);
              // } catch (InterruptedException e) {
              //   e.printStackTrace();
              // }

              flywheel.toggleFlywheel();
            });

    primaryController.getAButton().whenPressed(() -> intakeAndIndexer.toggleIntake());

    primaryController
        .getRightJoystickButton()
        .whenPressed(() -> intakeAndIndexer.extendIntakeSubsystem());

    primaryController.getStartButton().whileHeld(() -> endLift.reverseSpool());

    primaryController.getStartButton().whenReleased(() -> endLift.stopLift());

    primaryController.getXButton().whileHeld(() -> endLift.SendSpool());

    primaryController.getXButton().whenReleased(() -> endLift.stopLift());

    primaryController.getLeftJoystickButton().whenPressed(() -> endLift.togglePin());
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

  public IntakeAndIndexer getIntakeAndIndexerSubsystem() {
    return intakeAndIndexer;
  }

  public Flywheel getFlywheelSubsystem() {
    return flywheel;
  }

  public Limelight getLimelightSubsystem() {
    return limelight;
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
