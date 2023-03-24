/**
 * This is the new middle auton for livonia.
 * It consistently backs up over the charge station and out of the community, and then goes onto the charge pad and balances.
 * Robot must be started up against the loading zone.
 */
package frc.robot.commands.auton;

//Command libraries
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

//Commands
//import frc.robot.commands.chassis.DriveDistanceInches;
import frc.robot.commands.chassis.DriveDistanceRotations;
import frc.robot.commands.chassis.AutoBalance;
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
                new DriveDistanceRotations(120, -0.8, chassisSubsystem),
                new WaitCommand(0.25),
                //move forwards
                new DriveDistanceRotations(55, 0.6, chassisSubsystem),
                new WaitCommand(0.25),
                //engage autobalance
                new RepeatCommand(
                    new AutoBalance(chassisSubsystem, ahrs, 0).withTimeout(0.5)
                    .andThen(new WaitCommand(0.5))
                    )
            )
        );
    }
    
}
