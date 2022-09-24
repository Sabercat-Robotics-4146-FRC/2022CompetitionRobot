package org.frcteam4146.c2022.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Servos implements Subsystem {
  private Servo servoRight;
  private Servo servoLeft;

  private double setpoint = 0.2;

  public Servos() {
    servoLeft = new Servo(0);
    servoRight = new Servo(1);

    servoRight.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    servoRight.setSpeed(1.0); // to open
    servoRight.setSpeed(-1.0); // to close
    servoRight.set(setpoint);
    servoLeft.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
    servoLeft.setSpeed(1.0); // to open
    servoLeft.setSpeed(-1.0); // to close
    servoLeft.set(setpoint);
  }

  public void setPosition(double pos) {
    if (0.2 <= pos && pos <= 1) {
      if (Math.abs(pos - setpoint) >= 0.01) { // Hedge against jitter
        servoLeft.setPosition(pos);
        servoRight.setPosition(pos);
        setpoint = pos;
      }
    }
    // else {
    //  SmartDashboard.putBoolean("Flag", true);
    //  servoLeft.set(0.2);
    //  servoRight.set(0.2);
    // }
  }

  public void setServos(double angle) {
    double pos = computePosition(angle);
    setPosition(pos);
  }

  public double computePosition(double angle) {
    // TODO: Roughly measured initial and final arc points from cad file, remeasure if needed
    double radius = .25;
    double px = .345;
    double py = -0.04;
    double len = 0.22;
    double maxExt = 0.15;

    double m = Math.tan(angle);
    double x = Math.sqrt(radius * radius / (1 + m * m));
    double y = m * x;

    double dist = Math.sqrt((x - px) * (x - px) + (py + y) * (py + y));
    double ext = dist - len;
    double scaled_val = ext / maxExt;

    SmartDashboard.putNumber("Desired Extension", scaled_val);
    // Scaled from 0 to 1, if outside this range, the shot is unobtainable at the current robot
    // position
    return scaled_val;
  }
}
