/*This is a template for how a command should be laid out so we don't lose 
our minds trying to remember the sintax.
In the future we can just duplicate this file and remove this comment.
*/


package frc.robot.commands;

//Import any subsystems your robot interacts with below here
//Example below: 
import frc.robot.subsystems.ChassisSubsystem;

//Import any other files below here


//Import this so you can make this class a command
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CommandTemplate extends CommandBase {
    //Import any instance variables that are passed into the file below here, such as the subsystem(s) your command interacts with.
    //Example below: 
    private final ChassisSubsystem m_drive;
    
    public CommandTemplate(ChassisSubsystem subsystem){
        m_drive = subsystem;
        
        //I'm still not entirely sure what this "addRequirements" thing does, but you should probably pass your subsystems into it.
        addRequirements(m_drive);
    }


    @Override
    public void execute(){
        
    }

}
