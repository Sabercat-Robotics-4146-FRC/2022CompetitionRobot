package org.frcteam4146.c2022.commands.subsystems;

import org.frcteam4146.c2022.subsystems.Climb;
import org.frcteam4146.common.robot.input.Axis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExtendArmCommand extends CommandBase {
    Climb climb;
    Axis x;

    public ExtendArmCommand(Climb climb, Axis x) {
        this.climb = climb;
        this.x = x;
    }

    @Override
    public void execute() {
        climb.extendArm(x.get());
    }

    @Override
    public void end(boolean interupted) {
        // TODO Auto-generated method stub
        climb.extensionMotor.stopMotor();
    }
}
