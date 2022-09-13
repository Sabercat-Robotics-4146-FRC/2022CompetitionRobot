package org.frcteam2910.c2020.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.frcteam2910.common.control.*;
import org.frcteam2910.common.io.PathReader;
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
        new MaxVelocityConstraint(6.0 * 12.0); // change this to lower speed
    slowConstraints[slowConstraints.length - 2] =
        new MaxAccelerationConstraint(4.0 * 12.0); // change this to lower acceleration

    straightBackAndShoot =
        new Trajectory(
            new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO)
                .lineTo(new Vector2(0, -5.0), Rotation2.ZERO)
                .build(),
            trajectoryConstraints,
            SAMPLE_DISTANCE);
    straightAutoPartOne =
        new Trajectory(
            new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO)
                .lineTo(new Vector2(0, -5.0), Rotation2.ZERO)
                .build(),
            trajectoryConstraints,
            SAMPLE_DISTANCE);
  }

  private Path getPath(String name) throws IOException {
    InputStream in = getClass().getClassLoader().getResourceAsStream(name);
    if (in == null) {
      throw new FileNotFoundException("Path file not found: " + name);
    }

    try (PathReader reader = new PathReader(new InputStreamReader(in))) {
      return reader.read();
    }
  }
  public Trajectory getStraightAutoPartOne() {
    return straightAutoPartOne;
  }

  public Trajectory getStraightBackAndShoot() {
    return straightBackAndShoot;
  }
}
