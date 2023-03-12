package frc.robot.subsystems;

//NAVX
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NavSubsystem extends SubsystemBase {
    private AHRS NavX2;
    private final double kInitialPitchOffset;
    private double previousYaw = 0;

    public NavSubsystem(AHRS Nav, double yawOffset){
        NavX2 = Nav;
        kInitialPitchOffset = yawOffset;
    }

    //This is called every 20ms
    @Override
    public void periodic(){

        double offsetYaw = NavX2.getYaw() - kInitialPitchOffset;

        double yawChange = offsetYaw-previousYaw;
        
        SmartDashboard.putNumber("Yaw with initial offset:", offsetYaw);
        SmartDashboard.putNumber("Change in yaw:", yawChange);
        
        SmartDashboard.putNumber("Pitch", NavX2.getPitch());
        SmartDashboard.putNumber("Roll", NavX2.getRoll());
        SmartDashboard.putNumber("Yaw", NavX2.getYaw());

        SmartDashboard.putNumber("XAcceleration", NavX2.getWorldLinearAccelX());
        SmartDashboard.putNumber("YAcceleration", NavX2.getWorldLinearAccelY());
        SmartDashboard.putNumber("ZAcceleration", NavX2.getWorldLinearAccelZ());

        SmartDashboard.putNumber("XVelocity", NavX2.getVelocityX());
        SmartDashboard.putNumber("YVelocity", NavX2.getVelocityY());
        SmartDashboard.putNumber("ZVelocity", NavX2.getVelocityZ());

        previousYaw = offsetYaw;
    }
}
