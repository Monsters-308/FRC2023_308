package frc.robot.subsystems;

//Motor stuff
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

//Constants
import frc.robot.Constants.ChassisConstants;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {
    
    private final CANSparkMax m_leftFront = new CANSparkMax(ChassisConstants.kLeftFrontPort, MotorType.kBrushless);
    private final CANSparkMax m_leftRear = new CANSparkMax(ChassisConstants.kLeftRearPort, MotorType.kBrushless);
    MotorControllerGroup m_left = new MotorControllerGroup(m_leftFront, m_leftRear);

    private final CANSparkMax m_rightFront = new CANSparkMax(ChassisConstants.kRightFrontPort, MotorType.kBrushless);
    private final CANSparkMax m_rightRear = new CANSparkMax(ChassisConstants.kRightRearPort, MotorType.kBrushless);
    MotorControllerGroup m_right = new MotorControllerGroup(m_rightFront, m_rightRear);
    

    //Get Encoders
    private RelativeEncoder m_leftFrontEncoder = m_leftFront.getEncoder();
    private RelativeEncoder m_rightFrontEncoder = m_rightFront.getEncoder();
    private RelativeEncoder m_rightRearEncoder = m_rightRear.getEncoder();
    private RelativeEncoder m_leftRearEncoder = m_leftRear.getEncoder();

    private final DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

    public ChassisSubsystem(){
        m_leftFront.restoreFactoryDefaults();
        m_rightFront.restoreFactoryDefaults();
        m_rightRear.restoreFactoryDefaults();
        m_leftRear.restoreFactoryDefaults();

        //Bruh why tf did Vex not have this feature?
        m_leftFront.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        m_rightFront.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        m_rightRear.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        m_leftRear.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        
        /*m_leftFront.setIdleMode(IdleMode.kCoast);
        m_rightFront.setIdleMode(IdleMode.kCoast);
        m_rightRear.setIdleMode(IdleMode.kCoast);
        m_leftRear.setIdleMode(IdleMode.kCoast);*/
        
        m_leftFront.setIdleMode(IdleMode.kBrake);
        m_rightFront.setIdleMode(IdleMode.kBrake);
        m_rightRear.setIdleMode(IdleMode.kBrake);
        m_leftRear.setIdleMode(IdleMode.kBrake);
        
        //Maybe make constants for this?
        m_leftFront.setInverted(true);
        m_leftRear.setInverted(true);
        m_rightFront.setInverted(false);
        m_rightRear.setInverted(false);
        
        //Make sure all encoders work in the same direction
        /*m_leftFrontEncoder.setInverted(false);
        m_leftRearEncoder.setInverted(false);
        m_rightFrontEncoder.setInverted(false);
        m_rightRearEncoder.setInverted(false);*/


    }

    public void setBrakeMode(){
        m_leftFront.setIdleMode(IdleMode.kBrake);
        m_rightFront.setIdleMode(IdleMode.kBrake);
        m_rightRear.setIdleMode(IdleMode.kBrake);
        m_leftRear.setIdleMode(IdleMode.kBrake);
    }

    public void setCoastMode(){
        m_leftFront.setIdleMode(IdleMode.kCoast);
        m_rightFront.setIdleMode(IdleMode.kCoast);
        m_rightRear.setIdleMode(IdleMode.kCoast);
        m_leftRear.setIdleMode(IdleMode.kCoast);
    }


    public void drive(double xSpeed, double zRotation){

        //Safety mode
        /*if(xSpeed>0.5){
            xSpeed = 0.5;
        }
        if(xSpeed<-0.5){
            xSpeed = -0.5;
        }

        if(zRotation>0.5){
            zRotation = 0.5;
        }
        if(zRotation<-0.5){
            zRotation = -0.5;
        }*/

        m_drive.arcadeDrive(xSpeed*.75, zRotation*.75);
    }

    public double getAverageEncoderDistanceInches(){
        return ChassisConstants.kEncoderConversionFactor * (m_leftFrontEncoder.getPosition()+m_rightFrontEncoder.getPosition())/2;
    }

    //Im pretty sure all of our encoders are running in the same direction, so change this probably
    public double getAverageEncoderPosition(){
        //return(m_leftFrontEncoder.getPosition() + ((-1.0)*m_rightFrontEncoder.getPosition())) / 2.0;
        return (m_leftFrontEncoder.getPosition() + m_leftRearEncoder.getPosition() + 
            m_rightFrontEncoder.getPosition() + m_rightRearEncoder.getPosition()) / 4;
    }

    public double getAverageEncoderRotation(){
        return (m_leftFrontEncoder.getPosition() + m_leftRearEncoder.getPosition() + 
           (-m_rightFrontEncoder.getPosition()) + (-m_rightRearEncoder.getPosition())) / 4;
    }


    //What purpose do these functions actually serve?
    /*public double getLeftFrontEncoder(){
        return m_leftFrontEncoder.getPosition();
    }
    public double getRightFrontEncoder(){
        return m_rightFrontEncoder.getPosition();
    }
    public double getRightRearEncoder(){
        return m_rightRearEncoder.getPosition();
    }
    public double getLeftRearEncoder(){
        return m_leftRearEncoder.getPosition();
    }*/


    public void resetEncoders(){
        m_leftFrontEncoder.setPosition(0.0);
        m_rightFrontEncoder.setPosition(0.0);
        m_rightRearEncoder.setPosition(0.0);
        m_leftRearEncoder.setPosition(0.0);
    }

    //This is called every 20ms
    @Override
    public void periodic(){
        SmartDashboard.putNumber("LF_Enc",m_leftFrontEncoder.getPosition());
        SmartDashboard.putNumber("RF_Enc",m_rightFrontEncoder.getPosition());
        SmartDashboard.putNumber("RR_Enc",m_rightRearEncoder.getPosition());
        SmartDashboard.putNumber("LR_Enc",m_leftRearEncoder.getPosition());
        SmartDashboard.putNumber("LF_Speed",m_leftFront.get());
        SmartDashboard.putNumber("RF_Speed",m_rightFront.get());
        SmartDashboard.putNumber("RR_Speed",m_rightRear.get());
        SmartDashboard.putNumber("LR_Speed",m_leftRear.get());
        SmartDashboard.putNumber("AveragePosition",this.getAverageEncoderPosition());

    }

}
