// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Crusher extends SubsystemBase {
  /** Creates a new Crusher. */
  private final DoubleSolenoid sol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  private final DigitalInput hatchSwitch = new DigitalInput(0);

  public Crusher() {
    sol.set(Value.kForward);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void close(){
    if(getHatchClosed()){
       sol.set(Value.kReverse);
    }
  }

  public void open(){
    sol.set(Value.kForward);
  }

  public boolean getHatchClosed(){
    return !hatchSwitch.get();
  }
}
