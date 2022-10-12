package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.LimelightConstants;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Limelight implements Subsystem{
    public static NetworkTable mLime;

    public double ballSpeed = LimelightConstants.BALL_SPEEDS[0];
    public double cameraAng = LimelightConstants.CAMERA_ANG;

    public Servos servos;

    public boolean tracking;

    public Limelight(Servos servos) {

        this.servos = servos;
        mLime = NetworkTableInstance.getDefault().getTable("limelight");

        SmartDashboard.putNumber("Camera Angle", cameraAng);

        SmartDashboard.putNumber("Ball Exit Speed", ballSpeed);
    }

    public boolean getSeesTarget() {
        return mLime.getEntry("tv").getBoolean(true);
    }

    public double getHorizontalOffset() {
        return mLime.getEntry("tx").getDouble(0);
    }

    public double getVerticalOffset() {
        return mLime.getEntry("ty").getDouble(0) + cameraAng;
    }

    public double getDistanceFromTarget() {
        double verticalOffset = getVerticalOffset() * (Math.PI / 180.0);
        return (LimelightConstants.HEIGHT_DIFF) / Math.tan(verticalOffset);
    }

    public double calculateShootingAngle() {
        double g = 9.81;
        double x = getDistanceFromTarget();
        double y = LimelightConstants.HEIGHT_DIFF;
        double s = ballSpeed;
        double theta =
                Math.atan((s * s + Math.sqrt(s * s * s * s - g * (g * x * x + 2 * y * s * s))) / (g * x));
        SmartDashboard.putNumber("Theta", theta);
        return theta;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void toggleTracking() {
        tracking = !tracking;
    }

    public void toggleTracking(boolean state) {
        tracking = state;
    }

    @Override
    public void periodic() {

        SmartDashboard.putBoolean("Limelight Tracking", tracking);
        SmartDashboard.putNumber("ty", mLime.getEntry("ty").getDouble(0.0));
        ballSpeed = SmartDashboard.getNumber("Ball Exit Speed", 0);
        cameraAng = SmartDashboard.getNumber("Camera Angle", 0);

        SmartDashboard.putNumber("Distance", getDistanceFromTarget());

        double desAng = calculateShootingAngle();
        ballSpeed = (getDistanceFromTarget() > LimelightConstants.DIST_CUTOFF)? LimelightConstants.BALL_SPEEDS[0] : LimelightConstants.BALL_SPEEDS[1];

        if (tracking) {
            if (getSeesTarget()) {
                servos.setServos(desAng);
            }
//            else {
//                System.out.println("Cannot see target");
//                servos.setServos(0.5);
//            }
        }
    }
}
