package org.frcteam4146.common.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import org.frcteam4146.common.Logger;
import org.frcteam4146.common.control.PidConstants;
import org.frcteam4146.common.math.MathUtils;
import org.frcteam4146.common.math.Vector2;
import org.frcteam4146.common.robot.input.Axis;
import org.frcteam4146.common.robot.subsystems.HolonomicDrivetrain;

@Deprecated
public final class HolonomicDriveCommand extends CommandBase {
  private static final double ROTATION_END_TIMEOUT = 0.5;
  private static final Logger LOGGER = new Logger(HolonomicDriveCommand.class);

  private final HolonomicDrivetrain drivetrain;
  private final Axis forwardAxis;
  private final Axis strafeAxis;
  private final Axis rotationAxis;
  private final Trigger fieldOrientedOverrideButton;

  private final Timer rotationEndTimer = new Timer();
  private boolean waitingForRotationTimer = false;

  private final PIDController angleController;
  private double angleControllerOutput = 0;

  public HolonomicDriveCommand(
      HolonomicDrivetrain drivetrain,
      Axis forwardAxis,
      Axis strafeAxis,
      Axis rotationAxis,
      Trigger fieldOrientedOverrideButton) {
    this(
        drivetrain,
        forwardAxis,
        strafeAxis,
        rotationAxis,
        fieldOrientedOverrideButton,
        new PidConstants(0, 0, 0));
  }

  public HolonomicDriveCommand(
      HolonomicDrivetrain drivetrain,
      Axis forwardAxis,
      Axis strafeAxis,
      Axis rotationAxis,
      Trigger fieldOrientedOverrideButton,
      PidConstants constants) {
    this.drivetrain = drivetrain;
    this.forwardAxis = forwardAxis;
    this.strafeAxis = strafeAxis;
    this.rotationAxis = rotationAxis;
    this.fieldOrientedOverrideButton = fieldOrientedOverrideButton;

    angleController =
        new PIDController (
          constants.p,
          constants.i,
          constants.d
        );
        // new PIDController(
        //     constants.p,
        //     constants.i,
        //     constants.d,
        //     new PIDSource() {
        //       @Override
        //       public void setPIDSourceType(PIDSourceType pidSource) {}

        //       @Override
        //       public double pidGet() {
        //         return drivetrain.getGyroscope().getAngle().toDegrees();
        //       }

        //       @Override
        //       public PIDSourceType getPIDSourceType() {
        //         return PIDSourceType.kDisplacement;
        //       }
        //     },
        //     output -> {
        //       angleControllerOutput = output;
        //     });
    angleController.enableContinuousInput(0, 360);

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    waitingForRotationTimer = false;

    angleController.setSetpoint(drivetrain.getGyroscope().getAngle().toDegrees());
  }

  @Override
  public void execute() {
    double forward = forwardAxis.get(true);
    double strafe = strafeAxis.get(true);
    double rotation = rotationAxis.get(true);
    boolean fieldOriented = !fieldOrientedOverrideButton.getAsBoolean();

    if (MathUtils.epsilonEquals(rotation, 0)) {
      if (waitingForRotationTimer) {
        if (rotationEndTimer.get() > ROTATION_END_TIMEOUT) {
          LOGGER.debug("Correcting angle");
          rotation = angleControllerOutput;

          if (rotationAxis.isInverted()) {
            rotation *= -1;
          }
        } else {
          angleController.setSetpoint(drivetrain.getGyroscope().getAngle().toDegrees());
        }
      } else {
        LOGGER.debug("Starting end wait");
        rotationEndTimer.reset();
        rotationEndTimer.start();
        waitingForRotationTimer = true;
      }
    } else {
      waitingForRotationTimer = false;
      angleController.setSetpoint(drivetrain.getGyroscope().getAngle().toDegrees());
    }

    drivetrain.holonomicDrive(new Vector2(forward, strafe), rotation, fieldOriented);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
