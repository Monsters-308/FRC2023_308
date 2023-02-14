package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.ArmConstants;

//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup; //just in case we have 2 motors for the arm
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ArmSubsystem extends SubsystemBase {
    
    private final CANSparkMax m_armMotor = new CANSparkMax(ArmConstants.kMotorPort, MotorType.kBrushless);

    //Get Encoders
    private RelativeEncoder m_armEncoder = m_armMotor.getEncoder();

    public ArmSubsystem(){
        m_armMotor.restoreFactoryDefaults();

        //Bruh why tf did Vex not have this feature?
        m_armMotor.setSmartCurrentLimit(ArmConstants.kCurrentLimit);

        m_leftFront.setIdleMode(IdleMode.kCoast);
        m_rightFront.setIdleMode(IdleMode.kCoast);
        m_rightRear.setIdleMode(IdleMode.kCoast);
        m_leftRear.setIdleMode(IdleMode.kCoast);
        
        //Maybe make constants for this?
        m_leftFront.setInverted(false);
        m_leftRear.setInverted(false);
        m_rightFront.setInverted(true);
        m_rightRear.setInverted(true);


    }

    public void drive(double xSpeed, double zRotation){
        m_drive.arcadeDrive(xSpeed, zRotation);
    }

    public double getAverageEncoderDistanceInches(){
        return ChassisConstants.kEncoderConversionFactor * (m_leftFrontEncoder.getPosition()+m_rightFrontEncoder.getPosition())/2;
    }

    public double getAverageEcoderPosition(){
        return(m_leftFrontEncoder.getPosition() + ((-1.0)*m_rightFrontEncoder.getPosition())) / 2.0;
    }

    public double getLeftFrontEncoder(){
        return m_leftFrontEncoder.getPosition();
    }

    public void resetEncoders(){
        m_leftFrontEncoder.setPosition(0.0);
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
        SmartDashboard.putNumber("AveragePosition",this.getAverageEcoderPosition());
    }

}
