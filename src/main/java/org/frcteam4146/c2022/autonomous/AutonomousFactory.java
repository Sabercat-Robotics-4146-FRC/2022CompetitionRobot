package org.frcteam4146.c2022.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.io.File;
import java.io.FileReader;
import org.frcteam4146.c2022.RobotContainer;
import org.frcteam4146.c2022.commands.drive.FollowTrajectoryCommand;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
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
    public SequentialCommandGroup toggleSubsystems(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        // add commands to toggle

        return command;
    }

    /*
     * creates an autonomous path.
     */

    public SequentialCommandGroup createPath(String pathDirectory) {
        File root = new File(pathDirectory);
        SequentialCommandGroup command = new SequentialCommandGroup();

        File[] files = root.listFiles();

        for (File f : files) {
            if (f.isFile()) {

                Trajectory trajectory = null;
                
                try {

                    // create trajectory from JSON file
                    PathReader pathReader = new PathReader(new FileReader(f));
                    Path path = pathReader.read();

                    trajectory = new Trajectory(
                        path, // read JSON file and convert to Path
                        new TrajectoryConstraint[0], // N/A
                        0); // TODO find this value
                    
                    if (f.getName().toLowerCase().contains("ball")) {
                        command.addCommands(createAutoBallCommand(trajectory, f));
                    } else {
                        command.addCommands(createAutoNonBallCommand(trajectory, f));
                    }
                } catch (Exception e) {}
            }
        }

        return command;
    }

    /*
     * creates auto command that interacts with a ball
     */
    public SequentialCommandGroup createAutoBallCommand(Trajectory trajectory, File f) throws Exception {
        SequentialCommandGroup command = new SequentialCommandGroup();
        
        // command.addCommands(new FollowTrajectoryCommand(drivetrain, trajectory),
                            // new LoadBallCommand()
                            // new ShootBallCommand());
        // TODO: add commands

        return command;
    }

    /*
     * creates auto command that does not interact with a ball
     */
    public SequentialCommandGroup createAutoNonBallCommand(Trajectory trajectory, File f) throws Exception {
        SequentialCommandGroup command = new SequentialCommandGroup();
        
        command.addCommands(new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), trajectory));

        return command;
    }
}
