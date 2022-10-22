package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.DriveConstants;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.swervedrivespecialties.swervelib.Mk4SwerveModuleHelper;
import com.swervedrivespecialties.swervelib.SwerveModule;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.ArrayList;
import java.util.Optional;
import org.frcteam4146.common.control.*;
import org.frcteam4146.common.drivers.Gyroscope;
import org.frcteam4146.common.kinematics.ChassisVelocity;
import org.frcteam4146.common.kinematics.SwerveKinematics;
import org.frcteam4146.common.kinematics.SwerveOdometry;
import org.frcteam4146.common.math.RigidTransform2;
import org.frcteam4146.common.math.Rotation2;
import org.frcteam4146.common.math.Vector2;
import org.frcteam4146.common.robot.UpdateManager;
import org.frcteam4146.common.util.*;

public class DrivetrainSubsystem implements Subsystem, UpdateManager.Updatable {
  public boolean driveFlag = false;

  /*
    This value is used to turn the robot back to its initialPosition
  */
  public ArrayList<Double> speeds;

  public Timer m_Timer;

  public boolean fieldOriented;

  // tune with sysid, view w/ .3190 meters per rotation
  public static final DrivetrainFeedforwardConstants FEEDFORWARD_CONSTANTS =
      new DrivetrainFeedforwardConstants( // tune
          // with
          // sysid,
          // view
          // w/
          // .3190
          // meters
          // per
          // rotation
          0.70067, 2.2741, 0.16779);

  public static final TrajectoryConstraint[] TRAJECTORY_CONSTRAINTS = {
    new FeedforwardConstraint(
        11.0,
        FEEDFORWARD_CONSTANTS.getVelocityConstant(),
        FEEDFORWARD_CONSTANTS.getAccelerationConstant(),
        false),
    new MaxAccelerationConstraint(1 * 12.0), // originally 12.5 * 12.0
    new CentripetalAccelerationConstraint(1 * 12.0)
  };

  private final HolonomicMotionProfiledTrajectoryFollower follower =
      new HolonomicMotionProfiledTrajectoryFollower(
          new PidConstants(0.035597, 0.0, 0.0015618),
          new PidConstants(0.035597, 0.0, 0.0015618),
          new HolonomicFeedforward(FEEDFORWARD_CONSTANTS));

  private final SwerveKinematics swerveKinematics =
      new SwerveKinematics(
          new Vector2(
              DriveConstants.TRACKWIDTH / 2.0, DriveConstants.WHEELBASE / 2.0), // front left
          new Vector2(
              DriveConstants.TRACKWIDTH / 2.0, -DriveConstants.WHEELBASE / 2.0), // front right
          new Vector2(
              -DriveConstants.TRACKWIDTH / 2.0, DriveConstants.WHEELBASE / 2.0), // back left
          new Vector2(
              -DriveConstants.TRACKWIDTH / 2.0, -DriveConstants.WHEELBASE / 2.0) // back right
          );

  // private final SwerveDriveKinematics wpi_driveKinematics = new
  // SwerveDriveKinematics(
  // new Translation2d(-TRACKWIDTH / 2.0, WHEELBASE / 2.0), //front left
  // new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0), //front right
  // new Translation2d(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0), // back left
  // new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0) // back right
  // );

  private final SwerveModule[] modules;
  private final TalonSRX[] talons;

  private final Gyroscope gyroscope;

  private final SwerveOdometry swerveOdometry =
      new SwerveOdometry(swerveKinematics, RigidTransform2.ZERO);
  private RigidTransform2 pose = RigidTransform2.ZERO;
  private Vector2 velocity = Vector2.ZERO;
  private double angularVelocity = 0.0;

  private HolonomicDriveSignal driveSignal;

  // Logging
  private final NetworkTableEntry odometryXEntry;
  private final NetworkTableEntry odometryYEntry;
  private final NetworkTableEntry odometryAngleEntry;

