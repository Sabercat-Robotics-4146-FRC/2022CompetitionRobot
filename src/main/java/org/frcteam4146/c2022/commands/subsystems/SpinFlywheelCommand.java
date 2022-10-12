package org.frcteam4146.c2022.commands.subsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.frcteam4146.c2022.subsystems.Flywheel;

public class SpinFlywheelCommand extends CommandBase {
    private final Flywheel flywheel;
    private final boolean state;
    private final double distance;

    public SpinFlywheelCommand(Flywheel flywheel, boolean state, double distance) {
        this.flywheel = flywheel;
        this.distance = distance;
        this.state = state;
    }
    public SpinFlywheelCommand(Flywheel flywheel, boolean state) {
        this(flywheel, state, 2);
    }

    @Override
    public void initialize() {
        flywheel.determineSetpoint(distance);
        flywheel.toggleFlywheel(state);
    }

    @Override
    public void end(boolean interrupted) {
        flywheel.toggleFlywheel(false);
    }
}

