package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


//NAVX libraries
import com.kauailabs.navx.frc.AHRS;

public class LEDSubsystem extends SubsystemBase {
 
  public enum LEDState {
    //These comments were added in by Marcus
    NONE, //Turn LEDs off
    RAINBOW, //Static rainbow hew across LEDs
    BLACKWHITE, //turbo mode rainbow red
    SOLID, //Static single color
    PULSE, //Flip between two colors at blink speed
    STREAK, //One per LED color moves down the strip
    BLINK, //Single color turns on and off
    GREEN,
    YELLOW,
    TEAL,
    TURBO,
    ORANGE,
    TEST,
    RED
  }

  private LEDState m_ledMode;
  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;
  private int[] LEDColor = {255,0,0}; //RGB
  private int m_rainbowFirstPixelHue = 0;
  private AHRS ahrs; 
  private int m_TurboFirstPixelHue = 0;
  private int rainbowSunshine = 0;

  public LEDSubsystem(AHRS navx2){
    ahrs = navx2;
    // Must be a PWM header, not MXP or DIO
    m_ledMode = LEDState.RAINBOW;
    m_led = new AddressableLED(0);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(125);
    
    m_led.setLength(m_ledBuffer.getLength());

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();    
  }

  @Override
  public void periodic() {    
    switch(m_ledMode) {
      case RAINBOW:
        rainbow();
        break;
      case SOLID:
        solid(LEDColor);
        break;
      case BLACKWHITE:
        red(LEDColor);
        break;
      case GREEN:
        green(LEDColor);
        break;
      case YELLOW:
        yellow(LEDColor);
        break;
      case RED:
        red(LEDColor);
        break;
      case TEAL:
        teal();
        break;
      case TURBO:
        turbo();
        break;
      case ORANGE:
        orange();
        break;
      case TEST:
        test();
        break;
      default:
        rainbow();
        break;
      
    }
    m_rainbowFirstPixelHue = Math.abs((int)ahrs.getYaw()) + m_TurboFirstPixelHue;
  }
    
  public void changeLEDState(LEDState mode) {
    this.m_ledMode = mode;
  }

  public void teal() {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        m_ledBuffer.setRGB(i, 0, 0 ,180);
    }

    m_led.setData(m_ledBuffer);
  }

  public void solid(int[] RGB) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        m_ledBuffer.setRGB(i, RGB[0], RGB[1], RGB[2]);
    }

    m_led.setData(m_ledBuffer);
  }
  public void green(int[] RGB) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        //m_ledBuffer.setRGB(i, RGB[0], 255, 30);
        m_ledBuffer.setRGB(i, 0, 255, 0);
    }



    m_led.setData(m_ledBuffer);
  }
  public void orange() {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        //m_ledBuffer.setRGB(i, RGB[0], 255, 30);
        m_ledBuffer.setRGB(i, 255, 255, 0);
    }



    m_led.setData(m_ledBuffer);
  }
  public void greenAlign(int ammountGreen) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        m_ledBuffer.setRGB(i, 0, ammountGreen, 30);
    }

    m_led.setData(m_ledBuffer);
  }
  public void red(int[] RGB) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        //m_ledBuffer.setRGB(i, 255, 0, RGB[2]);
        m_ledBuffer.setRGB(i, 255, 0, 0);
    }

    m_led.setData(m_ledBuffer);
  }
  public void yellow(int[] RGB) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        m_ledBuffer.setRGB(i, 255, 255, 0);
    }

    m_led.setData(m_ledBuffer);
  }

  public void blink(int[] RGB) { }

  private void rainbow() {
    // For every pixel

    // For every pixel

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())+ (rainbowSunshine + (i * 180 / m_ledBuffer.getLength()))) % 360;
      // Set the value
      m_ledBuffer.setRGB(i, hue*3, hue*10, rainbowSunshine*5);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;
    m_led.setData(m_ledBuffer);
  }

  private void test() {
    // For every pixel

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_TurboFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      
      // Set the value
      if (hue > 100){
        m_ledBuffer.setRGB(i, hue, 0, 255);
      }
      else{
        m_ledBuffer.setRGB(i, 255, 0, hue);
      }

      
    }
    // Increase by to make the rainbow "move"
    m_TurboFirstPixelHue += 3;
    // Check bounds
    m_TurboFirstPixelHue %= 180;
    m_led.setData(m_ledBuffer);
  }

  private void turbo() {
    // For every pixel

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_TurboFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setRGB(i, 200, hue, 0);
    }
    // Increase by to make the rainbow "move"
    m_TurboFirstPixelHue += 3;
    // Check bounds
    m_TurboFirstPixelHue %= 180;
    m_led.setData(m_ledBuffer);
  }
  private void frenzy() {
    // For every pixel

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setRGB(i, hue, 0, 0);
    }
    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;
    m_led.setData(m_ledBuffer);
  }
}
