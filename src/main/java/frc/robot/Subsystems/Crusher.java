// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
// TODO: Change switch names (hatch → beam break) and possible implement hatch for safety.


package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.io.*;
import java.net.*;

public class Crusher extends SubsystemBase {
  /** Creates a new Crusher. */
  private final PneumaticsControlModule pcm = new PneumaticsControlModule(5);
  private final DoubleSolenoid sol = pcm.makeDoubleSolenoid(0, 1);

  private final DigitalInput hatchSwitch = new DigitalInput(2);
  private final DigitalInput canSensor = new DigitalInput(0);

  private DatagramSocket sock;
  byte[] sendData;
  private final int sendPort = 5800;
  private final String nano_hostname = "10.60.23.5";
  
  public Crusher() {

    try{
      sock = new DatagramSocket(sendPort);
      sock.connect(InetAddress.getByName(nano_hostname), sendPort);
    } catch (SocketException se){
      System.out.println(se.getMessage());
    } catch (UnknownHostException uhe){
      System.out.println(uhe.getMessage());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    System.out.println();
    if(this.getCanSensor()){
      triggerNano();
    } else {
      idleNano();
    }
  }

  public void close(){
    sol.set(Value.kForward);
  }

  public void open(){
    sol.set(Value.kReverse);
  }

  private void sendMessage(String trig){
    try{
      InetAddress ip = InetAddress.getByName(nano_hostname);
      String requestValue = trig;
      sendData = requestValue.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, sendPort);
      sock.send(sendPacket);
    } catch (IOException ioe){
      System.out.println(ioe.getMessage());
    }
  }

  public void triggerNano(){
    sendMessage("start");
  }

  public void idleNano(){
    sendMessage("idle");
  }

  public boolean getHatchClosed(){
    return !hatchSwitch.get();
  }

  public boolean getCanSensor(){
    return canSensor.get();
  }
}
