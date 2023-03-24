/*This is a template for how a command should be laid out so we don't lose 
our minds trying to remember the sintax.
In the future we can just duplicate this file and remove this comment.
*/

package frc.robot.commands.vision;

import frc.robot.subsystems.ChassisSubsystem;

//Import subsystem(s) this command interacts with below

import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.LEDSubsystem;


//Import this so you can make this class a command
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoAlign extends CommandBase {

    //Import any instance variables that are passed into the file below here, such as the subsystem(s) your command interacts with.
    final VisionSubsystem m_visionSubsystem;
    final ChassisSubsystem m_chassisSubsystem;
    final LEDSubsystem m_ledSubsystem;


    //If you want to contoll whether or not the command has ended, you should store it in some sort of variable:
    private boolean m_complete = false;

    //Class Constructor
    public AutoAlign(VisionSubsystem visionSubsystem, ChassisSubsystem chassisSubsystem, LEDSubsystem ledSubsystem){
        m_chassisSubsystem = chassisSubsystem;
        m_visionSubsystem = visionSubsystem;
        m_ledSubsystem = ledSubsystem;
        
        //If your command interacts with any subsystem(s), you should pass them into "addRequirements()"
        //This function makes it so your command will only run once these subsystem(s) are free from other commands.
        //This is really important as it will stop scenarios where two commands try to controll a motor at the same time.
        addRequirements(m_visionSubsystem, m_chassisSubsystem);
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
        double x = m_visionSubsystem.getX();
        double targets = m_visionSubsystem.getTV();
        SmartDashboard.putNumber("SUSSY BAKA VALUE", x);
        double forwardSpeed = 0;
        double rotation = 0;

        if (targets == 0){
            rotation = 0;
            forwardSpeed = 0;
            SmartDashboard.putNumber("motor speed align", 0);
            m_ledSubsystem.changeLEDState(LEDState.YELLOW);
        }
        else{
            //Rotate so target is in center
            if (x > 2){
                rotation = .5;
                SmartDashboard.putNumber("motor speed align", .5);
            }
            else if (x < -2){
                rotation = -.5;
                SmartDashboard.putNumber("motor speed align", -.5);
            }

            //Move forwards/backwards
            double distanceFromTarget = m_visionSubsystem.getDistance();
            if (distanceFromTarget < 50){
                forwardSpeed = -.4;
                SmartDashboard.putNumber("motor speed align distance", -.4);
            }
            else if (distanceFromTarget > 60){
                forwardSpeed = .4;
                SmartDashboard.putNumber("motor speed align distance", .4);
            }

            //Change LED state
            if(((distanceFromTarget > 50) && (distanceFromTarget < 60)) && ((x < 2) && (x > -2))){
                m_ledSubsystem.changeLEDState(LEDState.GREEN);
            }
            else{
                m_ledSubsystem.changeLEDState(LEDState.RED);
            }
        }
        
        m_chassisSubsystem.drive(forwardSpeed, rotation);
    }

    /*This function is called once when the command ends.
     * A command ends either when you tell it to end with the "isFinished()" function below, or when it is interupted.
     * Whether a command is interrupted or not is determined by "boolean interrupted."
     * Things initialized in "initialize()" should be closed here.
     */
    //When not overridden, this function is blank.
    @Override
    public void end(boolean interrupted){
        m_ledSubsystem.changeLEDState(LEDState.RED);
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