  public DrivetrainSubsystem(Gyroscope gyroscope) {
    this.gyroscope = gyroscope;
    SmartDashboard.putBoolean("Drive Flag", driveFlag);
    gyroscope.setInverted(false);
    driveSignal = new HolonomicDriveSignal(new Vector2(0, 0), 0.0, true);

    m_Timer = new Timer();

    ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

    SwerveModule frontLeftModule =
        Mk4SwerveModuleHelper.createFalcon500(
            tab.getLayout("Front Left Module", BuiltInLayouts.kList)
                .withPosition(2, 0)
                .withSize(2, 4),
            Mk4SwerveModuleHelper.GearRatio.L2,
            DriveConstants.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR,
            DriveConstants.DRIVETRAIN_FRONT_LEFT_STEER_MOTOR,
            DriveConstants.DRIVETRAIN_FRONT_LEFT_STEER_ENCODER,
            DriveConstants.DRIVETRAIN_FRONT_LEFT_STEER_OFFSET);
    SwerveModule frontRightModule =
        Mk4SwerveModuleHelper.createFalcon500(
            tab.getLayout("Front Right Module", BuiltInLayouts.kList)
                .withPosition(4, 0)
                .withSize(2, 4),
            Mk4SwerveModuleHelper.GearRatio.L2,
            DriveConstants.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR,
            DriveConstants.DRIVETRAIN_FRONT_RIGHT_STEER_MOTOR,
            DriveConstants.DRIVETRAIN_FRONT_RIGHT_STEER_ENCODER,
            DriveConstants.DRIVETRAIN_FRONT_RIGHT_STEER_OFFSET);
    SwerveModule backLeftModule =
        Mk4SwerveModuleHelper.createFalcon500(
            tab.getLayout("Back Left Module", BuiltInLayouts.kList)
                .withPosition(6, 0)
                .withSize(2, 4),
            Mk4SwerveModuleHelper.GearRatio.L2,
            DriveConstants.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR,
            DriveConstants.DRIVETRAIN_BACK_LEFT_STEER_MOTOR,
            DriveConstants.DRIVETRAIN_BACK_LEFT_STEER_ENCODER,
            DriveConstants.DRIVETRAIN_BACK_LEFT_STEER_OFFSET);
    SwerveModule backRightModule =
        Mk4SwerveModuleHelper.createFalcon500(
            tab.getLayout("Back Right Module", BuiltInLayouts.kList)
                .withPosition(8, 0)
                .withSize(2, 4),
            Mk4SwerveModuleHelper.GearRatio.L2,
            DriveConstants.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR,
            DriveConstants.DRIVETRAIN_BACK_RIGHT_STEER_MOTOR,
            DriveConstants.DRIVETRAIN_BACK_RIGHT_STEER_ENCODER,
            DriveConstants.DRIVETRAIN_BACK_RIGHT_STEER_OFFSET);

    modules =
        new SwerveModule[] {frontLeftModule, frontRightModule, backLeftModule, backRightModule};

    talons =
        new TalonSRX[] {
          new TalonSRX(DriveConstants.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR),
          new TalonSRX(DriveConstants.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR),
          new TalonSRX(DriveConstants.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR),
          new TalonSRX(DriveConstants.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR)
        };

    for (var talon : talons) {
      talon.configPeakCurrentLimit(15); // max. current (amps)
      talon.configPeakCurrentDuration(
          5); // # milliseconds after peak reached before regulation starts
      talon.configContinuousCurrentLimit(10); // continuous current (amps) after regulation
      talon.configOpenloopRamp(1); // # seconds to reach peak throttle
    }

    odometryXEntry = tab.add("X", 0.0).withPosition(0, 0).withSize(1, 1).getEntry();
    odometryYEntry = tab.add("Y", 0.0).withPosition(0, 1).withSize(1, 1).getEntry();
    odometryAngleEntry = tab.add("Angle", 0.0).withPosition(0, 2).withSize(1, 1).getEntry();

    tab.addNumber(
            "Trajectory X",
            () -> {
              if (follower.getLastState() == null) {
                return 0.0;
              }
              return follower.getLastState().getPathState().getPosition().x;
            })
        .withPosition(1, 0)
        .withSize(1, 1);
    tab.addNumber(
            "Trajectory Y",
            () -> {
              if (follower.getLastState() == null) {
                return 0.0;
              }
              return follower.getLastState().getPathState().getPosition().y;
            })
        .withPosition(1, 1)
        .withSize(1, 1);

    tab.addNumber(
        "Rotation Voltage",
        () -> {
          HolonomicDriveSignal signal;
          signal = driveSignal;
          if (signal == null) {
            return 0.0;
          }

          return signal.getRotation() * RobotController.getBatteryVoltage();
        });

    tab.addNumber("Average Velocity", this::getAverageAbsoluteValueVelocity);
  }

  public void drive(
      Vector2 translationalVelocity, double rotationalVelocity, boolean isFieldOriented) {

    driveSignal =
        new HolonomicDriveSignal(translationalVelocity, rotationalVelocity, isFieldOriented);
  }

