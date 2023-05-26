package frc.robot.commands.chassis;

import frc.robot.subsystems.ChassisSubsystem;

import frc.robot.Constants.ChassisConstants;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDistance extends CommandBase{
    private final ChassisSubsystem m_drive;
    private double m_distance;
    private double m_speed;
    private double start_encoders;
    private boolean m_complete = false;
    
    //distance must be positive; if you want to go in reverse, set speed to negative number.
    public DriveDistance(double rotations, double speed, ChassisSubsystem subsystem){
        m_distance = rotations;
        m_speed = speed;
        m_drive = subsystem;
        addRequirements(m_drive);
    }

    public DriveDistance(double distance, double speed, ChassisSubsystem subsystem, boolean convertFromInches){
        if(convertFromInches){
            m_distance = distance*ChassisConstants.kInchesToRotationsConversionFactor;
        }
        else{
            m_distance = distance;
        }
        m_speed = speed;
        m_drive = subsystem;

        addRequirements(m_drive);
    }

    @Override
    public void initialize(){
        m_complete = false;

        m_drive.setBrakeMode();

        start_encoders = m_drive.getAverageEncoderPosition();
    }

    @Override
    public void execute(){
        if(Math.abs(m_drive.getAverageEncoderPosition()-start_encoders)>=m_distance){
            m_drive.drive(0.0,0.0);
            m_complete = true;
        }
        else{
            m_drive.drive(m_speed,0.0);
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
