package frc.robot.subsystems;

//Constants
import frc.robot.Constants.ClawConstants;

//Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//pneumatic stuff
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;



import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {
    
    Compressor m_compressor = new Compressor(ClawConstants.kModuleID, PneumaticsModuleType.CTREPCM); //probably useless but whatever
    Solenoid m_claw = new Solenoid(ClawConstants.kModuleID, PneumaticsModuleType.CTREPCM, ClawConstants.kPistonChannel);
    

    public ClawSubsystem(){

    }

    public void toggleSolenoid(){
        m_claw.toggle();
    }



    //This is called every 20ms
    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Claw Acitvated", m_claw.get());
        SmartDashboard.putNumber("Pressure",m_compressor.getPressure());
    }

}