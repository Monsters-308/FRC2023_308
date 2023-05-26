package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.chassis.AutoTurn;
//Commands
import frc.robot.commands.chassis.DriveDistance;
import frc.robot.commands.arm.ArmGotoAngle;

//Constants
import frc.robot.Constants.ArmConstants;

//subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonTest extends SequentialCommandGroup{

    public AutonTest(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem){
        addCommands(
            /* 
            new SequentialCommandGroup(
                //Startup processes:
                //close claw
                new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),
                new WaitCommand(0.2),

                //move arm to high level and put wrist down
                new ParallelCommandGroup(
                    new ArmGotoAngle(80, 0.9, armSubsystem),
                    new WaitCommand(0.18).andThen(new InstantCommand(clawSubsystem::wristDown, clawSubsystem))
                ),
                new WaitCommand(0.3),
                new ArmGotoAngle(ArmConstants.kTopPositionCube, ArmConstants.kTopSpeed, armSubsystem)

                */
                new SequentialCommandGroup(
                    //Startup processes:
                    //close claw
                    new InstantCommand(clawSubsystem::closeClaw, clawSubsystem),

                    new AutoTurn(chassisSubsystem, 64, 0.8)


            )
        );
    }
    
}
