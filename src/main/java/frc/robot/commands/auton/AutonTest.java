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
            new SequentialCommandGroup(
                new AutoTurn(chassisSubsystem, 30, 0.6)
            )
        );
    }
    
}
