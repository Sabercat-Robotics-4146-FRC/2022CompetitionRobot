package org.frcteam4146.c2022.commands.subsystems;

import org.frcteam4146.c2022.Constants.ClimbConstants;
import org.frcteam4146.c2022.subsystems.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;

// uses a closed loop with position

public class AnchorArmCommand extends CommandBase {
    
    private final Climb climb;

    public AnchorArmCommand(Climb climb) {

        this.climb = climb;

    }

    @Override
    public void execute() {

        climb.setAnchorArmHeight(ClimbConstants.MAX_HEIGHT);
        
    }

    @Override
    public boolean isFinished() {

        return climb.isExtended();

    }

    @Override
    public void end(boolean interrupted) {

        climb.stopAnchorArm();

    }

}