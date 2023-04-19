package frc.robot.commands.chassis;

//Subsystem
import frc.robot.subsystems.ChassisSubsystem; 

//Constants
import frc.robot.Constants.ChassisConstants;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoTurn extends CommandBase {
    private final ChassisSubsystem m_drive; 
    private double m_rotations; //Positive should be clockwise, negative should be counterclockwise.
    private double m_speed;
    private double start_encoders;
    private boolean m_complete = false;

 
    //Speed must be positive; positive rotations is clockwise, negative rotations is counter-clockwise
    public AutoTurn(ChassisSubsystem subsystem, double rotations, double speed){
        m_drive = subsystem;
        m_rotations = rotations;
        m_speed = speed;

        addRequirements(m_drive);
    }
    
    public AutoTurn(ChassisSubsystem subsystem, double angle, double speed, boolean convertFromDegrees){
        if(convertFromDegrees){
            m_rotations = angle*ChassisConstants.kEndcoderRotationConversionFactor;
        }
        else{
            m_rotations = angle;
        }
        m_drive = subsystem;
        m_speed = speed;

        addRequirements(m_drive);
    }

    @Override
    public void initialize(){
        m_complete = false;
        m_drive.setBrakeMode();
        m_drive.resetEncoders();
        start_encoders = m_drive.getAverageEncoderPosition();
    }

    @Override
    public void execute(){
        if((m_rotations > 0) && (Math.abs(m_drive.getAverageEncoderPosition()-start_encoders) <= m_rotations)){
            m_drive.drive(0.0, m_speed);
        }
        else if((m_rotations < 0) && (Math.abs(m_drive.getAverageEncoderPosition()-start_encoders) >= m_rotations)){
            m_drive.drive(0.0, -m_speed);
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