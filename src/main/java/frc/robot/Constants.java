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

    public static final class ChassisConstants {
        public static final int kLeftFrontPort = 2;
        public static final int kRightFrontPort = 3;
        public static final int kRightRearPort = 5;
        public static final int kLeftRearPort = 4;

        public static final int kCurrentLimit = 40; // 40A current limit for motors

        //public static final double kAutoRotationSpeed = 0.4; // speed to rotate for auto aim

        public static final double kdriftOffset = 0; // account for drift when driving straight

        public static final double kWheelDiameter = 7.5;
        public static final double kGearRatio = 16.444;

        // Wheel diameter * pi / gear ratio
        public static final double kEncoderConversionFactor = kWheelDiameter * Math.PI / kGearRatio;

        //((diameter of drive train * pi) / (diameter of wheels * pi)) / 360 = number of rotations for wheels for the robot to spin 1 degree
        public static final double kEndcoderRotationConversionFactor = ((23.5 * Math.PI) / (kWheelDiameter * Math.PI)/kGearRatio) / 360;

        //NOTE: the rulebook says that the robot is considered balanced if it's within 2.5 degrees of being balanced.
        public static final double kOffBalanceAngleThresholdDegrees = 2.5;//
        public static final double kOnBalanceAngleThresholdDegrees = 2.5;//

        public static final double kAutoBalanceMultiplier = 0.6;

    }

    public static final class ArmConstants {
        public static final int kMotorPort = 21;
        public static final int kCurrentLimit = 30;

        public static final int kPotPort = 0;

        //Note: constants marked with "//" are constants we still need to figure out
        public static final double kAngleTolerance = 10;//

        public static final double kMaxAngle = 180;
        public static final double kMinAngle = 0;

        //Position: the angle to set the arm to
        public static final double kBottomPosition = 5;
        public static final double kMiddlePosition = 128;
        public static final double kTopPosition = 151;
        //public static final double kLoadingPosition = 45;//

        //speed: the speed at which to move the arm at when going to a level
        public static final double kBottomSpeed = -0.3;
        public static final double kMiddleSpeed = 0.6;//
        public static final double kTopSpeed = 0.7;//
        //public static final double kLoadingSpeed = 0.6;//

        //offset: the power needed to keep the arm from falling down
        public static final double kBottomOffset = -0.2;
        public static final double kMiddleOffset = 0.2;
        public static final double kTopOffset = 0.2;
        //public static final double kLoadingOffset = 0.6;//
    }

    public static final class ClawConstants {
        public static final int kModuleID = 10; //The ID for the PCM in the canbus.
        public static final int kClawPistonChannel = 0;
        public static final int kWristPistonChannel = 7;
    }

    public static final class IOConstants {
        public static final int kDriverPort = 0;
        public static final int kCoDriverPort = 1;
        public static final double kTriggerThreshold = 0.5;
    }
}
