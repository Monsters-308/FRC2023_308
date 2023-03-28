/*This is a template for how a command should be laid out so we don't lose 
our minds trying to remember the sintax.
In the future we can just duplicate this file and remove this comment.
*/

package frc.robot.commands;

//Import subsystem(s) this command interacts with below
import frc.robot.subsystems.SubsystemTemplate; //example

//Import any other files below here:
import edu.wpi.first.wpilibj2.command.Command;



//Import this so you can make this class a command
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CommandTemplate extends CommandBase {

    //Import any instance variables that are passed into the file below here, such as the subsystem(s) your command interacts with.
    private final SubsystemTemplate m_subsystem; //example

    //If you want to contoll whether or not the command has ended, you should store it in some sort of variable:
    private boolean m_complete = false;

    //Class Constructor
    public CommandTemplate(SubsystemTemplate subsystem){
        m_subsystem = subsystem;
        
        //If your command interacts with any subsystem(s), you should pass them into "addRequirements()"
        //This function makes it so your command will only run once these subsystem(s) are free from other commands.
        //This is really important as it will stop scenarios where two commands try to controll a motor at the same time.
        addRequirements(m_subsystem);
    }



    /*Like Robot.java, there are a series of functions that you can override to give the command functionality. */
    

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



    /*In addition to the four lifecycle methods described above, each Command also has some properties defined by getter methods.*/



    /*This function tells the robot whether or not the command can run if the robot is disabled.
     * For our purposes, we should probably never change this.
     */
    //When not overridden, this function returns false.
    @Override
    public boolean runsWhenDisabled(){
        return false;
    }

    /*This function allows you to specify what a command does when another command that uses the same subsystem is scheduled.
     * The default behavior, Command.InterruptBehavior.kCancelSelf, will make the command cancel itself so the other command can run.
     * You can also return Command.InterruptBehavior.kCancelIncoming, which will cancel the incoming command.
     * You can also specify the interruption behavior for any command by appending it with ".withInterruptBehavior()", which makes this
     *  function kind of useless :/
     */
    //When not overridden, this function returns Command.InterruptionBehavior.kCancelSelf
    @Override
    public Command.InterruptionBehavior getInterruptionBehavior(){
        return Command.InterruptionBehavior.kCancelSelf;
    }


}
