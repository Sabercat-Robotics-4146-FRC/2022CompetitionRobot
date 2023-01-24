package org.frcteam4146.c2022.commands.auto;

import org.frcteam4146.c2022.RobotContainer;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutonomousTab {
    private final SendableChooser<String> loadingBayChooser = new SendableChooser<>();

  public AutonomousTab(RobotContainer container) {
    ShuffleboardTab tab = Shuffleboard.getTab("Driver Readout");


    tab.add("Autonomous Mode", container.getAutoSelector().getChooser())
        .withSize(2, 1)
        .withPosition(2, 0);
  }
}
