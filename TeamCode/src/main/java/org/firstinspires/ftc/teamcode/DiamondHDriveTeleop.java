/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.DiamondHDriveHardware;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="H-Drive: Teleop", group="H-Drive")
//@Disabled
public class DiamondHDriveTeleop extends OpMode{

    /* Declare OpMode members. */
    DiamondHDriveHardware robot = new DiamondHDriveHardware(); // use the class created to define a Robot's hardware
                                                         // could also use HardwarePushbotMatrix class.


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Robot Mode", "Initializing...");    //
        if (robot.leftMotor == null){//warn the driver if the left motor could not be found
            telemetry.addData("Left Drive Motor", "Could not find in hardware map");
        }
        else{
            telemetry.addData("Left Drive Motor", "Connected");
        }
        if (robot.rightMotor == null){//warn the driver if the right motor could not be found
            telemetry.addData("Right Drive Motor", "Could not find in hardware map");
        }
        else{
            telemetry.addData("Right Drive Motor", "Connected");
        }
        if (robot.centerMotor == null){//warn the driver if the center motor could not be found
            telemetry.addData("Center Drive Motor", "Could not find in hardware map");
        }
        else{
            telemetry.addData("Center Drive Motor", "Connected");
        }
        updateTelemetry(telemetry);//send telemetry data to driver

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        telemetry.addData("Robot Mode", "Waiting...");//tell driver the robot is ready to go
        updateTelemetry(telemetry);//sent telemetry data to driver
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double left;
        double right;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        if (gamepad1.left_stick_x==0 && gamepad1.left_stick_y == 0){//if the left joystick, used for H-Drive, is at rest,
            DiamondHDriveHardware.setMotorPower(robot.rightMotor, -gamepad1.right_stick_x);//use the right joystick to...
            DiamondHDriveHardware.setMotorPower(robot.leftMotor, gamepad1.right_stick_x);//make the robot do zero-turn
            telemetry.addData("Robot Drive Mode", "Zero Turn");//tell the driver the robot is in zero-turn mode
            telemetry.addData("Gamepad 1 Right Stick X", gamepad1.right_stick_x);//send joystick position to driver
            telemetry.addData("Right Motor Power", robot.rightMotor.getPower());//send motor powers...
            telemetry.addData("Left Motor Power", robot.leftMotor.getPower());//to driver
        }
        else{//if the left joystick is not at rest
            DiamondHDriveHardware.setMotorPower(robot.leftMotor, gamepad1.left_stick_y);//left joystick vertical motion...
            DiamondHDriveHardware.setMotorPower(robot.rightMotor, gamepad1.left_stick_y);//controls robot forward/backward motion
            DiamondHDriveHardware.setMotorPower(robot.centerMotor, gamepad1.left_stick_x);//left joystick horizontal motion controls robot left/right motion
            telemetry.addData("Robot Drive Mode", "H-Drive");//tell the driver the robot is in h-drive mode
            telemetry.addData("Gamepad 1 Left Stick X", gamepad1.left_stick_x);//send joystick position to driver
            telemetry.addData("Gamepad 1 Left Stick Y", gamepad1.left_stick_y);//send joystick position to driver
            telemetry.addData("Right Motor Power", robot.rightMotor.getPower());//send motor...
            telemetry.addData("Left Motor Power", robot.leftMotor.getPower());//powers...
            telemetry.addData("Center Motor Power", robot.centerMotor.getPower());//to driver
        }
        telemetry.addData("Robot Mode", "Running...");//tell the driver code is running
        updateTelemetry(telemetry);//send telemetry data to driver
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        telemetry.addData("Robot Mode", "Stopped");//tell the driver code has stopped
        updateTelemetry(telemetry);//send telemetry data to driver
    }

}
