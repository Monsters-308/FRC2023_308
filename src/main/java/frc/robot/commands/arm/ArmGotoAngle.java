package frc.robot.commands.arm;

//Subsystem
import frc.robot.subsystems.ArmSubsystem;

//Constants
import frc.robot.Constants.ArmConstants;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmGotoAngle extends CommandBase {
    private final ArmSubsystem m_armSubsystem;
    private boolean m_complete = false;
    private double m_speed;
    private double m_degrees;

    //Class Constructor
    public ArmGotoAngle(double degrees, double speed, ArmSubsystem subsystem){
        m_degrees = degrees;
        m_speed = speed;
        m_armSubsystem = subsystem;

        addRequirements(m_armSubsystem);
    }

    @Override
    public void initialize(){
        m_armSubsystem.setCoast();
        m_complete = false;
    }

    @Override
    public void execute(){
        if((m_armSubsystem.getPot() < m_degrees+ArmConstants.kAngleTolerance) && (m_armSubsystem.getPot() > m_degrees-ArmConstants.kAngleTolerance)){
            m_armSubsystem.stop();
            m_complete = true;
        }

        else if(m_armSubsystem.getPot() < m_degrees-ArmConstants.kAngleTolerance){
            m_armSubsystem.setSafe(m_speed);
        }
        
        else if(m_armSubsystem.getPot() > m_degrees+ArmConstants.kAngleTolerance){
            m_armSubsystem.setSafe(-0.15);
        }
    }

    //This function is called once when the command ends.
    @Override
    public void end(boolean interrupted){
        
    }

    //This fuction is used to tell the robot when the command has ended.
    @Override
    public boolean isFinished(){
        return m_complete;
    }
}