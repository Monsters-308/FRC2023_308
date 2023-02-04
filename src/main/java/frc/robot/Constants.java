// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int kLeftMotor1Port = 2;
        public static final int kLeftMotor2Port = 1;
        public static final int kRightMotor1Port = 4;
        public static final int kRightMotor2Port = 3;
        // Wheel diameter * pi / gear ratio
        public static final double kEncoderConversionFactor = 6 * Math.PI / 9.52;
        public static final double kAutonDriveSpeed = 0.3;
        public static final double kSlowModeMultiplier = 0.5;
    }

    public static final class ShooterConstants {
        public static final int kShooterMotorCANPort = 11;
        public static final int kHelperMotorCANPort = 7;
        public static final int kBackspinMotorPort = 5;

        public static final double kF = 0.05;
        public static final double kD = 5.0;
        public static final double kI = 0.001;
        public static final double kP = 0.1;
        public static final int kIzone = 300;
        public static final int kUnitsPerRotation = 2048;

        public static final double kShooterSpeed = 0.40; //at 44% power, the shooter was at 2000-2100 RMP
        public static final double kShooterLowGoalSpeed = 0.3;
        public static final double kHelperMotorSpeed = 0.5;
        public static final double kBackspinMotorSpeed = 0.5;
        public static final double kShooterReverseSpeed = -0.4;
        public static final double kShooterSpeedRPM = 2000;

        public static final double kRampTimeSec = 1;
        public static final double kMaxIndexTimeSec = 1;
        public static final double kMaxRampTime = 2;
        public static final double kMaxReleaseTimeSec = 0.15;
    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorPort = 10;

        public static final double kIntakeMotorSpeed = 0.7;
    }

    public static final class IndexConstants {
        public static final int kIndexMotorPort = 8;
        public static final int kHighSensorPort = 2;
        public static final int kLowSensorPort = 1;
        public static final int kIntakeSensorPort = 0;

        public static final double kIndexMotorSpeed = 0.5;
    }

    public static final class HangConstants {
        public static final int kLeftStaticArmPort = 6;
        public static final int kRightStaticArmPort = 9;
        public static final int kLeftRotatingArmPort = 13;
        public static final int kRightRotatingArmPort = 14;
        public static final int kUpperLeftSwitchPort = 7; // change when we know the port // 7 5
        public static final int kLowerLeftSwitchPort = 8; // change when we know the port // 8 6
        public static final int kUpperRightSwitchPort = 5; // change when we know the port
        public static final int kLowerRightSwitchPort = 6; // change when we know the port

        public static final double kHangMotorSpeed = 1.0;
        public static final double kRotHangMotorSpeed = 0.75;
    }

    public static final class IOConstants {
        public static final int kControllerDrivePort = 0;
        public static final int kControllerCoPort = 1;
        public static final double kTriggerThreshold = 0.5;
    }

    public static final class LEDConstants {
        public static final int kLEDPWMPort = 0;
        public static final int kLEDCount = 240; // 79 and 240
    }

    public static final class PneumaticsConstants {
        public static final int kControlModulePort = 17;
        public static final int kIntakePistonPort = 6;
        public static final int kHangPistonPort = 7;
    }
}
