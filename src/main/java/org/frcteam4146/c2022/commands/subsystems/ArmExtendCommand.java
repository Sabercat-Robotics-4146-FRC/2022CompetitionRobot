package org.frcteam4146.c2022.commands.subsystems;

import org.frcteam4146.c2022.subsystems.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmExtendCommand extends CommandBase {
       
    Climb climb;

    public ArmExtendCommand(Climb climb) {
        this.climb = climb;
    }

    @Override
    public void execute() {
        climb.extendAnchorArm();

    }

    @Override
    public boolean isFinished() {
        return climb.armIsExtended();
    }

    @Override
    public void end(boolean interrupted) {
        climb.extensionMotor.stopMotor();
    }
}
