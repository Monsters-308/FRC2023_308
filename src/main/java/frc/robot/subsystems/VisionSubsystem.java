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

    public VisionSubsystem(){
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        ty = table.getEntry("ty");
        tx = table.getEntry("tx");
        tl = table.getEntry("ty");
        tv = table.getEntry("tv");
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(3);
    }
    /**
     * Pipe number - purpose
     * 0           - top reflective tape
     * 1           - Apriltags (unused)
     * 2           - Cones (unused)
     * 3           - Default camera
     * 4           - bottom reflective tape  
     */

    //tv = valid targets
    //tx horizontal offest from crosshair to target
    //ty vertical offset from crosshair to target
    //ta = target area 0% to 100%

    public double getX(){
        return tx.getDouble(0.0);
    }

    public void setPipeline(int pipeline){
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline);
    }

    public double getPipeline(){
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").getDouble(0);
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
        double limelightMountAngleDegrees = 4; //NOTE: we should really take into account the rotation of the robot using the pitch of the NavX - Noah

        // distance from the center of the Limelight lens to the floor
        double limelightLensHeightInches = 28.5;

        // distance from the target to the floor
        double goalHeightInches;

        if(targetOffsetAngle_Vertical > 0){
            goalHeightInches = 43.5;
        }
        else{
            goalHeightInches = 24;
        }


        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);

        //calculate distance
        distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches)/Math.tan(angleToGoalRadians);
        SmartDashboard.putNumber("Distance from limelight",distanceFromLimelightToGoalInches);
    }
}