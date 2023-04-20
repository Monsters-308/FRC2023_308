package frc.robot.subsystems;

//Motor libraries
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

//Constants
import frc.robot.Constants.ChassisConstants;

//Drive train object
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ChassisSubsystem extends SubsystemBase {
    
    private final CANSparkMax m_leftFront = new CANSparkMax(ChassisConstants.kLeftFrontPort, MotorType.kBrushless);
    private final CANSparkMax m_leftRear1 = new CANSparkMax(ChassisConstants.kLeftRearPort1, MotorType.kBrushless);
    private final CANSparkMax m_leftRear2 = new CANSparkMax(ChassisConstants.kLeftRearPort2, MotorType.kBrushless);
    MotorControllerGroup m_left = new MotorControllerGroup(m_leftFront, m_leftRear1, m_leftRear2);

    private final CANSparkMax m_rightFront = new CANSparkMax(ChassisConstants.kRightFrontPort, MotorType.kBrushless);
    private final CANSparkMax m_rightRear1 = new CANSparkMax(ChassisConstants.kRightRearPort1, MotorType.kBrushless);
    private final CANSparkMax m_rightRear2 = new CANSparkMax(ChassisConstants.kRightRearPort2, MotorType.kBrushless);
    MotorControllerGroup m_right = new MotorControllerGroup(m_rightFront, m_rightRear1, m_rightRear2);

    //Get Encoders
    private RelativeEncoder m_leftFrontEncoder = m_leftFront.getEncoder();
    private RelativeEncoder m_rightFrontEncoder = m_rightFront.getEncoder();
    private RelativeEncoder m_rightRearEncoder1 = m_rightRear1.getEncoder();
    private RelativeEncoder m_rightRearEncoder2 = m_rightRear2.getEncoder();
    private RelativeEncoder m_leftRearEncoder1 = m_leftRear1.getEncoder();
    private RelativeEncoder m_leftRearEncoder2 = m_leftRear2.getEncoder();

    private final DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);


    public ChassisSubsystem(){
        m_leftFront.restoreFactoryDefaults();
        m_rightFront.restoreFactoryDefaults();
        m_rightRear1.restoreFactoryDefaults();
        m_rightRear2.restoreFactoryDefaults();
        m_leftRear1.restoreFactoryDefaults();
        m_leftRear2.restoreFactoryDefaults();

        m_leftFront.setOpenLoopRampRate(0.25);
        m_rightFront.setOpenLoopRampRate(0.25);
        m_rightRear1.setOpenLoopRampRate(0.25);
        m_rightRear2.setOpenLoopRampRate(0.25);
        m_leftRear1.setOpenLoopRampRate(0.25);
        m_leftRear2.setOpenLoopRampRate(0.25);

        m_leftFront.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        m_rightFront.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        m_rightRear1.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        m_rightRear2.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        m_leftRear1.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        m_leftRear2.setSmartCurrentLimit(ChassisConstants.kCurrentLimit);
        
        m_leftFront.setIdleMode(IdleMode.kCoast);
        m_rightFront.setIdleMode(IdleMode.kCoast);
        m_rightRear1.setIdleMode(IdleMode.kCoast);
        m_rightRear2.setIdleMode(IdleMode.kCoast);
        m_leftRear1.setIdleMode(IdleMode.kCoast);
        m_leftRear2.setIdleMode(IdleMode.kCoast);
        
        m_leftFront.setInverted(false);
        m_rightFront.setInverted(true);
        m_leftRear1.setInverted(false);
        m_leftRear2.setInverted(false);
        m_rightRear1.setInverted(true);
        m_rightRear2.setInverted(true);

        m_leftFront.burnFlash();
        m_rightFront.burnFlash();
        m_leftRear1.burnFlash();
        m_leftRear2.burnFlash();
        m_rightRear1.burnFlash();
        m_rightRear2.burnFlash();

        resetEncoders();
    }

    public void setBrakeMode(){
        m_leftFront.setIdleMode(IdleMode.kBrake);
        m_rightFront.setIdleMode(IdleMode.kBrake);
        m_rightRear1.setIdleMode(IdleMode.kBrake);
        m_rightRear2.setIdleMode(IdleMode.kBrake);
        m_leftRear1.setIdleMode(IdleMode.kBrake);
        m_leftRear2.setIdleMode(IdleMode.kBrake);
    }

    public void setCoastMode(){
        m_leftFront.setIdleMode(IdleMode.kCoast);
        m_rightFront.setIdleMode(IdleMode.kCoast);
        m_rightRear1.setIdleMode(IdleMode.kCoast);
        m_rightRear2.setIdleMode(IdleMode.kCoast);
        m_leftRear1.setIdleMode(IdleMode.kCoast);
        m_leftRear2.setIdleMode(IdleMode.kCoast);
    }

    //Called by autonomous commands
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
        
        //Drift offset code
        // if(xSpeed>0){
        //     zRotation += SmartDashboard.getNumber("driftOffset", 0);
        // }
        // else if (xSpeed<0){
        //     zRotation -= SmartDashboard.getNumber("driftOffset", 0);
        // }


        m_drive.arcadeDrive(xSpeed*.60, -zRotation * .60);
    }

    //Called by Default drive
    public void drive(double xSpeed, double zRotation, boolean turbo){
        if(turbo){
            m_drive.arcadeDrive(xSpeed, -zRotation);
        }
        else{
            m_drive.arcadeDrive(xSpeed*.8, -zRotation * .8);
        }
    }

    //Returns the average encoder rotation of the 6 encoders
    public double getAverageEncoderPosition(){
        return (m_leftFrontEncoder.getPosition() + m_leftRearEncoder1.getPosition() + m_leftRearEncoder2.getPosition() + 
        m_rightFrontEncoder.getPosition() + m_rightRearEncoder1.getPosition() + m_rightRearEncoder2.getPosition()) / 6;
    }

    //Returns the average encoder rotation of the 6 encoders converted to inches
    public double getAverageEncoderDistanceInches(){
        return getAverageEncoderPosition() / ChassisConstants.kInchesToRotationsConversionFactor;
    }

    //Returns the average rotation of the robot by subtracting the average of the right side from the average of the left side
    public double getAverageEncoderRotation(){
        return (
            ((m_leftFrontEncoder.getPosition() + m_leftRearEncoder1.getPosition() + m_leftRearEncoder2.getPosition())/3) - 
            ((m_rightFrontEncoder.getPosition() + m_rightRearEncoder1.getPosition() + m_rightRearEncoder2.getPosition())/3)
            );
    }

    //Might cause DifferentialDrive errors if called
    public void resetEncoders(){
        m_leftFrontEncoder.setPosition(0.0);
        m_rightFrontEncoder.setPosition(0.0);
        m_rightRearEncoder1.setPosition(0.0);
        m_leftRearEncoder1.setPosition(0.0);
        m_rightRearEncoder2.setPosition(0.0);
        m_leftRearEncoder2.setPosition(0.0);
    }

    //This is called every 20ms
    @Override
    public void periodic(){
        SmartDashboard.putNumber("LF_Enc",m_leftFrontEncoder.getPosition());
        SmartDashboard.putNumber("RF_Enc",m_rightFrontEncoder.getPosition());
        SmartDashboard.putNumber("RR1_Enc",m_rightRearEncoder1.getPosition());
        SmartDashboard.putNumber("RR2_Enc",m_rightRearEncoder2.getPosition());
        SmartDashboard.putNumber("LR1_Enc",m_leftRearEncoder1.getPosition());
        SmartDashboard.putNumber("LR2_Enc",m_leftRearEncoder2.getPosition());
        

        SmartDashboard.putNumber("LF_Speed",m_leftFront.get());
        SmartDashboard.putNumber("RF_Speed",m_rightFront.get());
        SmartDashboard.putNumber("RR1_Speed",m_rightRear1.get());
        SmartDashboard.putNumber("RR2_Speed",m_rightRear2.get());
        SmartDashboard.putNumber("LR1_Speed",m_leftRear1.get());
        SmartDashboard.putNumber("LR2_Speed",m_leftRear2.get());

        SmartDashboard.putNumber("AveragePosition",this.getAverageEncoderPosition());
        SmartDashboard.putNumber("AverageRotation",this.getAverageEncoderRotation());
        SmartDashboard.putNumber("AveragePosition(inch)",this.getAverageEncoderDistanceInches());
    }
}
