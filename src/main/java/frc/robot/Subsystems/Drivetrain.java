// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  /** Creates a new Drivetrain. */
  
  private final WPI_VictorSPX LD1 = new WPI_VictorSPX(0);
  private final WPI_VictorSPX LD2 = new WPI_VictorSPX(1);
  private final WPI_VictorSPX RD1 = new WPI_VictorSPX(2);
  private final WPI_VictorSPX RD2 = new WPI_VictorSPX(3);

  private final DifferentialDrive m_drive = new DifferentialDrive(LD1, RD1);

  public Drivetrain() {
    LD1.setInverted(true);
    LD2.setInverted(true);
    LD2.follow(LD1);
    RD2.follow(RD1);
    LD1.configOpenloopRamp(0.5);
    RD1.configOpenloopRamp(0.5);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double x, double y){
    m_drive.arcadeDrive(x, y);
  }

  public void stop(){
    LD1.set(0.0);
    RD1.set(0.0);
  }
}
