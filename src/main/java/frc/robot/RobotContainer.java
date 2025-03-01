// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Subsystems.*;
import frc.robot.Commands.*;

public class RobotContainer {
  final Drivetrain m_drive = new Drivetrain();
  final Crusher m_crusher = new Crusher();

  final XboxController controller = new XboxController(0);

  public RobotContainer() {
    configureBindings();

    m_drive.setDefaultCommand(new RunCommand(() -> m_drive.drive(controller.getLeftX() / 2, controller.getLeftY() / 2), m_drive));
    m_crusher.setDefaultCommand(new WaitToCrush(m_crusher));
  }

  private void configureBindings() {
    JoystickButton b = new JoystickButton(controller, XboxController.Button.kA.value);
    b.toggleOnTrue(new InstantCommand(() -> m_crusher.close(), m_crusher));
    b.toggleOnFalse(new InstantCommand(() -> m_crusher.open(), m_crusher));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
