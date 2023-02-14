// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DriverControl;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.Pivot;
import frc.robot.subsystems.claw.Claw;
import frc.robot.subsystems.claw.Pistons;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class RobotContainer {

  private final Drivetrain drivetrain = new Drivetrain();
  private final Arm arm = new Arm();
  private final Pivot pivot = new Pivot();
  private final Claw claw = new Claw();
  private final Pistons pistons = new Pistons();
  
  private final CommandXboxController mDriver =
      new CommandXboxController(0);
  private final CommandXboxController mOperator =
      new CommandXboxController(1);

  public RobotContainer() {
    
    configureBindings();
  }

  private void configureBindings() {

    CommandScheduler.getInstance().setDefaultCommand(drivetrain, 
      new DriverControl(drivetrain,
        () -> -mDriver.getLeftY(), 
        () -> mDriver.getRightX()));

      //Intake or Grab
      mOperator.a().whileTrue(
        new ParallelCommandGroup(
          new InstantCommand(
            pistons::extend,
            pistons
          ),
          new InstantCommand(
            claw::intake,
            claw
          ))
      ).whileFalse(
        new ParallelCommandGroup(
          new InstantCommand(
            pistons::retract,
            pistons
          ),
          new InstantCommand(
            claw::stop,
            claw
          ))
      );

      //Score
      mOperator.b().whileTrue(
        new ParallelCommandGroup(
          new InstantCommand(
            pistons::extend,
            pistons
          ),
          new InstantCommand(
            claw::outtake,
            claw
          ))
      ).whileFalse(
        new ParallelCommandGroup(
          new InstantCommand(
            pistons::retract,
            pistons
          ),
          new InstantCommand(
            claw::stop,
            claw
          ))
      );

      mOperator.leftTrigger().whileTrue(
        new InstantCommand(
          pivot::forward,
          pivot
        )
      ).whileFalse(
        new InstantCommand(
          pivot::stop,
          pivot
        )
      );

      mOperator.rightTrigger().whileTrue(
        new InstantCommand(
          pivot::backward,
          pivot
        )
      ).whileFalse(
        new InstantCommand(
          pivot::stop,
          pivot
        )
      );

      mOperator.x().whileTrue(
        new InstantCommand(
          arm::extend,
          arm
        )
      ).whileFalse(
        new InstantCommand(
          arm::stop,
          arm
        )
      );

      mOperator.y().whileTrue(
        new InstantCommand(
          arm::retract,
          arm
        )
      ).whileFalse(
        new InstantCommand(
          arm::stop,
          arm
        )
      );
  }

}
