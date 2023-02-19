package frc.robot.commands.chassis;

import frc.robot.subsystems.ChassisSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultDrive extends CommandBase {
    private final ChassisSubsystem m_drive;
    private final DoubleSupplier m_xSpeed;
    private final DoubleSupplier m_zRotation;
    
    public DefaultDrive(ChassisSubsystem subsystem, DoubleSupplier xSpeed, DoubleSupplier zRotation){
        System.out.println("another DefaultDrive instance has been created.");
        m_drive = subsystem;
        m_xSpeed = xSpeed;
        m_zRotation = zRotation;
        addRequirements(m_drive);
    }


    @Override
    public void execute(){
        m_drive.drive(m_xSpeed.getAsDouble(), m_zRotation.getAsDouble());
    }

    @Override
    public void end(boolean interrupted){
        System.out.println("DefaultDrive has ended.");
        m_drive.drive(0, 0);
    }

}
