package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionSubsystem extends SubsystemBase {
    final public NetworkTableEntry ty;
    final public NetworkTableEntry tx; 
    final public NetworkTableEntry tl;
    final public NetworkTableEntry tv;
    public double distanceFromLimelightToGoalInches;
    /**
     * STUB: Add LimeLight items here
     */
    public VisionSubsystem(){
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        ty = table.getEntry("ty");
        tx = table.getEntry("tx");
        tl = table.getEntry("ty");
        tv = table.getEntry("tv");
    }

    //tv = valid targets
    //tx horizontal offest from crosshair to target
    //ty vertical offset from crosshair to target
    //ta = target area 0% to 100%

    public double getX(){
        return tx.getDouble(0.0);
    }

    public double getY(){
        return ty.getDouble(0.0);
    }
    
    public double getTL(){
        return tl.getDouble(0.0);
    }

    public double getTV(){
        return tv.getDouble(0.0);
    }

    public double getDistance(){
        return distanceFromLimelightToGoalInches; 
    }

    

    @Override
    public void periodic(){
        /**
         * Limelight code test to get range of the photoreflective tape
         */
        double targetOffsetAngle_Vertical = ty.getDouble(0.0);


        // how many degrees back is your limelight rotated from perfectly vertical?
        double limelightMountAngleDegrees = 5.0;

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 37.0;

        // distance from the target to the floor
        double goalHeightInches = 43.5;

        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

        //calculate distance
        double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches)/Math.tan(angleToGoalRadians);
        SmartDashboard.putNumber("Distence from limelight",distanceFromLimelightToGoalInches);


    }
}