  public void drive(Vector2 translationalVelocity, double rotationalVelocity) {
    drive(translationalVelocity, rotationalVelocity, fieldOriented);
  }

  public void resetPose(RigidTransform2 pose) {
    this.pose = pose;
    swerveOdometry.resetPose(pose);
  }

  public void resetGyroAngle(Rotation2 angle) {
    gyroscope.setAdjustmentAngle(gyroscope.getUnadjustedAngle().rotateBy(angle.inverse()));
  }

  private void updateOdometry(double time, double dt) {
    Vector2[] moduleVelocities = getModuleVelocities();

    Rotation2 angle;
    double angularVelocity;

    angle = gyroscope.getAngle();
    angularVelocity = gyroscope.getRate();

    ChassisVelocity velocity = swerveKinematics.toChassisVelocity(moduleVelocities);

    this.pose = swerveOdometry.update(angle, dt, moduleVelocities);
    this.velocity = velocity.getTranslationalVelocity();
    this.angularVelocity = angularVelocity;
  }

  /*
   * The chassis velocity is rotated inversely to the angle of the robot
   * when it is field rotated to account for the robot's direction on the field.
   * When it is not field oriented, this
   * does not occur
   */
  private void updateModules(HolonomicDriveSignal driveSignal, double dt) {
    ChassisVelocity chassisVelocity;

    if (driveSignal.isFieldOriented()) {
      chassisVelocity =
          new ChassisVelocity(
              driveSignal.getTranslation().rotateBy(getPose().rotation.inverse()),
              driveSignal.getRotation());
    } else {
      chassisVelocity =
          new ChassisVelocity(driveSignal.getTranslation(), driveSignal.getRotation());
    }

    Vector2[] moduleOutputs = swerveKinematics.toModuleVelocities(chassisVelocity);
    SwerveKinematics.normalizeModuleVelocities(moduleOutputs, 1);
    for (int i = 0; i < moduleOutputs.length; i++) {
      var module = modules[i];
      // As maximum velocity is normalized to 1, the maximum voltage passed to a
      // module is 12 Volts.
      module.set(moduleOutputs[i].length * 12.0, moduleOutputs[i].getAngle().toRadians());
    }
  }

  @Override
  public void update(double time, double dt) {
    updateOdometry(time, dt);

    if (driveFlag) {
      HolonomicDriveSignal driveSignal;
      Optional<HolonomicDriveSignal> trajectorySignal =
          follower.update(getPose(), getVelocity(), getAngularVelocity(), time, dt);
      driveSignal = trajectorySignal.orElseGet(() -> this.driveSignal);
      updateModules(driveSignal, dt);
    } else {
      updateModules(new HolonomicDriveSignal(new Vector2(0, 0), 0, false), dt);
    }
  }

  @Override
  public void periodic() {
    // get the pose and publish the x-y translations and
    // rotation degrees to SmartDashboard

    SmartDashboard.putBoolean("Field Oriented", fieldOriented);
    SmartDashboard.putBoolean("Drive Flag", driveFlag);

    RigidTransform2 pose = getPose();
    odometryXEntry.setDouble(pose.translation.x);
    odometryYEntry.setDouble(pose.translation.y);
    odometryAngleEntry.setDouble(getPose().rotation.toDegrees());
  }

  public double getAverageAbsoluteValueVelocity() {
    double averageVelocity = 0;
    for (var module : modules) {
      averageVelocity += Math.abs(module.getDriveVelocity());
    }
    return averageVelocity / 4;
  }

  public Vector2[] getModuleVelocities() {
    Vector2[] velocities = new Vector2[modules.length];
    for (int i = 0; i < modules.length; i++) {
      velocities[i] =
          Vector2.fromAngle(Rotation2.fromRadians(modules[i].getSteerAngle()))
              .scale(modules[i].getDriveVelocity() * 39.37008);
    }
    return velocities;
  }

  public RigidTransform2 getPose() {
    return pose;
  }

  public HolonomicMotionProfiledTrajectoryFollower getFollower() {
    return follower;
  }

  public Vector2 getVelocity() {
    return velocity;
  }

  public double getAngularVelocity() {
    return angularVelocity;
  }

  public void toggleFieldOriented() {
    fieldOriented = !fieldOriented;
  }

  public void toggleDriveFlag() {
    driveFlag = !driveFlag;
  }

  public Vector2 getTranslation() {
    return driveSignal.getTranslation();
  }
}
