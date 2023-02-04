package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveDistance extends CommandBase {
    private final double m_distance;
    private final double m_speed;
    private final DriveSubsystem m_driveSubsystem;
    public boolean m_complete = false;
    private double start_encoders;

    /**
     * A command to control the driveSubsystem to drive straight for a certain distance.
     * @param inches the amount of distance you want to travel. This must always be positive, regardless of whether your speed is negative.
     * @param speed the speed and direction that you want to travel in. (-1 to 1)
     * @param driveSubsystem the drive subsystem (probably).
     */
    public DriveDistance(double inches, double speed, DriveSubsystem driveSubsystem) {
        m_distance = inches;
        m_speed = speed;
        m_driveSubsystem = driveSubsystem;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void initialize() {
        m_complete = false;
        start_encoders = m_driveSubsystem.getAverageEncoderDistanceInches();
        // m_driveSubsystem.resetEncoders(); //this takes 200ms and can cause problems, get encoders and find difference instead
    }

    @Override
    public void execute() {
        if (Math.abs(m_driveSubsystem.getAverageEncoderDistanceInches()-start_encoders) >= m_distance) {
            m_driveSubsystem.tankDrive(0, 0);
            m_complete = true;
        } else {
            m_driveSubsystem.tankDrive(m_speed, m_speed);
        }
    }

    @Override
    public boolean isFinished() {
        return m_complete;
    }
}
