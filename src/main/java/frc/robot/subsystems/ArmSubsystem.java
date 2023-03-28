package frc.robot.subsystems;

//Motor libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; //just in case we have 2 motors for the arm

//Constants
import frc.robot.Constants.ArmConstants;

//Potentiometer library
import edu.wpi.first.wpilibj.AnalogPotentiometer;

//Shuffleboard library
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    private final CANSparkMax m_armMotor = new CANSparkMax(ArmConstants.kMotorPort, MotorType.kBrushed);

    //Set with a range of angles so we don't have to deal with long decimels, even though the output won't be the same as real-world angles
    private AnalogPotentiometer pot = new AnalogPotentiometer(ArmConstants.kPotPort , 333, -92);

    //private double gravityOffset = 0; //this represents the motor speed required to stop the arm from falling down
    //private double desiredAngle = 5;


    public ArmSubsystem(){
        m_armMotor.restoreFactoryDefaults();
        m_armMotor.setSmartCurrentLimit(ArmConstants.kCurrentLimit);
        m_armMotor.setIdleMode(IdleMode.kBrake);
        m_armMotor.setInverted(false);
        setSafe(-0.2);
    }
    

    //USE THIS INSTEAF OF .set() to prevent arm from breaking
    private void setSafe(double speed){
        if(pot.get() > ArmConstants.kMaxAngle){
            m_armMotor.set(0.2); //setting it to zero will make it fall too quickly
        }
        else if(speed < -0.8){
            m_armMotor.set(-0.8);
        }
        else if(speed > 0.8){
            m_armMotor.set(0.8);
        }
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

        stop();
    }

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
    /*public void loadingLevel(){
        gotoAngle(ArmConstants.kLoadingPosition, ArmConstants.kLoadingSpeed, ArmConstants.kLoadingOffset);
    }*/


    //These three are used for manuel conroll of the arm
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
            // Setting the arm to 0.2 seems to be strong enough to stop the arm from falling back down,
            // so I'm thinking about no longer using a "gravity offset" parameter in the gotoAngle function.
            setSafe(0.2);            
        }
    }


    //This is called every 20ms
    @Override
    public void periodic(){
        //stabalization function
        /* Version1:
         *      There's a variable called "gravity offset" that keeps track of what speed the motor has to be at to stop it from falling
         *      There's a variable called "desired angle" that keeps track of what angle the arm SHOULD be at
         *      When the subsystem is not being used, Slowly adjust gravity Offset to go to the desired angle
         *      So basically it's a slower version of the "goto" function.
         */
        // UPDATE - this method does not work because when the arm reaches a resting point, it develops a large amount of 
        // static friction that can only be broken by increase the arm speed by a large amount, so gradual control of the arm
        // is simply not possible.

        //if(getCurrentCommand() == null){
            /*if(pot.get() > desiredAngle+5){
                gravityOffset -= 0.001;
            }
            else if((pot.get() < desiredAngle-5) && (gravityOffset < 0.6)){
                gravityOffset += 0.001;
            }*/
            //setSafe(gravityOffset);
        //}
        
        //SmartDashboard.putNumber("Gravity Offset", gravityOffset); 
        //SmartDashboard.putNumber("Desired angle", desiredAngle);
        SmartDashboard.putNumber("Motor speed", m_armMotor.get());  
        SmartDashboard.putNumber("pot position", pot.get());
    }
}
