package frc.robot.commands.drive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;

public class ArcadeDrive extends CommandBase {
    private DriveSubsystem m_driveSubsystem;
    private DoubleSupplier m_fwd;
    private DoubleSupplier m_rot;
    private BooleanSupplier m_slowMode;

    /**
     * Tank drive that constantly sets the drive motors with the left and right
     * doublesuppliers
     * 
     * @param driveSubsystem the drivesubsystem to be controlled
     * @param left           doulbe supplier for the left motor (-1 to 1)
     * @param right          doulbe supplier for the right motor (-1 to 1)
     */
    public ArcadeDrive(DriveSubsystem driveSubsystem, DoubleSupplier fwd, DoubleSupplier rot,
            BooleanSupplier slowMode) {
        m_driveSubsystem = driveSubsystem;
        m_fwd = fwd;
        m_rot = rot;
        m_slowMode = slowMode;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void execute() {
        double speedMultiplier = 1.0;
        if (m_slowMode.getAsBoolean()) {
            speedMultiplier = DriveConstants.kSlowModeMultiplier;
        }
        m_driveSubsystem.arcadeDrive(-speedMultiplier * m_fwd.getAsDouble(), 1 * m_rot.getAsDouble());
    }
}
