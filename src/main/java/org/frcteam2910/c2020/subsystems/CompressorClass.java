package org.frcteam2910.c2020.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class CompressorClass implements Subsystem {

  Compressor compressor;
  boolean compressor_flag;

  public CompressorClass() {
    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    SmartDashboard.putBoolean("Compressor flag", false);
  }

  public void switch_compressor() {
    if (compressor_flag) {
      compressor.enableDigital();
    } else {
      compressor.disable();
    }
  }

  @Override
  public void periodic() {
    compressor_flag = SmartDashboard.getBoolean("Compressor flag", false);
    switch_compressor();
  }
}
