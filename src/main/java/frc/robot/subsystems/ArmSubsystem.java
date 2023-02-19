package frc.robot.subsystems;

//motor stuff
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; //just in case we have 2 motors for the arm

//Constants
import frc.robot.Constants.ArmConstants;

//Potentiometer
import edu.wpi.first.wpilibj.AnalogPotentiometer;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ArmSubsystem extends SubsystemBase {
    
    private final CANSparkMax m_armMotor = new CANSparkMax(ArmConstants.kMotorPort, MotorType.kBrushed);

    // Ask build team about potentiometer range.
    private AnalogPotentiometer pot = new AnalogPotentiometer(ArmConstants.kPotPort, 180, 0);


    public ArmSubsystem(){
        m_armMotor.restoreFactoryDefaults();

        m_armMotor.setSmartCurrentLimit(ArmConstants.kCurrentLimit);

        m_armMotor.setIdleMode(IdleMode.kBrake); //Ask marcus about this
        
        m_armMotor.setInverted(false);

    }
    
    

    public void gotoAngle(double degrees){
        while((pot.get() > degrees+ArmConstants.kAngleTolerance) || (pot.get() < degrees-ArmConstants.kAngleTolerance)){
            
            if(pot.get() > degrees+ArmConstants.kAngleTolerance){
                m_armMotor.set(-ArmConstants.kRotationSpeed);
            }
            else if(pot.get() < degrees-ArmConstants.kAngleTolerance){
                m_armMotor.set(ArmConstants.kRotationSpeed);
            }
        }
        m_armMotor.set(0);
    }


    //I'll probably have to modify this if Mr. Mallot adds his spacesensor thingy
    public void bottomLevel(){
        gotoAngle(ArmConstants.kBottomPosition);
    }

    public void middleLevel(){
        gotoAngle(ArmConstants.kMiddlePosition);
    }

    public void topLevel(){
        gotoAngle(ArmConstants.kTopPosition);
    }

    //This might not be necessary
    public void loadingLevel(){
        gotoAngle(ArmConstants.kLoadingPosition);
    }



    //This is called every 20ms
    @Override
    public void periodic(){
        SmartDashboard.putNumber("LF_Speed",m_armMotor.get());

        //This should scale the voltage so that it goes from 0-180 degrees
        System.out.println(pot.get());
    }

}
