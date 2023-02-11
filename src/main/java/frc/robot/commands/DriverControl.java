package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriverControl extends CommandBase {
    
  private final Drivetrain mDrivetrain;
  private final DoubleSupplier mSpeed;
  private final DoubleSupplier mTurn;

  public DriverControl(Drivetrain drivetrain, DoubleSupplier speedInput, DoubleSupplier turnInput) {

    mDrivetrain = drivetrain;
    mSpeed = speedInput;
    mTurn = turnInput;  

    addRequirements(drivetrain);
  }

  @Override
  public void execute() {

    mDrivetrain.easyDrive(mSpeed.getAsDouble(), mTurn.getAsDouble());

  }

}