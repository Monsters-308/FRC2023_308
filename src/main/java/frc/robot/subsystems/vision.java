package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class vision extends SubsystemBase {

    private final DoubleSubscriber sd;
    double value;

    public vision(){
        // Initialize NetworkTables
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        //inst.startClient("10.30.80.2"); 

        NetworkTable smartDashboardTable = inst.getTable("SmartDashboard");

        // Get a reference to the SmartDashboard table
        //sd = inst.getTable("SmartDashboard");
        sd = smartDashboardTable.getDoubleTopic("dsTime").subscribe(0.0);
    }

    
    @Override
    public void periodic(){

        //while true, or on button press
        // Retrieve the value of "dsTime" key from the "SmartDashboard" table
        double value = sd.get();

        // Print the value of "dsTime"
        System.out.println("value: " + value);
        
        // Sleep for 1 second
        //Thread.sleep(1000);
    }
}
