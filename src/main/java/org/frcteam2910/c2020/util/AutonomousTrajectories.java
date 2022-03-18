package org.frcteam2910.c2020.util;

import java.io.IOException;
import java.util.Arrays;
import org.frcteam2910.common.control.*;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;

public class AutonomousTrajectories {

  private static final double SAMPLE_DISTANCE = 0.1;

  private Trajectory straightBackAndShoot;

  private final Trajectory straightAutoPartOne;

  public AutonomousTrajectories(TrajectoryConstraint[] trajectoryConstraints) throws IOException {
    TrajectoryConstraint[] slowConstraints =
        Arrays.copyOf(trajectoryConstraints, trajectoryConstraints.length + 1);
    slowConstraints[slowConstraints.length - 1] =
        new MaxVelocityConstraint(1.0); // change this to lower speed
    slowConstraints[slowConstraints.length - 2] =
        new MaxAccelerationConstraint(2.0 * 1.0); // change this to lower acceleration

    straightBackAndShoot =
        new Trajectory(
            new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO)
                .lineTo(new Vector2(-5, 0), Rotation2.ZERO)
                .build(),
            trajectoryConstraints,
            SAMPLE_DISTANCE);

    straightAutoPartOne =
        new Trajectory(
            new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO)
                .lineTo(new Vector2(-14, 0), Rotation2.ZERO)
                .build(),
            slowConstraints,
            SAMPLE_DISTANCE);
  }

  public Trajectory getStraightAutoPartOne() {
    return straightAutoPartOne;
  }

  public Trajectory getStraightBackAndShoot() {
    return straightBackAndShoot;
  }
}
