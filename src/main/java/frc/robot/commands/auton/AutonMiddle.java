/**
 * This is the new middle auton for livonia.
 * It consistently backs up over the charge station and out of the community, and then goes onto the charge pad and balances.
 * Robot must be started up against the loading zone.
 */
package frc.robot.commands.auton;

//Command libraries
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
//import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
//import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

//Commands
//import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistance;
import frc.robot.commands.chassis.AutoBalance;
import frc.robot.commands.chassis.BrakeDrive;
import com.kauailabs.navx.frc.AHRS;

//Subsystems
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ClawSubsystem;
import frc.robot.subsystems.ArmSubsystem;

public class AutonMiddle extends SequentialCommandGroup{
 
    public AutonMiddle(ChassisSubsystem chassisSubsystem, ClawSubsystem clawSubsystem, ArmSubsystem armSubsystem, AHRS ahrs){
        addCommands(
            new SequentialCommandGroup(
                //move backwards
                new DriveDistance(120, -0.8, chassisSubsystem),
                new WaitCommand(0.25),

                //move forwards
                new DriveDistance(55, 0.6, chassisSubsystem),
                new WaitCommand(0.25),

                //engage autobalance
                new RepeatCommand(
                    new AutoBalance(chassisSubsystem, ahrs, 1.6).withTimeout(0.7)
                    .andThen(
                    new BrakeDrive(chassisSubsystem,
                        () -> 0,
                        () -> 0).withTimeout(0.5)
                  )
                )
            )
        );
    }
    
}
