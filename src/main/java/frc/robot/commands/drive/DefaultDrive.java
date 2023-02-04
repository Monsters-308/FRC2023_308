package frc.robot.commands.drive;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;

public class DefaultDrive extends CommandBase {
    private DriveSubsystem m_driveSubsystem;
    private DoubleSupplier m_left;
    private DoubleSupplier m_right;
    private BooleanSupplier m_slowMode;

    /**
     * Tank drive that constantly sets the drive motors with the left and right
     * doublesuppliers
     * 
     * @param driveSubsystem the drivesubsystem to be controlled
     * @param left           doulbe supplier for the left motor (-1 to 1)
     * @param right          doulbe supplier for the right motor (-1 to 1)
     */
    public DefaultDrive(DriveSubsystem driveSubsystem, DoubleSupplier left, DoubleSupplier right,
            BooleanSupplier slowMode) {
        m_driveSubsystem = driveSubsystem;
        m_left = left;
        m_right = right;
        m_slowMode = slowMode;
        addRequirements(m_driveSubsystem);
    }

    @Override
    public void execute() {
        double speedMult = 1.0;
        if (m_slowMode.getAsBoolean()) {
            speedMult = DriveConstants.kSlowModeMultiplier;
        }
        m_driveSubsystem.tankDrive(-speedMult * m_left.getAsDouble(), -speedMult * m_right.getAsDouble());
    }
}
