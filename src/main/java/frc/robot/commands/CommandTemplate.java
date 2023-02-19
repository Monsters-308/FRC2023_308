/*This is a template for how a command should be laid out so we don't lose 
our minds trying to remember the sintax.
In the future we can just duplicate this file and remove this comment.
*/


package frc.robot.commands;

//Import any subsystems your robot interacts with below here
//Example below: 
import frc.robot.subsystems.ChassisSubsystem;


//Import any other files below here:


//Import this so you can make this class a command
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CommandTemplate extends CommandBase {

    //Import any instance variables that are passed into the file below here, such as the subsystem(s) your command interacts with.
    //Example below: 
    private final ChassisSubsystem m_drive;

    //If you want to contoll whether or not the command has ended, you should store it in some sort of variable:
    private boolean m_complete = false;

    //Class Constructor
    public CommandTemplate(ChassisSubsystem subsystem){
        m_drive = subsystem;
        
        //If your command interacts with any subsystem(s), you should pass them into "addRequirements()"
        //This function makes it so your command will only run once these subsystems are free from other commands.
        //This is really important as it will stop scenarios where two commands try to controll a motor at the same time.
        addRequirements(m_drive);
    }



    /*Like Robot.java, there are a series of functions that you can override to give the command functionality. */
    

    /*This function is called once when the command is schedueled.
     * If you are overriding "isFinished()", you should probably use this to set m_complete to false.
     */
    //When not overridden, this function is blank.
    @Override
    public void initialize(){
        
    }

    /*This function is called repeatedly when the schedueler's "run()" function is called.
     * Once you want the function to end, you should set m_complete to true.
     */
    //When not overridden, this function is blank.
    @Override
    public void execute(){
        m_complete = true;
    }

    /*This function is called once when the command ends.
     * A command ends either when you tell it to end with the "isFinished()" function below, or when it is interupted.
     * Whether a command is interrupted or not is determined by "boolean interrupted."
     * Things initialized in "initialize()" should be closed here.
     */
    //When not overridden, this function is blank.
    @Override
    public void end(boolean interrupted){
        
    }

    /*This function is called while the   */
    //When not overridden, this function returns false.
    @Override
    public boolean isFinished(){
        return m_complete;
    }

}
