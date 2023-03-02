package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.chassis.AutoTurn;
//Commands
import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistanceRotations;
import frc.robot.commands.chassis.AutoTurn;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonSide extends SequentialCommandGroup{

    public AutonSide(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem){
        addCommands(

            new SequentialCommandGroup(
                new InstantCommand(clawSubsystem::wristDown, clawSubsystem),
                //wait
                new WaitCommand(1),
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                //wait
                new WaitCommand(1),
                new DriveDistanceRotations(114, -0.6, chassisSubsystem)
                //new AutoTurn(chassisSubsystem, -90, 0.5)
                
            )
        );
    }
    
}
