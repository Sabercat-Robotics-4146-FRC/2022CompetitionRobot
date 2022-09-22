package org.frcteam4146.c2022.commands.drive;

import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.math.Vector2;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ReturnRobotCommand extends CommandBase {
    
    DrivetrainSubsystem drivetrain;

    double rotation;

    double rotationSpeed = 0;

    public ReturnRobotCommand(DrivetrainSubsystem drivetrain) {
        this.drivetrain = drivetrain;   
    }

    @Override
    public void initialize() {
        rotation = drivetrain.returnAngle;
    }

    @Override
    public void execute() {
        double currentPosition = drivetrain.getPose().rotation.toDegrees();

        drivetrain.drive(Vector2.ZERO, 0, false);
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }
}
