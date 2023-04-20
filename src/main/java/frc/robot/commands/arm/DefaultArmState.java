package frc.robot.commands.arm;

//Subsystem
import frc.robot.subsystems.ArmSubsystem;

//Constants
import frc.robot.Constants.ArmConstants;
import java.util.function.DoubleSupplier;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultArmState extends CommandBase {
    private final ArmSubsystem m_armSubsystem;
    //private boolean m_complete = false;
    private boolean armIsDown;
    private final DoubleSupplier m_xSpeed;
    private final DoubleSupplier m_zRotation;

    //Class Constructor
    public DefaultArmState(ArmSubsystem subsystem, DoubleSupplier xSpeed, DoubleSupplier zRotation){
        m_armSubsystem = subsystem;
        m_xSpeed = xSpeed;
        m_zRotation = zRotation;

        addRequirements(m_armSubsystem);
    }

    @Override
    public void initialize(){
        m_armSubsystem.setBrake();
        armIsDown = m_armSubsystem.getPot()>20;
    }

    @Override
    public void execute(){
        if(armIsDown){
            if((Math.abs(m_xSpeed.getAsDouble()) > 0)||(Math.abs(m_zRotation.getAsDouble()) > 0)){
                m_armSubsystem.setSafe(-0.1);
            }
            else{
                m_armSubsystem.setSafe(0);
            }
        }
        else{
            m_armSubsystem.setSafe(0.3);
        }
    }

    //This function is called once when the command ends.
    @Override
    public void end(boolean interrupted){
        
    }
}