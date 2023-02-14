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

    public MainAutoBalance(ChassisSubsystem subsystem, AHRS Nav){
        m_drive = subsystem;
        NavX2 = Nav; 
    }

    @Override
    public void execute() {

        // double xAxisRate            = stick.getX();
        //returns the pitch as a number from -180 to 180
        double pitchAngleDegrees    = NavX2.getPitch();
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
        // driving in reverse direction of pitch/roll angle,
        // with a magnitude based upon the angle
        
        if ( autoBalanceXMode ) {
            double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
            // xAxisRate = Math.sin(pitchAngleRadians) * -1;

            if((pitchAngleRadians > 1) || (pitchAngleRadians < -1)){
                pitchAngleRadians = 1/0;
            }
            m_drive.drive(pitchAngleRadians, 0);

        }

    }
}
