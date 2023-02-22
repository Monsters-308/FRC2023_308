package frc.robot.commands.chassis;

import frc.robot.subsystems.ChassisSubsystem;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDistance extends CommandBase{
    private final ChassisSubsystem m_drive;
    private double m_distance;
    private double m_speed;
    private double start_encoders;
    private boolean m_complete = false;
    
    //inches must be positive; if you want to go in reverse, set speed to negative number.
    public DriveDistance(double inches, double speed, ChassisSubsystem subsystem){
        m_distance = inches;
        m_speed = speed;
        m_drive = subsystem;
        addRequirements(m_drive);
    }

    @Override
    public void initialize(){
        m_complete = false;
        start_encoders = m_drive.getAverageEncoderDistanceInches();
    }

    @Override
    public void execute(){
            if(Math.abs(m_drive.getAverageEncoderDistanceInches()-start_encoders)>=m_distance){
                m_drive.drive(0.0,0.0);
                m_complete = true;
            }
            else{
                m_drive.drive(m_speed,0.0);
            }
    
        // }else{
        //     if(Math.abs(m_drive.getAverageEncoderDistanceInches()-start_encoders)<=m_distance){
        //         m_drive.drive(0.0,0.0,0.0);
        //         m_complete = true;
        //     }else{
        //         m_drive.drive(m_speed,0.0,0.0);
        //     }
    
        // }
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
