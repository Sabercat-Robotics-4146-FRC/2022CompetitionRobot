package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Climb;
import org.frcteam4146.common.robot.input.Axis;

public class ClimbCommand extends CommandBase {
  public final Climb climb;

  public final Axis rotAxis;
  public final Axis armAxis;

  public final Axis[] anchorAxis;

  public ClimbCommand(Climb climb, Axis armAxis, Axis rotAxis, Axis leftUp, Axis rightDown) {
    this.climb = climb;
    this.rotAxis = rotAxis;
    this.armAxis = armAxis;
    anchorAxis = new Axis[] {leftUp, rightDown};

    addRequirements(climb);
  }

  @Override
  public void execute() {
    climb.setArmExtension(armAxis.get());
    climb.setRotation(rotAxis.get());
    climb.setAnchorExtension(anchorAxis[0].get() - anchorAxis[1].get());
  }
}
