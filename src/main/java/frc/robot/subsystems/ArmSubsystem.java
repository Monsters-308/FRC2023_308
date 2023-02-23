package frc.robot.subsystems;

//Motor stuff
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; //just in case we have 2 motors for the arm

//Constants
import frc.robot.Constants.ArmConstants;

//Potentiometer
import edu.wpi.first.wpilibj.AnalogPotentiometer;

//Shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    
    private final CANSparkMax m_armMotor = new CANSparkMax(ArmConstants.kMotorPort, MotorType.kBrushed);

    // Ask build team about potentiometer range.
    // When not given a range, it will return the voltage
    // Forget voltages, I'm just gonna use angles so i don't have to deal with long decimels
    private AnalogPotentiometer pot = new AnalogPotentiometer(ArmConstants.kPotPort , 333, 0);

    private double gravityOffset = 0; //this represents the motor speed required to stop the 

    private double desiredAngle = 2;


    public ArmSubsystem(){
        m_armMotor.restoreFactoryDefaults();

        m_armMotor.setSmartCurrentLimit(ArmConstants.kCurrentLimit);

        m_armMotor.setIdleMode(IdleMode.kBrake);
        
        m_armMotor.setInverted(false);

    }
    

    //USE THIS INSTEAF OF .set() to prevent arm from breaking
    private void setSafe(double speed){
        if(pot.get() > ArmConstants.kMaxAngle){
            m_armMotor.set(0.1); //setting it to zero will make it fall too quickly
        }
        else if((pot.get() < ArmConstants.kMinAngle) && (speed < 0)){
            m_armMotor.set(0);
        }
        else{
            m_armMotor.set(speed);
        }
    }
    

    //We COULD just set the motor speed to the offset and let it reach its maximum that way, but that will be slow
    //By having a separate speed, we can let the arm quickly go to the level it needs to go to and then stabilize.
    public void gotoAngle(double degrees, double speed, double offset){
        desiredAngle = degrees;

        while((pot.get() > degrees+ArmConstants.kAngleTolerance) || (pot.get() < degrees-ArmConstants.kAngleTolerance)){
            
            if(pot.get() > degrees+ArmConstants.kAngleTolerance){
                setSafe(-speed);
            }
            else if(pot.get() < degrees-ArmConstants.kAngleTolerance){
                setSafe(speed);
            }
            
        }
        setSafe(offset);
        gravityOffset = offset;
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
        desiredAngle += 0.5;
        if (desiredAngle > ArmConstants.kMaxAngle){
            desiredAngle = ArmConstants.kMaxAngle;
        }
    }

    public void down(){
        desiredAngle -= 0.5;
        if (desiredAngle < 0){
            desiredAngle = 0;
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

        if(getCurrentCommand() == null){
            //while((pot.get() > desiredAngle+1) || (pot.get() < desiredAngle-1)){
            if(pot.get() > desiredAngle+1){
                gravityOffset -= 0.02;
            }
            else if(pot.get() < desiredAngle-1){
                gravityOffset += 0.02;
            }
        }
        setSafe(gravityOffset);

        SmartDashboard.putNumber("Gravity Offset", gravityOffset); 

        SmartDashboard.putNumber("Desired angle", desiredAngle); 

        SmartDashboard.putNumber("pot position", pot.get());


    }

}
