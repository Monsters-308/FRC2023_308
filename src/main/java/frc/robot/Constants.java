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
        public static final int kLeftFrontPort = 1;
        public static final int kRightFrontPort = 2;
        public static final int kRightRearPort = 3;
        public static final int kLeftRearPort = 4;

        public static final int kCurrentLimit = 40; // 40A current limit for motors eventhough it could be higher but not higher than 50A as the rating is 40A @80%

        public static final double kAutoRotationSpeed = 0.2; // speed to rotate for auto aim
        public static final double kEncoderConversionFactor = 6 * Math.PI / 9.52;

        public static final double kOffBalanceAngleThresholdDegrees = 10;
        public static final double kOonBalanceAngleThresholdDegrees  = 5;

    }

    public static final class IOConstants {
        public static final int kDriverPort = 0;
        public static final int kCoDriverPort = 1;
    }

    
}
