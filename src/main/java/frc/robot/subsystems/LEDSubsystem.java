package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import frc.robot.Constants.LEDState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class LEDSubsystem extends SubsystemBase {
  
    private LEDState m_ledMode;
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    private int[] LEDColor = {0,0,0}; //RGB

    
    
    public LEDSubsystem(){
      // Must be a PWM header, not MXP or DIO
      m_led = new AddressableLED(0);

      // Reuse buffer
      // Default to a length of 60, start empty output
      // Length is expensive to set, so only set it once, then just update data
      m_ledBuffer = new AddressableLEDBuffer(20);
      m_led.setLength(m_ledBuffer.getLength());

      // Set the data
      m_led.setData(m_ledBuffer);
      m_led.start();    
    }
    
  
@Override
public void periodic() {      
  // Fill the buffer with a rainbow
  rainbow();
}
  
public void changeLEDState(LEDState mode) {
  this.m_ledMode = mode;
}

public void solid(int[] RGB) {
  for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      m_ledBuffer.setRGB(i, RGB[0], RGB[1], RGB[2]);
  }

  m_led.setData(m_ledBuffer);
}

public void blink(int[] RGB) {
  for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      m_ledBuffer.setRGB(i, 255, 0, 0);
  }

  m_led.setData(m_ledBuffer);

  for (var i = 0; i < m_ledBuffer.getLength(); i++) {
    m_ledBuffer.setRGB(i, 0, 255, 0);
  }

  m_led.setData(m_ledBuffer);
  
}

private void rainbow() {
  // For every pixel
  int m_rainbowFirstPixelHue = 20;

  for (var i = 0; i < m_ledBuffer.getLength(); i++) {
    // Calculate the hue - hue is easier for rainbows because the color
    // shape is a circle so only one value needs to precess
    final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
    // Set the value
    m_ledBuffer.setHSV(i, hue, 255, 128);
  }
  // Increase by to make the rainbow "move"
  m_rainbowFirstPixelHue += 3;
  // Check bounds
  m_rainbowFirstPixelHue %= 180;
  m_led.setData(m_ledBuffer);
}
}
  

  


