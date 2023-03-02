package frc.robot.subsystems;

//Constants
import frc.robot.Constants.ClawConstants;

//Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Pneumatic Libraries
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsystem extends SubsystemBase {
    
    Compressor m_compressor = new Compressor(ClawConstants.kModuleID, PneumaticsModuleType.CTREPCM);
    Solenoid m_claw = new Solenoid(ClawConstants.kModuleID, PneumaticsModuleType.CTREPCM, ClawConstants.kClawPistonChannel);
    Solenoid m_wrist = new Solenoid(ClawConstants.kModuleID, PneumaticsModuleType.CTREPCM, ClawConstants.kWristPistonChannel);

    public ClawSubsystem(){ }

    public void openClaw(){
        m_claw.set(false);
    }
    public void closeClaw(){
        m_claw.set(true);
    }
    public void toggleClaw(){
        m_claw.toggle();
    }

    public void wristUp(){
        m_wrist.set(false);
    }
    public void wristDown(){
        m_wrist.set(true);
    }
    public void toggleWrist(){
        // What is this code trying to trying to do that is diffrent from the claw? - Marcus
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