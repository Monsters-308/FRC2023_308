// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.chassis;

//ShuffleBoard library
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//NAVX library
import com.kauailabs.navx.frc.AHRS;

//Subsystem
import frc.robot.subsystems.ChassisSubsystem;

//Constants
import frc.robot.Constants.ChassisConstants;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalanceSmooth extends CommandBase {
    private final ChassisSubsystem m_drive;
    private AHRS NavX2;
    private final double kInitialPitchOffset;
    boolean autoBalanceMode = false;
    private double previousPitch = 0;

    public AutoBalanceSmooth(ChassisSubsystem subsystem, AHRS Nav, double pitchOffset){
        this.m_drive = subsystem;
        this.NavX2 = Nav;
        this.kInitialPitchOffset = pitchOffset; 

        addRequirements(m_drive);
    }

    @Override
    public void initialize(){
        m_drive.setBrakeMode();
        previousPitch = -NavX2.getPitch() - kInitialPitchOffset;
    }

    @Override
    public void execute() {
        double pitchAngleDegrees = -NavX2.getPitch() - kInitialPitchOffset;

        double changeInPitch = pitchAngleDegrees-previousPitch;
        previousPitch = pitchAngleDegrees;

        //This pseudocode relies on the idea that the sign of the pitch and the sign of the changeinpitch will be the same

        //if the pitch is out of the desired range
            //if the pitch is positive
                //if the changeInPitch is positive and greater than a certain amount, don't move. otherwise keep moving
            //if the pitch is negative
                //if the changeInPitch is negative and greater than a certain amount, don't move. otherwise keep moving
        //else stop moving

        if(Math.abs(pitchAngleDegrees) >= Math.abs(ChassisConstants.kOffBalanceAngleThresholdDegrees)){
            if(pitchAngleDegrees>0){
                if((changeInPitch>0) && (changeInPitch>ChassisConstants.kChangeInPitchTolerance)){
                    autoBalanceMode = false;
                }
                else{
                    autoBalanceMode = true;
                }
            }
            else{
                if((changeInPitch<0) && (changeInPitch<-ChassisConstants.kChangeInPitchTolerance)){
                    autoBalanceMode = false;
                }
                else{
                    autoBalanceMode = true;
                }
            }
        }
        else{
            autoBalanceMode = false;
        }
        SmartDashboard.putBoolean("IsAutobalancing?", autoBalanceMode);
        

        /*if (!autoBalanceXMode && 
            (Math.abs(pitchAngleDegrees) >= Math.abs(ChassisConstants.kOffBalanceAngleThresholdDegrees))) {
                autoBalanceXMode = true;
        }
        else if (autoBalanceXMode && 
            (Math.abs(pitchAngleDegrees) <= Math.abs(ChassisConstants.kOnBalanceAngleThresholdDegrees))) {
                autoBalanceXMode = false;
        }*/
        
        // Control drive system automatically, 
        // driving in direction of pitch angle,
        // with a magnitude based upon the angle
        
        if ( autoBalanceMode ) {
            double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
            double xAxisSpeed = Math.sin(pitchAngleRadians) ;
            
            //If we go too fast, the robot will go over the center of the pad and keep rocking back and forth.
            //If we go too slow, the robot will struggle to get over the charge pad since the ramp will make it slide downwards.
            //We might want to consider using a cubic function instead of a sine function.
            xAxisSpeed *= ChassisConstants.kAutoBalanceBackMultiplier;

            if(Math.abs(pitchAngleDegrees) > 30){
                xAxisSpeed = 0;
            }
            
            m_drive.drive(xAxisSpeed, 0);

            SmartDashboard.putNumber("AutoBalanceSpeed", xAxisSpeed);
        }

        //If the robot is balanced, it should tell the motors to stop moving.
        else{
            m_drive.drive(0, 0);
        }
    }

    //This is for if DefaultDrive doesn't tell the motors to stop moving so the robot doesn't crash into a wall.
    @Override
    public void end(boolean interrupted){
        m_drive.drive(0, 0);
    }
}
