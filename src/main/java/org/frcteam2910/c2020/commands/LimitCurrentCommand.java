//   public void setSubsystemMotorsLimit() {

//     int[] neoIds = {21, 22, 31, 32, 41, 51, 52};
//     for (int i = 0; i < 7; i++) {
//       CANSparkMax canMotor = new CANSparkMax(neoIds[i], MotorType.kBrushless);
//       double voltage = canMotor.getBusVoltage();
//       SmartDashboard.putNumber(String.format("voltage on motor id # %d", i), voltage);

//       canMotor.enableVoltageCompensation(?);
//       canMotor.setSmartCurrentLimit(?);
//     }
//   }
