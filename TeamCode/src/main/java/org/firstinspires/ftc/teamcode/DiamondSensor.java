//package com.qualcomm.ftcrobotcontroller.opmodes;
package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ColorSensorHardware;

//public class DiamondSensor extends LinearOpMode {
//
//    ColorSensor colorSensor;
//
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//
//        // write some device information (connection info, name and type)
//        // to the log file.
//        hardwareMap.logDevices();
//
//        // get a reference to our ColorSensor object.
//        colorSensor = hardwareMap.colorSensor.get("color_sensor");
//
//        // bEnabled represents the state of the LED.
//        boolean bEnabled = true;
//
//        // turn the LED on in the beginning, just so user will know that the sensor is active.
//        colorSensor.enableLed(true);
//
//        // wait one cycle.
//        waitOneFullHardwareCycle();
//
//        // wait for the start button to be pressed.
//        waitForStart();
//
//        // hsvValues is an array that will hold the hue, saturation, and value information.
//        float hsvValues[] = {0F,0F,0F};
//
//        // values is a reference to the hsvValues array.
//        final float values[] = hsvValues;
//
//        // get a reference to the RelativeLayout so we can change the background
//        // color of the Robot Controller app to match the hue detected by the RGB sensor.
//        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);
//
//        // bPrevState and bCurrState represent the previous and current state of the button.
//        boolean bPrevState = false;
//        boolean bCurrState = false;
//
//        // while the op mode is active, loop and read the RGB data.
//        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
//        while (opModeIsActive()) {
//            // check the status of the x button on either gamepad.
//            bCurrState = gamepad1.x || gamepad2.x;
//
//            // check for button state transitions.
//            if (bCurrState == true && bCurrState != bPrevState)  {
//                // button is transitioning to a pressed state.
//
//                // print a debug statement.
//                DbgLog.msg("MY_DEBUG - x button was pressed!");
//
//                // update previous state variable.
//                bPrevState = bCurrState;
//
//                // on button press, enable the LED.
//                bEnabled = true;
//
//                // turn on the LED.
//                colorSensor.enableLed(bEnabled);
//            } else if (bCurrState == false && bCurrState != bPrevState)  {
//                // button is transitioning to a released state.
//
//                // print a debug statement.
//                DbgLog.msg("MY_DEBUG - x button was released!");
//
//                // update previous state variable.
//                bPrevState = bCurrState;
//
//                // on button press, enable the LED.
//                bEnabled = false;
//
//                // turn off the LED.
//                colorSensor.enableLed(false);
//            }
//
//            // convert the RGB values to HSV values.
//            Color.RGBToHSV(colorSensor.red(), colorSensor.green(), colorSensor.blue(), hsvValues);
//
//            // send the info back to driver station using telemetry function.
//
//
//            // change the background color to match the color detected by the RGB sensor.
//            // pass a reference to the hue, saturation, and value array as an argument
//            // to the HSVToColor method.
//            relativeLayout.post(new Runnable() {
//                public void run() {
//                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
//                }
//            });
//
//            // wait a hardware cycle before iterating.
//            waitOneFullHardwareCycle();
//        }
//    }
//}


public class DiamondSensor extends LinearOpMode
{
    public ColorSensor colorSensor;

    public void runOpMode() throws InterruptedException {
        while (opModeIsActive())
        {
        }
    }
    public boolean isBright(){
        if (colorSensor.alpha() >= 240){
            return true;
        }
        else{
            return false;
        }
    }
    public DiamondSensor(String name,HardwareMap hardwareMap)
    {
        colorSensor = hardwareMap.colorSensor.get(name);
    }

}
