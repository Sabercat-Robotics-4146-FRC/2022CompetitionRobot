package org.frcteam2910.c2020.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.frcteam2910.c2020.RobotContainer;
import org.frcteam2910.c2020.commands.FollowTrajectoryCommand;
import org.frcteam2910.c2020.commands.ShootBallCommand;
import org.frcteam2910.common.control.Trajectory;
import org.frcteam2910.common.math.RigidTransform2;
import org.frcteam2910.common.math.Rotation2;

public class AutonomousChooser {
  private final AutonomousTrajectories trajectories;

  private SendableChooser<AutonomousMode> autonomousModeChooser = new SendableChooser<>();

  public AutonomousChooser(AutonomousTrajectories trajectories) {
    this.trajectories = trajectories;

    autonomousModeChooser.setDefaultOption("6 Ball Auto", AutonomousMode.EIGHT_BALL);
    autonomousModeChooser.addOption("6 Ball Compatible", AutonomousMode.EIGHT_BALL_COMPATIBLE);
    autonomousModeChooser.addOption("Simple Shoot Three", AutonomousMode.SIMPLE_SHOOT_THREE);
    autonomousModeChooser.addOption("Straight Line", AutonomousMode.STRAIGHT_LINE);
    autonomousModeChooser.addOption("Sraight Back and Shoot", AutonomousMode.STRAIGHT_BACK_SHOOT);
  }

  public SendableChooser<AutonomousMode> getAutonomousModeChooser() {
    return autonomousModeChooser;
  }

  private SequentialCommandGroup get10BallAutoCommand(RobotContainer container) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    resetRobotPose(command, container, trajectories.getTenBallAutoPartOne());
    followAndIntake(command, container, trajectories.getTenBallAutoPartOne());
    shootAtTarget(command, container);
    // command.addCommands(new FollowTrajectoryCommand(drivetrainSubsystem,
    // trajectories.getTenBallAutoPartTwo()));
    // command.addCommands(new TargetWithShooterCommand(shooterSubsystem, visionSubsystem,
    // xboxController));

    return command;
  }

  private Command get8BallAutoCommand(RobotContainer container) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    // reset robot pose
    resetRobotPose(command, container, trajectories.getEightBallAutoPartOne());
    // follow first trajectory and shoot
    follow(command, container, trajectories.getEightBallAutoPartOne());
    // shoot
    // follow second trajectory and shoot
    followAndIntake(command, container, trajectories.getEightBallAutoPartTwo());

    follow(command, container, trajectories.getEightBallAutoPartThree());
    // shoot
    follow(command, container, trajectories.getEightBallAutoPartFour());

    return command;
  }

  private Command get8BallCompatibleCommand(RobotContainer container) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    // reset robot pose
    resetRobotPose(command, container, trajectories.getEightBallCompatiblePartOne());
    // follow first trajectory and shoot
    follow(command, container, trajectories.getEightBallCompatiblePartOne());
    // follow second trajectory and shoot
    followAndIntake(command, container, trajectories.getEightBallCompatiblePartTwo());

    follow(command, container, trajectories.getEightBallCompatiblePartThree());
    follow(command, container, trajectories.getEightBallCompatiblePartFour());

    return command;
  }

  public Command getCircuit10BallAutoCommand(RobotContainer container) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    // Reset the robot pose
    resetRobotPose(command, container, trajectories.getCircuitTenBallAutoPartOne());
    // Pickup the first balls and shoot
    followAndIntake(command, container, trajectories.getCircuitTenBallAutoPartOne());
    followAndIntake(command, container, trajectories.getCircuitTenBallAutoPartTwo());
    // shootAtTarget(command, container);

    // Grab from trench
    followAndIntake(command, container, trajectories.getEightBallAutoPartTwo());
    followAndIntake(command, container, trajectories.getEightBallAutoPartThree());
    // shootAtTarget(command, container);

    return command;
  }

  public Command getSimpleShootThreeAutoCommand(RobotContainer container) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    resetRobotPose(command, container, trajectories.getSimpleShootThree());

    // shootAtTarget(command, container);
    follow(command, container, trajectories.getSimpleShootThree());

    return command;
  }

  public Command getStraightAutoCommand(RobotContainer container) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    resetRobotPose(command, container, trajectories.getStraightAutoPartOne());

    follow(command, container, trajectories.getStraightAutoPartOne());

    return command;
  }

  public Command getStraightBackShootCommand(RobotContainer container) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    resetRobotPose(command, container, trajectories.getStraightBackAndShoot());

    follow(command, container, trajectories.getStraightBackAndShoot());

    shootAtTarget(command, container);

    return command;
  }

  public Command getCommand(RobotContainer container) {
    switch (autonomousModeChooser.getSelected()) {
      case EIGHT_BALL:
        return get8BallAutoCommand(container);
      case EIGHT_BALL_COMPATIBLE:
        return get8BallCompatibleCommand(container);
      case TEN_BALL:
        return get10BallAutoCommand(container);
      case TEN_BALL_CIRCUIT:
        return getCircuit10BallAutoCommand(container);
      case SIMPLE_SHOOT_THREE:
        return getSimpleShootThreeAutoCommand(container);
      case STRAIGHT_LINE:
        return getStraightAutoCommand(container);
      case STRAIGHT_BACK_SHOOT:
        return getStraightBackShootCommand(container);
    }

    return get10BallAutoCommand(container);
  }

  private void shootAtTarget(SequentialCommandGroup command, RobotContainer container) {
    command.addCommands(new ShootBallCommand(container.getIntakeAndIndexerSubsystem()));
  }

  private void follow(
      SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
    command.addCommands(
        new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory));
  }

  private void followAndIntake(
      SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
    command.addCommands(
        new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory));
  }

  private void resetRobotPose(
      SequentialCommandGroup command, RobotContainer container, Trajectory trajectory) {
    command.addCommands(
        new InstantCommand(
            () -> container.getDrivetrainSubsystem().resetGyroAngle(Rotation2.ZERO)));
    command.addCommands(
        new InstantCommand(
            () ->
                container
                    .getDrivetrainSubsystem()
                    .resetPose(
                        new RigidTransform2(
                            trajectory.calculate(0.0).getPathState().getPosition(),
                            Rotation2.ZERO))));
  }

  private enum AutonomousMode {
    EIGHT_BALL,
    EIGHT_BALL_COMPATIBLE,
    TEN_BALL,
    TEN_BALL_CIRCUIT,
    SIMPLE_SHOOT_THREE,
    STRAIGHT_LINE,
    STRAIGHT_BACK_SHOOT
  }
}
