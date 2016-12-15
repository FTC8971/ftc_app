package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by LCSTEAM3 on 12/4/2016.
 */
public class ColorSensorHardware
{
    public ColorSensor colorSensor;
    HardwareMap hwMap;
    //private ElapsedTime period  = new ElapsedTime();

        public ColorSensorHardware()
        {

        }
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        try
        {
            colorSensor = hwMap.colorSensor.get("color_sensor");
        }
        catch(Exception ex){}
    }

}
