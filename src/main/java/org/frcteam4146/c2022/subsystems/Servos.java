package org.frcteam4146.c2022.subsystems;

import static org.frcteam4146.c2022.Constants.ServoConstants;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Servos implements Subsystem {
  private final Servo servoRight;
  private final Servo servoLeft;

  private double setpoint = 0.2;

  public Servos() {
    servoLeft = new Servo(ServoConstants.LEFT_SERVO);
    servoRight = new Servo(ServoConstants.RIGHT_SERVO);

    servoRight.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    servoRight.setSpeed(1.0);
    servoRight.setSpeed(-1.0);
    servoRight.set(setpoint);
    servoLeft.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    servoLeft.setSpeed(1.0);
    servoLeft.setSpeed(-1.0);
    servoLeft.set(setpoint);
  }

  public void setPosition(double pos) {

    if (0.2 <= pos && pos <= 1) {
      if (Math.abs(pos - setpoint) >= 0.01) {
        servoLeft.setPosition(pos);
        servoRight.setPosition(pos);
        setpoint = pos;
      }
    }
  }

  public void setServos(double angle) {
    double pos = computePosition(angle);
    setPosition(pos);
  }

  public double computePosition(double angle) {

    double m = Math.tan(angle);
    double x = Math.sqrt(Math.pow(ServoConstants.RADIUS, 2) / (1 + m * m));
    double y = m * x;
    double dist =
        Math.sqrt(Math.pow((x - ServoConstants.PX), 2) + Math.pow((y - ServoConstants.PY), 2));
    double ext = dist - ServoConstants.LEN;
    double scaled_val = 1 - ext / ServoConstants.MAX_EXT + 0.2;

    SmartDashboard.putNumber("Desired Extension", scaled_val);
    return scaled_val;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Servo", setpoint);
  }
}
