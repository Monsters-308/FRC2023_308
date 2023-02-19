// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.chassis;

//NAVX
import com.kauailabs.navx.frc.AHRS;

//Subsystem
import frc.robot.subsystems.ChassisSubsystem;

//Constants
import frc.robot.Constants.ChassisConstants;

//actually important stuff
import edu.wpi.first.wpilibj2.command.CommandBase;


public class MainAutoBalance extends CommandBase {
    private final ChassisSubsystem m_drive;

    static private AHRS NavX2;

    final double kInitialPitchOffset;

    public MainAutoBalance(ChassisSubsystem subsystem, AHRS Nav, double pitchOffset){
        m_drive = subsystem;
        NavX2 = Nav;
        kInitialPitchOffset = pitchOffset; 
        addRequirements(m_drive);
    }

    @Override
    public void execute() {

        //returns the pitch as a number from -180 to 180
        double pitchAngleDegrees = NavX2.getPitch() - kInitialPitchOffset;
        //System.out.println("Pitch offset: " + kInitialPitchOffset);
        System.out.println("Pitch with initial offset: " + pitchAngleDegrees);

        //I'm pretty sure this will have to be moved outside of this function.
        boolean autoBalanceXMode = false;

        if ( !autoBalanceXMode && 
            (Math.abs(pitchAngleDegrees) >= 
            Math.abs(ChassisConstants.kOffBalanceAngleThresholdDegrees))) {
            autoBalanceXMode = true;
        }
        else if ( autoBalanceXMode && 
                (Math.abs(pitchAngleDegrees) <= 
                Math.abs(ChassisConstants.kOonBalanceAngleThresholdDegrees))) {
            autoBalanceXMode = false;
        }
        
        // Control drive system automatically, 
        // driving in direction of pitch/roll angle,
        // with a magnitude based upon the angle
        
        if ( autoBalanceXMode ) {
            double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
            double xAxisSpeed = Math.sin(pitchAngleRadians) ;
            
            //I don't know how slow, but we need to significantly slow down the autobalance once we know it is working.
            xAxisSpeed *= 1;

            System.out.println("xAxisSpeed: " + xAxisSpeed);
            
            m_drive.drive(xAxisSpeed, 0);
           
            //System.out.println("Auto Balance speed: " + xAxisSpeed);

        }

        //If the robot is balanced, it should tell the motors to stop moving.
        else{
            m_drive.drive(0, 0);
        }

    }


    //This is for if DefaultDrive doesn't tell the motors to stop moving so the robot doesn't crash into a wall.
    //I swear to fucking god if the robot takes off again I will throw it out a window and onto Mr. Mallot's car.
    @Override
    public void end(boolean interrupted){
        System.out.println("AutoBalance has ended.");
        m_drive.drive(0, 0);
    }

    /*@Override
    public InterruptionBehavior getInterruptionBehavior(){
         
    }*/

}
