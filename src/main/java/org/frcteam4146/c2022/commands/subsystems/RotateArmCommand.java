package org.frcteam4146.c2022.commands.subsystems;

import org.frcteam4146.c2022.subsystems.Climb;
import org.frcteam4146.common.robot.input.Axis;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateArmCommand extends CommandBase {
    Climb climb;
    Axis x;

    public RotateArmCommand(Climb climb, Axis x) {
        this.climb = climb;
        this.x = x;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }
}   
