/**
 * This file will do the startup processes necessary for getting a hold of the piece that the robot starts with.
 * If auton commands can call other auton commands, then this command should be called at the start of all other auton commands.
 */

package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
//import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
//import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


//Commands
//import frc.robot.commands.chassis.DriveDistanceInches;
//import frc.robot.commands.chassis.DriveDistanceRotations;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonStartup extends SequentialCommandGroup{

    public AutonStartup(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem){
        addCommands(
            new SequentialCommandGroup(
                //Startup processes:
                //close claw
                //wrist down
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(1),
                new InstantCommand(clawSubsystem::wristDown, clawSubsystem),
                new WaitCommand(1)
            )
        );
    }
    
}
