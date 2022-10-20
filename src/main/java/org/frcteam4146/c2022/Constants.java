package org.frcteam4146.c2022;

public final class Constants {

  public static final class DriveConstants {

    public static final double TRACKWIDTH = 24.0;
    public static final double WHEELBASE = 24.0;

    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 3;
    public static final int DRIVETRAIN_FRONT_LEFT_STEER_MOTOR = 4;
    public static final int DRIVETRAIN_FRONT_LEFT_STEER_ENCODER = 10;
    public static final double DRIVETRAIN_FRONT_LEFT_STEER_OFFSET =
        -Math.toRadians(15.380859374999998);

    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 7;
    public static final int DRIVETRAIN_FRONT_RIGHT_STEER_MOTOR = 8;
    public static final int DRIVETRAIN_FRONT_RIGHT_STEER_ENCODER = 12;
    public static final double DRIVETRAIN_FRONT_RIGHT_STEER_OFFSET = -Math.toRadians(245.21484375);

    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 1;
    public static final int DRIVETRAIN_BACK_LEFT_STEER_MOTOR = 2;
    public static final int DRIVETRAIN_BACK_LEFT_STEER_ENCODER = 9;
    public static final double DRIVETRAIN_BACK_LEFT_STEER_OFFSET =
        -Math.toRadians(189.66522216796875);

    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 5;
    public static final int DRIVETRAIN_BACK_RIGHT_STEER_MOTOR = 6;
    public static final int DRIVETRAIN_BACK_RIGHT_STEER_ENCODER = 11;
    public static final double DRIVETRAIN_BACK_RIGHT_STEER_OFFSET =
        -Math.toRadians(239.14489746093753);

    public static final int PRIMARY_CONTROLLER_PORT = 0;
    public static final int PIGEON_PORT = 16;
  }

  public static final class FlywheelConstants {
    public static final int FLYWHEEL_LEADER = 31;
    public static final int FLYWHEEL_FOLLOWER = 32;

    public static final double[] flywheelSetpoints = {2750, 2500, 2175};

    public static final double P = 0.00002;
    public static final double I = 0.00000022;
    public static final double D = 0;
    public static final double IZ = 0;
    public static final double FF = 0.000;
    public static final double MAX_OUTPUT = 1;
    public static final double MIN_OUTPUT = -1;
  }

  public static final class IndexerConstants {
    public static final int INDEXER_BOTTOM = 21;
    public static final int INDEXER_TOP = 22;

    public static final int INDEXER_BOTTOM_SENSOR = 1;
    public static final int INDEXER_TOP_SENSOR = 2;
  }

  public static final class LimelightConstants {
    public static final double LIMELIGHT_HEIGHT = 29.75 / 100.0;
    public static final double TARGET_HEIGHT = 104 / 100.0 + 0.05;
    public static final double HOOP_AIM_OFFSET = 0.4;

    public static final double[] BALL_SPEEDS = {4.85, 4.75, 4.45};
    public static final double CAMERA_ANG = 60;
    public static final double[] DIST_CUTOFF = {1.27, 1.02};

    public static final double HEIGHT_DIFF = TARGET_HEIGHT - LIMELIGHT_HEIGHT;
  }

  public static final class ServoConstants {
    public static final int LEFT_SERVO = 0;
    public static final int RIGHT_SERVO = 1;

    public static final double RADIUS = .25;
    public static final double PX = .36;
    public static final double PY = 0.07;
    public static final double LEN = 0.21;
    public static final double MAX_EXT = 0.17;
  }

  public static final class IntakeConstants {

    public static final int INTAKE_MOTOR = 13;
    public static final int INTAKE_SOLENOID = 21;
  }

  public static final class ClimbConstants {
    public static final int ANCHOR_MOTOR = 41; // TODO check in phoenix tuner
    public static final int EXTENSION_MOTOR = 42;
    public static final int ROTATION_MOTOR = 43;

    public static final double MAX_HEIGHT = 0; // TODO calculate
    public static final double MIN_HEIGHT = 0;
    public static final double VERTICAL_DISTANCE_TO_MOTOR = 0;
  }
}
