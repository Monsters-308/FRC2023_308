package frc.robot.commands.chassis;

import frc.robot.subsystems.ChassisSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultDrive extends CommandBase {
    private final ChassisSubsystem m_drive;
    private final DoubleSupplier m_xSpeed;
    private final DoubleSupplier m_zRotation;
    private final BooleanSupplier m_autoBalance;
    
    public DefaultDrive(ChassisSubsystem subsystem, DoubleSupplier xSpeed, DoubleSupplier zRotation, BooleanSupplier autoBalance){
        m_drive = subsystem;
        m_xSpeed = xSpeed;
        m_zRotation = zRotation;
        m_autoBalance = autoBalance;
        addRequirements(m_drive);
    }


    @Override
    public void execute(){
        if(!m_autoBalance.getAsBoolean()){
            m_drive.drive(m_xSpeed.getAsDouble(), m_zRotation.getAsDouble());
        }
        
    }

}
