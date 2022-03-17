package org.frcteam2910.c2020.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class CompressorClass implements Subsystem {

  Compressor compressor;

  public CompressorClass() {
    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    compressor.enableDigital();
  }
}
