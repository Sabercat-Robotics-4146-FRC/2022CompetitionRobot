package org.frcteam4146.c2022.commands.auto;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.frcteam4146.c2022.RobotContainer;
import org.frcteam4146.c2022.commands.FollowTrajectoryCommand;
import org.frcteam4146.c2022.subsystems.DrivetrainSubsystem;
import org.frcteam4146.common.control.Path;
import org.frcteam4146.common.control.SplinePathBuilder;
import org.frcteam4146.common.control.Trajectory;
import org.frcteam4146.common.io.PathReader;
import org.frcteam4146.common.math.Rotation2;
import org.frcteam4146.common.math.Vector2;

public class AutonomousFactory {
  RobotContainer container;

  public AutonomousFactory(RobotContainer container) {
    this.container = container;
  }

  /** Creates an autonomous path. */
  public SequentialCommandGroup createPath(String pathDirectory) {
    SequentialCommandGroup command = new SequentialCommandGroup();

    File root = new File(pathDirectory);
    // create an object for trajectories folder
    File trajectoriesFile = root.listFiles((dir, name) -> name.contains("trajectories"))[0];
    File waypointsFile = root.listFiles((dir, name) -> name.contains(".path"))[0];

    SmartDashboard.putString("Check 1", "true");


    // create the list of states from .json file
    List<List<State>> states = parseJson(waypointsFile.toPath());

    // Create trajectories from
    List<Trajectory> trajectories = getTrajectories(trajectoriesFile, states);

    SmartDashboard.putString("Check 3", "true");

    // create a list of commands found in the middle of a trajectory (not at a breakpoint)
    //List<SequentialCommandGroup> midCommands = getMidCommands(states);

    SmartDashboard.putString("Check 4", "true");

    // create a list of commands found at the end of a trajectory (at the breakpoint)
   // List<SequentialCommandGroup> endCommands = getEndCommands(states);

    SmartDashboard.putString("Check 5", "true");

    // compile a command that incorperates trajectory and subsystem commands
    for (int i = 0; i < trajectories.size(); i++) {
     // Command mid = midCommands.get(i);
     // Command endCommand = endCommands.get(i);
      Trajectory t = trajectories.get(i);

      // command.addCommands(
      //     new ParallelCommandGroup(
      //         new FollowTrajectoryCommand(this.container.getDrivetrainSubsystem(), t), 
      //         mid),
      //     endCommand);
      command.addCommands(
        new FollowTrajectoryCommand(container.getDrivetrainSubsystem(), t)
      );
    }

    SmartDashboard.putString("Check 6", "true");

    return command;
  }

  public List<SequentialCommandGroup> getMidCommands(List<List<State>> values) {
    List<SequentialCommandGroup> commands = new ArrayList<>();

    for (List<State> list : values) {
      SequentialCommandGroup c = new SequentialCommandGroup();
      double lastTime = 0.0;
      for (int i = 0; i < list.size() - 1; i++) {
        State state = list.get(i);
        c.addCommands(getCommand(state.action).withTimeout(state.time - lastTime));
        lastTime = state.time;
      }
      commands.add(c);
    }

    return commands;
  }

  public List<SequentialCommandGroup> getEndCommands(List<List<State>> list) {
    List<SequentialCommandGroup> commands = new ArrayList<>();

    for (List<State> states : list) {
      SequentialCommandGroup c =
          new SequentialCommandGroup(getCommand(states.get(states.size() - 1).action));
      commands.add(c);
    }

    return commands;
  }

  public List<Trajectory> getTrajectories(File file, List<List<State>> waypoints) {
    List<Trajectory> trajectories = new ArrayList<>();

    ShuffleboardTab tab = Shuffleboard.getTab("Auto Readout");
    tab.add("Trajectory File" , file.getPath());
    tab.add("W Length", waypoints.size());
    tab.add("T Length", file.listFiles().length);

    // iterate through all files within the folder "trajectories"
    Iterator<List<State>> i = waypoints.iterator();
    for (File f : file.listFiles()) {

      // filter .json files only
      if (f.toPath().toString().endsWith(".json")) {
        List<State> list = i.next();
        Trajectory trajectory;
        try {
          SplinePathBuilder splinePath = 
            new SplinePathBuilder(
              new Vector2(list.get(0).x, list.get(0).y),
              new Rotation2(Math.cos(list.get(0).heading), Math.sin(list.get(0).heading), true), 
              new Rotation2(Math.cos(list.get(0).heading), Math.sin(list.get(0).heading), true));

          for(int j = 1; j < list.size(); j++) {
            State waypoint = list.get(j);
            State waypoint2 = list.get(j-1);
            Vector2 v1 = new Vector2(waypoint2.tx, waypoint2.ty);
            Vector2 v2 = new Vector2(waypoint.tx, waypoint.ty);
            Vector2 end = new Vector2(waypoint.x, waypoint.y);
            Rotation2 r = new Rotation2(Math.cos(waypoint.heading), Math.sin(waypoint.heading), true);

            tab.add("Control Point 1", "(" + v1.x + ", " + v1.y + ")");
            tab.add("Control Point 2", "(" + v2.x + ", " + v2.y + ")");
            tab.add("End Point", "(" + end.x + ", " + end.y + ")");

            //splinePath.bezier(v1, v2, end, r);
            splinePath.hermite(end, r, r);

            tab.add("ITERATION" , j);
          }

          Path path = splinePath.build();

          //SmartDashboard.putString("PATH" , path.getSegments().);

          trajectory = new Trajectory(path, DrivetrainSubsystem.TRAJECTORY_CONSTRAINTS, 0.1);
          trajectories.add(trajectory);
        } catch (Exception e) {
          SmartDashboard.putString("Trajectory Error Message" , e.getMessage());
          SmartDashboard.putString("Trajectory Error Type" , e.toString());
        }
      }
    }

    return trajectories;
  }

  public List<List<State>> parseJson(java.nio.file.Path path) {

    // read JSON file
    String json = "";
    try {
      json = Files.readString(path, StandardCharsets.UTF_8);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // Convert JSON file into an ArrayList of type State
    // This creates an array list of all the waypoints, with
    // State representing the values within each waypoint.
    Type type = new TypeToken<List<State>>() {}.getType();
    List<State> list = new Gson().fromJson(json, type);

    SmartDashboard.putString("JSON", list.get(0).toString());

    // Map waypoints such that they corrospond to their respective
    // trajectories.
    List<List<State>> dividedLists = new ArrayList<>();
    List<State> temp = new ArrayList<>();
    list.forEach(
        (l) -> {
          temp.add(l);
          if (l.isBreak) {
            dividedLists.add(temp);
            temp.clear();
          }
        });
    dividedLists.add(temp);

    return dividedLists;
  }

  // this is just a skeleton method for now. When all commands are
  // implemented, add them to this method

  private Command getCommand(String name) {
    // return
    //   switch (name) {
    //     default -> new SequentialCommandGroup();
    // }

    return new SequentialCommandGroup();
  }

  private static class State {
    private double x;
    private double y;
    private double tx;
    private double ty;
    private double heading;
    private double time;
    private boolean isBreak;
    private String action;

    public String toString() {
      return "x = "
          + x
          + " y = "
          + y
          + "tx = "
          + tx 
          + "ty = "
          + ty
          + "heading = "
          + heading
          + "break = "
          + isBreak
          + " action = "
          + action
          + "time = "
          + time;
    }
  }
}
