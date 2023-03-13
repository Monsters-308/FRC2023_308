package frc.robot.commands.auton;

//Command libraries
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

//Commands
//import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistanceRotations;

//Subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonMiddle extends SequentialCommandGroup{

    public AutonMiddle(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem){
        addCommands(
            new SequentialCommandGroup(

                //Puts wrist down, closes claw, moves forward, open claw, move backwards out of community.
                new InstantCommand(clawSubsystem::wristDown, clawSubsystem),
                new WaitCommand(1),
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(1),

                new DriveDistanceRotations(15, 0.4, chassisSubsystem),
                new WaitCommand(1),
                new InstantCommand(clawSubsystem::openClaw, clawSubsystem),
                new WaitCommand(0.5),

                new DriveDistanceRotations(76, -0.8, chassisSubsystem)
            )
        );
    }

}
