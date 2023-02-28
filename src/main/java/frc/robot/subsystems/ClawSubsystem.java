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
    Solenoid m_claw = new Solenoid(ClawConstants.kModuleID, PneumaticsModuleType.CTREPCM, ClawConstants.kClawPistonChannel);
    Solenoid m_wrist = new Solenoid(ClawConstants.kModuleID, PneumaticsModuleType.CTREPCM, ClawConstants.kWristPistonChannel);

    public ClawSubsystem(){
        //closeClaw();
    }


    public void toggleClaw(){
        m_claw.toggle();
    }
    public void openClaw(){
        m_claw.set(true);
    }
    public void closeClaw(){
        m_claw.set(false);
    }


    public void wristUp(){
        m_claw.set(true);
        m_wrist.set(false);
    }
    public void wristDown(){
        m_wrist.set(true);
    }
    public void toggleWrist(){
        if(m_wrist.get()){
            wristUp();
        }
        else{
            wristDown();
        }
    }




    //This is called every 20ms
    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Claw Acitvated", m_claw.get());
        SmartDashboard.putBoolean("Wrist Acitvated", m_wrist.get());
        SmartDashboard.putNumber("Pressure",m_compressor.getPressure());
    }

}