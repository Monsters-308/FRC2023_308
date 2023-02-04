package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class StopDrive extends CommandBase {
    private final DriveSubsystem m_driveSubsystem;

    public StopDrive(DriveSubsystem driveSubsystem) {
        m_driveSubsystem = driveSubsystem;

        addRequirements(m_driveSubsystem);
    }

    @Override
    public void initialize() {
        m_driveSubsystem.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
