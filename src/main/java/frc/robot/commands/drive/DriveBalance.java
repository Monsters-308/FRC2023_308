/*package frc.robot.commands.drive;

public class DriveBalance {
    static final double kOffBalanceAngleThresholdDegrees = 10;
static final double kOonBalanceAngleThresholdDegrees  = 5;

public void operatorControl() {
    while (isOperatorControl() && isEnabled()) {

        double xAxisRate            = stick.getX();
        double yAxisRate            = stick.getY();
        double pitchAngleDegrees    = ahrs.getPitch();
        double rollAngleDegrees     = ahrs.getRoll();
        
        if ( !autoBalanceXMode && 
             (Math.abs(pitchAngleDegrees) >= 
              Math.abs(kOffBalanceAngleThresholdDegrees))) {
            autoBalanceXMode = true;
        }
        else if ( autoBalanceXMode && 
                  (Math.abs(pitchAngleDegrees) <= 
                   Math.abs(kOonBalanceAngleThresholdDegrees))) {
            autoBalanceXMode = false;
        }
        if ( !autoBalanceYMode && 
             (Math.abs(pitchAngleDegrees) >= 
              Math.abs(kOffBalanceAngleThresholdDegrees))) {
            autoBalanceYMode = true;
        }
        else if ( autoBalanceYMode && 
                  (Math.abs(pitchAngleDegrees) <= 
                   Math.abs(kOonBalanceAngleThresholdDegrees))) {
            autoBalanceYMode = false;
        }
        
        // Control drive system automatically, 
        // driving in reverse direction of pitch/roll angle,
        // with a magnitude based upon the angle
        
        if ( autoBalanceXMode ) {
            double pitchAngleRadians = pitchAngleDegrees * (Math.PI / 180.0);
            xAxisRate = Math.sin(pitchAngleRadians) * -1;
        }
        if ( autoBalanceYMode ) {
            double rollAngleRadians = rollAngleDegrees * (Math.PI / 180.0);
            yAxisRate = Math.sin(rollAngleRadians) * -1;
        }
        myRobot.mecanumDrive_Cartesian(xAxisRate, yAxisRate, stick.getTwist(),0);
        Timer.delay(0.005);		// wait for a motor update time
    }
}
}
*/
