// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// TODO: Change switch names (hatch → beam break) and possible implement hatch for safety.


package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.io.*;
import java.net.*;

public class Crusher extends SubsystemBase {
  /** Creates a new Crusher. */
  private final PneumaticsControlModule pcm = new PneumaticsControlModule(5);
  private final DoubleSolenoid sol = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  private final DigitalInput hatchSwitch = new DigitalInput(0);
  private DatagramSocket sock;
  byte[] sendData = new byte[256];
  private final int sendPort = 6023;
  private final String nano_hostname = "NucBoxG3.local";
  
  private DigitalOutput nantest = new DigitalOutput(6);

  public Crusher() {
    sol.set(Value.kForward);
    try{
      sock = new DatagramSocket(sendPort);
    } catch (SocketException se){
      System.out.println(se.getMessage());
    }
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
    nantest.set(false);
    sol.set(Value.kForward);
  }

  public void triggerNano(){
    try{
      InetAddress ip = InetAddress.getByName(nano_hostname);
      String requestValue = "0";
      sendData = requestValue.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, sendPort);
      sock.send(sendPacket);
    } catch (IOException ioe){
      System.out.println(ioe.getMessage());
    }
  }

  public boolean getHatchClosed(){
    return !hatchSwitch.get();
  }
}
