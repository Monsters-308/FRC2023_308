/*This is a template for how a command should be laid out so we don't lose 
our minds trying to remember the sintax.
In the future we can just duplicate this file and remove this comment.
*/

package frc.robot.commands.claw;

//Import subsystem(s) this command interacts with below
import frc.robot.subsystems.ClawSubsystem; 


//Import this so you can make this class a command
import edu.wpi.first.wpilibj2.command.CommandBase;

public class toggleClaw extends CommandBase {

    //Import any instance variables that are passed into the file below here, such as the subsystem(s) your command interacts with.
    private final ClawSubsystem m_clawSubsystem; //example

    //If you want to contoll whether or not the command has ended, you should store it in some sort of variable:
    private boolean m_complete = false;

    //Class Constructor
    public toggleClaw(ClawSubsystem subsystem){
        m_clawSubsystem = subsystem;
        
        //If your command interacts with any subsystem(s), you should pass them into "addRequirements()"
        //This function makes it so your command will only run once these subsystem(s) are free from other commands.
        //This is really important as it will stop scenarios where two commands try to controll a motor at the same time.
        addRequirements(m_clawSubsystem);
    }
    

    /*This function is called once when the command is schedueled.
     * If you are overriding "isFinished()", you should probably use this to set m_complete to false in case a command object is 
     * called a second time.
     */
    //When not overridden, this function is blank.
    @Override
    public void initialize(){
        m_complete = false;
    }

    /*This function is called repeatedly when the schedueler's "run()" function is called.
     * Once you want the function to end, you should set m_complete to true.
     */
    //When not overridden, this function is blank.
    @Override
    public void execute(){
        m_clawSubsystem.toggleClaw();
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

    /*This function is called while the command is running. It is called after each time the "execute()" function is ran.
     * Once this function returns true, "end(boolean interrupted)" is ran and the command ends.
     * This fuction is used to tell the robot when the command has ended.
     * It is recommended that you don't use this for commands that should run continuously, such as drive commands.
    */
    //When not overridden, this function returns false.
    @Override
    public boolean isFinished(){
        return m_complete;
    }

}
