package frc.robot.commands.vision;

import frc.robot.subsystems.ChassisSubsystem;

//Import subsystem(s) this command interacts with below

import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.LEDSubsystem.LEDState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.LEDSubsystem;

import frc.robot.Constants.VisionConstants;

//Import this so you can make this class a command
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoAlignTop extends CommandBase {

    //Import any instance variables that are passed into the file below here, such as the subsystem(s) your command interacts with.
    final VisionSubsystem m_visionSubsystem;
    final ChassisSubsystem m_chassisSubsystem;
    final LEDSubsystem m_ledSubsystem;


    //If you want to contoll whether or not the command has ended, you should store it in some sort of variable:
    private boolean m_complete = false;

    //Class Constructor
    public AutoAlignTop(VisionSubsystem visionSubsystem, ChassisSubsystem chassisSubsystem, LEDSubsystem ledSubsystem){
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
        m_chassisSubsystem.setBrakeMode();
        m_complete = false;
    }

    /*This function is called repeatedly when the schedueler's "run()" function is called.
     * Once you want the function to end, you should set m_complete to true.
     */
    @Override
    public void execute(){
        double y = m_visionSubsystem.getY();
        double x = m_visionSubsystem.getX();
        double targets = m_visionSubsystem.getTV();
        double forwardSpeed = 0;
        double rotation = 0;
        //SmartDashboard.putNumber("motor speed align targets", x);

        if ((targets == 0) || (y < 0)){
            rotation = 0;
            forwardSpeed = 0;
            m_ledSubsystem.changeLEDState(LEDState.YELLOW);
        }

        //if targets 
        else{
            //Rotate so target is in center
            if (x+1 > VisionConstants.kRotationTolerance){
                rotation = VisionConstants.kRotationSpeed;//.6
            }
            else if (x+1 < -VisionConstants.kRotationTolerance){
                rotation = -VisionConstants.kRotationSpeed;
            }

            //Move forwards/backwards
            double distanceFromTarget = m_visionSubsystem.getDistance();

            //P controller for distance (fred)
            //forwardSpeed = (currentPosition - desiredPosition) * Pconstant
            if ((distanceFromTarget < VisionConstants.kTopPoleDesiredDistance - VisionConstants.kDistanceTolerance) 
            || (distanceFromTarget > VisionConstants.kTopPoleDesiredDistance + VisionConstants.kDistanceTolerance)){
                forwardSpeed = (m_visionSubsystem.getDistance() - VisionConstants.kTopPoleDesiredDistance) * VisionConstants.kForwardSpeedPConstant;
            }
            else{
                forwardSpeed = 0;
            }

            //Change LED state
            if(((distanceFromTarget > 54.5) && (distanceFromTarget < 56.5))&&((x+1 < VisionConstants.kRotationTolerance)&&(x+1 > -VisionConstants.kRotationTolerance))){
                m_ledSubsystem.changeLEDState(LEDState.GREEN);
            }
            else{
                m_ledSubsystem.changeLEDState(LEDState.RED);
            }
        }
        //bottom level
            /*
            * For now we're only going to do the top level. In the future we should make a separate file for auto aligning to the lower pole and have 
            * it on a separate button press. - Noah
            */

            //Rotate so target is in center
            /*if (x+1 > 1){
                rotation = VisionConstants.kRotationSpeed;//.6
            }
            else if (x+1 < -1){
                rotation = -VisionConstants.kRotationSpeed;
            }

            //Move forwards/backwards
            double distanceFromTarget = m_visionSubsystem.getDistance();
            if (distanceFromTarget < 40){
                //forwardSpeed = -VisionConstants.kForwardSpeed; //.8
                forwardSpeed = (m_visionSubsystem.getDistance() - 40) * 0.2;
            }

            else if (distanceFromTarget > 43){
                //forwardSpeed = VisionConstants.kForwardSpeed;
                forwardSpeed = (m_visionSubsystem.getDistance() - 40) * 0.2;
            }

            //Change LED state
            if(((distanceFromTarget > 39) && (distanceFromTarget < 43))){
                m_ledSubsystem.changeLEDState(LEDState.GREEN);
            }
            else{
                m_ledSubsystem.changeLEDState(LEDState.RED);
            }*/

        SmartDashboard.putNumber("motor speed align forwardspeed", forwardSpeed);
        SmartDashboard.putNumber("motor speed align rotation", rotation);

        if (forwardSpeed > VisionConstants.kMaxForwardSpeed){
            forwardSpeed = VisionConstants.kMaxForwardSpeed;
        }
        else if (forwardSpeed < -VisionConstants.kMaxForwardSpeed){
            forwardSpeed = -VisionConstants.kMaxForwardSpeed;
        }
        
        //m_chassisSubsystem.drive(forwardSpeed, rotation);
        m_chassisSubsystem.drive(0, rotation);
    }

    /*This function is called once when the command ends.
     * A command ends either when you tell it to end with the "isFinished()" function below, or when it is interupted.
     * Whether a command is interrupted or not is determined by "boolean interrupted."
     * Things initialized in "initialize()" should be closed here.
     */
    @Override
    public void end(boolean interrupted){
        m_chassisSubsystem.drive(0, 0);
        m_ledSubsystem.changeLEDState(LEDState.RAINBOW);
    }

    @Override
    public boolean isFinished(){
        return m_complete;
    }
}