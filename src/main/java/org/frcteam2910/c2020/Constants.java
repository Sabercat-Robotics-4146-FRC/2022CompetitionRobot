package org.frcteam2910.c2020;

public class Constants {

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

  public static final int HOOD_MOTOR_PORT = 5;

  public static final double HOOD_MOTOR_TO_HOOD_GEAR_RATIO = (16.0) * (32.0 / 18.0) * (48.0 / 16.0);

  public static final double HOOD_MIN_ANGLE = Math.toRadians(0);
  public static final double HOOD_MAX_ANGLE = Math.toRadians(61.59);

  public static final double BOTTOM_FLYWHEEL_GEAR_RATIO = 1.5;

  public static final int INTAKE_MOTOR_PORT = 41;

  public static final int INDEXER_BOTTOM = 21;
  public static final int INDEXER_TOP = 22;

  public static final int INDEXER_BOTTOM_SENSOR = 1;
  public static final int INDEXER_TOP_SENSOR = 2;

  public static final int END_LIFT_RIGHT = 52;
  public static final int END_LIFT_LEFT = 51;

  public static final int FLYWHEEL_LEADER = 31;
  public static final int FLYWHEEL_FOLLOWER = 32;

  public static final int LIFT_PIN = 0;

  public static final int PRIMARY_CONTROLLER_PORT = 0;

  public static final int SECONDARY_CONTROLLER_PORT = 1;

  public static final int PIGEON_PORT = 16;

  public static final int PRESSURE_SENSOR_PORT = 0;
}
