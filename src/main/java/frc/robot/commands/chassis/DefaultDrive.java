package frc.robot.commands.chassis;

import frc.robot.subsystems.ChassisSubsystem;

//Suppliers (for lambda functions)
import java.util.function.DoubleSupplier;
import java.util.function.BooleanSupplier;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultDrive extends CommandBase {
    private final ChassisSubsystem m_drive;
    private final DoubleSupplier m_xSpeed;
    private final DoubleSupplier m_zRotation;
    private final BooleanSupplier m_turbo;
    
    public DefaultDrive(ChassisSubsystem subsystem, DoubleSupplier xSpeed, DoubleSupplier zRotation, BooleanSupplier turbo){
        m_drive = subsystem;
        m_xSpeed = xSpeed;
        m_zRotation = zRotation;
        m_turbo = turbo;
        addRequirements(m_drive);
    }

    @Override
    public void initialize(){
        m_drive.setCoastMode();
    }

    @Override
    public void execute(){
        m_drive.drive(m_xSpeed.getAsDouble(), m_zRotation.getAsDouble(), m_turbo.getAsBoolean());
    }

    @Override
    public void end(boolean interrupted){
        m_drive.drive(0, 0);
    }
}
