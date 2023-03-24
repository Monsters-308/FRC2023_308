package frc.robot.commands.chassis;

//Subsystem
import frc.robot.subsystems.ChassisSubsystem; 

//Constants
import frc.robot.Constants.ChassisConstants;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoTurnDegrees extends CommandBase {
    private final ChassisSubsystem m_drive; 
    private double m_degrees; //Positive should be clockwise, negative should be counterclockwise.
    private double m_speed;
    //private double startingRotation; //just in case resetEncoders() causes any issues
    private boolean m_complete = false;

    //Class Constructor
    public AutoTurnDegrees(ChassisSubsystem subsystem, double degrees, double speed){
        m_drive = subsystem;
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

    @Override
    public void initialize(){
        m_complete = false;
        m_drive.setBrakeMode();
        m_drive.resetEncoders();
    }

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

    @Override
    public void end(boolean interrupted){
        m_drive.drive(0.0, 0.0);
    }

    @Override
    public boolean isFinished(){
        return m_complete;
    }
}