// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Subsystems.*;
import frc.robot.Commands.*;

public class RobotContainer {
  private Drivetrain m_drive = new Drivetrain();
  private Crusher m_crusher = new Crusher();

  private XboxController controller = new XboxController(0);

  JoystickButton a = new JoystickButton(controller, XboxController.Button.kA.value);

  public RobotContainer() {
    configureBindings();

    m_drive.setDefaultCommand(new RunCommand(() -> m_drive.drive(controller.getLeftX(), controller.getLeftY()), m_drive));
    m_crusher.setDefaultCommand(new InstantCommand(() -> {
      if(m_crusher.getCanSensor()){
        new WaitToCrush(m_crusher).schedule();
      } else {
        new PrintCommand("IDLE");
      }
    }, m_crusher));
  }

  private void configureBindings() {
    a.onTrue(new InstantCommand(() -> m_crusher.close(), m_crusher));
    a.onFalse(new InstantCommand(() -> m_crusher.open(), m_crusher));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
