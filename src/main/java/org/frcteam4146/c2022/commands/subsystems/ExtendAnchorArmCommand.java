package org.frcteam4146.c2022.commands.subsystems;

import org.frcteam4146.c2022.subsystems.Climb;
import org.frcteam4146.common.robot.input.Axis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExtendAnchorArmCommand extends CommandBase {
    Climb climb;
    Axis up;
    Axis down;

    public ExtendAnchorArmCommand(Climb climb, Axis up, Axis down) {
        this.climb = climb;
        this.up = up;
        this.down = down;
    }

    @Override
    public void execute() {
        climb.extendAnchorArm(up.get(), down.get());
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        climb.anchorMotor.stopMotor();
    }
}
