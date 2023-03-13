package frc.robot.commands.chassis;

import frc.robot.subsystems.ChassisSubsystem;

//ShuffleBoard library
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Suppliers (for lambda functions)
import java.util.function.DoubleSupplier;
import java.util.function.BooleanSupplier;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultDrive extends CommandBase {
    private final ChassisSubsystem m_drive;
    private final DoubleSupplier m_xSpeed;
    private final DoubleSupplier m_zRotation;

    public DefaultDrive(ChassisSubsystem subsystem, DoubleSupplier xSpeed, DoubleSupplier zRotation){
        m_drive = subsystem;
        m_xSpeed = xSpeed;
        m_zRotation = zRotation;
        addRequirements(m_drive);
    }

    @Override
    public void initialize(){
        m_drive.setCoastMode();
    }

    @Override
    public void execute(){
        m_drive.drive(m_xSpeed.getAsDouble(), m_zRotation.getAsDouble());
    }

    @Override
    public void end(boolean interrupted){
        m_drive.drive(0, 0);
    }
}
