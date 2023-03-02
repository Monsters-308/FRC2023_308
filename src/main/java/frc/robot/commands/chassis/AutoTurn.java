package frc.robot.commands.chassis;

//Import subsystem(s) this command interacts with below
import frc.robot.subsystems.ChassisSubsystem; 

//NAVX
import com.kauailabs.navx.frc.AHRS;

//Constants
import frc.robot.Constants.ChassisConstants;

//Import this so you can make this class a command
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoTurn extends CommandBase {

    //Import any instance variables that are passed into the file below here, such as the subsystem(s) your command interacts with.
    private final ChassisSubsystem m_drive; 

    //private AHRS NavX2;

    private double m_degrees; //Positive is clockwise, negative is counterclockwise.

    private double m_speed;


    //private double startingRotation; //just in case resetEncoders() causes any issues

    private boolean m_complete = false;

    //Class Constructor
    public AutoTurn(ChassisSubsystem subsystem, /*AHRS Nav,*/ double degrees, double speed){
        m_drive = subsystem;
        //NavX2 = Nav;
        m_degrees = degrees;
        m_speed = speed;

        addRequirements(m_drive);
    }


    /*
    There are 2 ways we can do this: algebraicly using the encoders, or by using the roll of the NavX.
    I want to do it the former way since i don't trust the NavX.

    Equation for rotating 360 degrees:
    circumference of robot / circumference of wheels = number of rotations for each wheel 
    (diameter of robot * pi) / (diameter of wheels * pi) = number of rotations for each wheel
    */

    /*This function is called once when the command is schedueled.
     * If you are overriding "isFinished()", you should probably use this to set m_complete to false in case a command object is 
     * called a second time.
     */
    //When not overridden, this function is blank.
    @Override
    public void initialize(){
        m_complete = false;
        m_drive.setBrakeMode();
        m_drive.resetEncoders();
    }


    /*This function is called repeatedly when the schedueler's "run()" function is called.
     * Once you want the function to end, you should set m_complete to true.
     */
    //When not overridden, this function is blank.
    @Override
    public void execute(){
        if((m_degrees > 0) && (m_drive.getAverageEncoderRotation() < m_degrees*ChassisConstants.kEndcoderRotationConversionFactor)){
            m_drive.drive(0.0, -m_speed);
        }
        else if((m_degrees < 0) && (m_drive.getAverageEncoderRotation() > m_degrees*ChassisConstants.kEndcoderRotationConversionFactor)){
            m_drive.drive(0.0, m_speed);
        }
        else{
            m_drive.drive(0.0, 0.0);
            m_complete = true;
        }
    }


    /*This function is called once when the command ends.
     * A command ends either when you tell it to end with the "isFinished()" function below, or when it is interupted.
     * Whether a command is interrupted or not is determined by "boolean interrupted."
     * Things initialized in "initialize()" should be closed here.
     */
    //When not overridden, this function is blank.
    @Override
    public void end(boolean interrupted){
        m_drive.drive(0.0, 0.0);
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
