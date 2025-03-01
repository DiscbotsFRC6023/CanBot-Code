// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class WaitToCrush extends SequentialCommandGroup {
  /** Creates a new WaitToCrush. */
  public WaitToCrush(Crusher m_crusher) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> m_crusher.close(), m_crusher),
      new InstantCommand(() -> m_crusher.triggerNano(), m_crusher),
      new WaitCommand(2.0),
      new InstantCommand(() -> m_crusher.open(), m_crusher)
    );
  }
}
