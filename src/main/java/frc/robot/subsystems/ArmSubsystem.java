package frc.robot.subsystems;

//Motor Libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//Constants
import frc.robot.Constants.ArmConstants;

//Potentiometer
import edu.wpi.first.wpilibj.AnalogPotentiometer;

//Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    
    private final CANSparkMax m_armMotor = new CANSparkMax(ArmConstants.kMotorPort, MotorType.kBrushed);

    // Set with angles so we don't have to deal with long decimels
    private AnalogPotentiometer pot = new AnalogPotentiometer(ArmConstants.kPotPort , 333, -92);
    private double desiredAngle = 5;

    public ArmSubsystem(){
        m_armMotor.restoreFactoryDefaults();
        m_armMotor.setSmartCurrentLimit(ArmConstants.kCurrentLimit);
        m_armMotor.setIdleMode(IdleMode.kBrake);
        m_armMotor.setInverted(false);
        setSafe(-0.2);
    }
    

    //This helps ensure the arm safely lowers and raises to prevent itself from breaking
    private void setSafe(double speed){
        if(pot.get() > ArmConstants.kMaxAngle){
            m_armMotor.set(0.2); //setting it to zero will make it fall too quickly
        }
        else if(speed < -0.5){
            m_armMotor.set(-0.5);
        }
        /*else if((pot.get() < ArmConstants.kMinAngle) && (speed < 0)){
            m_armMotor.set(0);
        }*/
        else{
            m_armMotor.set(speed);
        }
    }

    //We COULD just set the motor speed to the offset and let it reach its maximum that way, but that will be slow
    //By having a separate speed, we can let the arm quickly go to the level it needs to go to and then stabilize.
    public void gotoAngle(double degrees, double speed, double offset){
        while((pot.get() > degrees+ArmConstants.kAngleTolerance) || (pot.get() < degrees-ArmConstants.kAngleTolerance)){
            
            if(pot.get() > degrees+ArmConstants.kAngleTolerance){
                setSafe(-0.3);
            }
            else if(pot.get() < degrees-ArmConstants.kAngleTolerance){
                setSafe(speed);
            }  
        }
        setSafe(offset);
    }

    //I'll probably have to modify this if Mr. Mallot adds his spacesensor thingy
    public void bottomLevel(){
        gotoAngle(ArmConstants.kBottomPosition, ArmConstants.kBottomSpeed, ArmConstants.kBottomOffset);
    }

    public void middleLevel(){
        gotoAngle(ArmConstants.kMiddlePosition, ArmConstants.kMiddleSpeed, ArmConstants.kMiddleOffset);
    }

    public void topLevel(){
        gotoAngle(ArmConstants.kTopPosition, ArmConstants.kTopSpeed, ArmConstants.kTopOffset);
    }

    //This might not be necessary
    public void loadingLevel(){
        gotoAngle(ArmConstants.kLoadingPosition, ArmConstants.kLoadingSpeed, ArmConstants.kLoadingOffset);
    }

    //These three are mainly for debugging purposes rn
    public void up(){
        m_armMotor.setIdleMode(IdleMode.kCoast);
        setSafe(0.8);
    }

    public void down(){
        m_armMotor.setIdleMode(IdleMode.kCoast);
        setSafe(-0.3);
    }

    public void stop(){
        m_armMotor.setIdleMode(IdleMode.kBrake);
        if(pot.get() < 10){
            setSafe(-0.2);  
        }
        else{
            setSafe(0.2);            
        }
    }

    //This is called every 20ms
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Desired angle", desiredAngle);
        SmartDashboard.putNumber("pot position", pot.get());
    }

}
