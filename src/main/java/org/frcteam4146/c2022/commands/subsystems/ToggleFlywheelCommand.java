package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Flywheel;

public class ToggleFlywheelCommand extends CommandBase {
    private final Flywheel flywheel;
    private boolean state;
    public ToggleFlywheelCommand(Flywheel flywheel, boolean state) {
        this.flywheel = flywheel;
        this.state = state;
    }
    @Override
    public void initialize() {flywheel.toggleFlywheel(state);}
}
