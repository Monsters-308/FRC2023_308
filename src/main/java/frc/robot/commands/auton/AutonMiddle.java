package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


//Commands
import frc.robot.commands.chassis.DriveDistanceRotations;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonMiddle extends SequentialCommandGroup{
 
    public AutonMiddle(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem){
        addCommands(
            new SequentialCommandGroup(
                //new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new DriveDistanceRotations(76, -0.6, chassisSubsystem)
                
            )
        );
    }
    
}
