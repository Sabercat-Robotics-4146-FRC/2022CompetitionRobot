package org.frcteam4146.common.robot.subsystems;

import org.frcteam4146.common.math.Vector2;

@Deprecated
public abstract class HolonomicDrivetrain extends Drivetrain {

    public final void holonomicDrive(Vector2 translation, double rotation) {
        holonomicDrive(translation, rotation, false);
    }

    public abstract void holonomicDrive(Vector2 translation, double rotation, boolean fieldOriented);
}
