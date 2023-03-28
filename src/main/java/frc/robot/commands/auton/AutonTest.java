package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


//Commands
import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistanceRotations;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonTest extends SequentialCommandGroup{

    public AutonTest(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem){
        addCommands(
            new SequentialCommandGroup(
                //Slightly better auton:
                //drop wrist
                //close claw
                //move forward slightly
                //open claw
                //back up
                //rotate(maybe)
                new InstantCommand(clawSubsystem::wristDown, clawSubsystem),
                new WaitCommand(1),
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(1),
                
                new DriveDistanceRotations(15, 0.4, chassisSubsystem),
                new WaitCommand(1),
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new WaitCommand(0.5),

                new DriveDistanceRotations(20, -0.6, chassisSubsystem)










                //Much better auton:
                //keep wrist up
                //close claw
                //wrist down
                //arm up
                //move forward slowly
                //open claw
                //back up slowly
                //lower arm
                //backup
                //rotate towards nearest piece
                //forward
                //close claw
                //backward
                //rotate towards goal
                //forward
                
            )
        );
    }
    
}
