package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.chassis.DriveDistance;

import frc.robot.subsystems.ChassisSubsystem;

public class AutonTest extends SequentialCommandGroup{

    public AutonTest(ChassisSubsystem subsystem){
        addCommands(
            new DriveDistance(180, 0.5, subsystem)
        );
    }
    
}
