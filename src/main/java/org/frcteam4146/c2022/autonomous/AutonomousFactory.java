package org.frcteam4146.c2022.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.io.File;
import java.io.FileReader;
import org.frcteam4146.c2022.RobotContainer;
import org.frcteam4146.c2022.commands.commandGroups.LoadBallCommand;
import org.frcteam4146.c2022.commands.drive.AimRobotCommand;
import org.frcteam4146.c2022.commands.drive.FollowTrajectoryCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleFlywheelCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleIntakeExtensionCommand;
import org.frcteam4146.c2022.commands.subsystems.ToggleIntakeMotorCommand;
import org.frcteam4146.common.control.Path;
import org.frcteam4146.common.control.Trajectory;
import org.frcteam4146.common.control.TrajectoryConstraint;
import org.frcteam4146.common.io.PathReader;

public class AutonomousFactory {
    RobotContainer container;

    public AutonomousFactory(RobotContainer container) {
        this.container = container;
    }

    /*
     * begins subsystems that will stay activated throughout the autonomous
     */
    public SequentialCommandGroup toggleSubsystems(boolean state) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        // add commands to toggle
        command.addCommands(
            new ToggleFlywheelCommand(container.getFlywheelSubsystem(), state),
            new ToggleIntakeExtensionCommand(container.getIntakeSubsystem(), state),
            new ToggleIntakeMotorCommand(container.getIntakeSubsystem(), state)
            /* possibly toggle a sensor on the intake/indexer 
             * The sensor would be used to automatically move any ball
             * picked up to the second holding position within the indexer
             * 
             * This function can also be used in teleop. 
            */
        );

        return command;
    }

    /*
     * creates an autonomous path.
     */

    public SequentialCommandGroup createPath(String pathDirectory) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        File[] files = {};

        // path directory can potentially be null, so throw a NullPointerException
        try {
            File root = new File(pathDirectory);
            files = root.listFiles();

        } catch (NullPointerException e){
            // if the directory does not exist, just return
            // empty command here
            return command;
        }
    

        for (File f : files) {
            if (f.isFile()) {

                Trajectory trajectory = null;
                try {
                    // create trajectory from JSON file
                    PathReader pathReader = new PathReader(new FileReader(f));
                    Path path = pathReader.read();
                    boolean ball = f.getName().toLowerCase().contains("ball");
                    boolean shoot = f.getName().toLowerCase().contains("shoot");

                    trajectory = new Trajectory(
                        path, // read JSON file and convert to Path
                        new TrajectoryConstraint[0], // N/A
                        0); // TODO: I dont know what this is; we need to know this though. 

                    command.addCommands(
                        createAutoCommand(trajectory, f, ball, shoot)
                    );

                } catch (Exception e) {}
            }
        }

        return command;
    }

    /*
     * creates auto command that interacts with a ball
     */
    public SequentialCommandGroup createAutoCommand(
        Trajectory trajectory, 
        File f,
        boolean ball,
        boolean shoot) throws Exception {
            
        SequentialCommandGroup command = new SequentialCommandGroup();
        
        command.addCommands(
            new FollowTrajectoryCommand(
                container.getDrivetrainSubsystem(), 
                trajectory)
            );
        
        if(ball) {
            command.addCommands(
                new WaitCommand(0.5) // allow the intake to fully retrieve the ball. this number may change.
            );
        }

        if(shoot) {
            command.addCommands(
                new AimRobotCommand(
                    container.getDrivetrainSubsystem(),
                    container.getLimelightSubsystem()),
                new LoadBallCommand(
                    container.getIndexerSubsystem())
                /* turn back to original position */
            );
        }
        // TODO: add commands

        return command;
    }

}
