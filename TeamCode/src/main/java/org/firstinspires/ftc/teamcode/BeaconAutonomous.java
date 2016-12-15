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

    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.Disabled;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.util.Range;

    import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

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
     * Created by Max Davy for Diamond Blades 8971 on 09/17/16
     */

    @Autonomous(name="Diamondbot H-Drive Autonomous", group="H-Drive")
    public class BeaconAutonomous extends OpMode{

        /* Declare OpMode members. */
        DiamondHDriveHardware robot = new DiamondHDriveHardware(); // use the class created to define the bot's hardware
        DiamondSensor sensor = new DiamondSensor("color_sensor", hardwareMap); //use the class created to define bot's color sensor

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
            telemetry.addData("Robot State", "Initialized");    //
            updateTelemetry(telemetry);
        }

        /*
         * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
         */
        @Override
        public void init_loop() {
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
        public void loop() { //robot goes forward until it senses the color white, then it stops
            if (!sensor.isBright()){
                robot.leftMotor.setPower(0.8);
                robot.rightMotor.setPower(0.8);
            } else {
                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
            }
        }


            /*if (gamepad1.left_stick_y != 0 && gamepad1.left_stick_x != 0) //if the left joystick isn't at rest
            {
                //use H-Drive (no rotation)
                robot.leftMotor.setPower(-gamepad1.left_stick_y);//set the left and right motor power based on the joystick's vertical position
                robot.rightMotor.setPower(-gamepad1.left_stick_y);//(note: The joystick goes negative when pushed forwards, so negate it)
                robot.centerMotor.setPower(gamepad1.left_stick_x);//set the center motor power based on the joystick's horizontal position
                //remember, the right motor was set to reverse in the hardware file
            }
            else{//if the left joystick is stationary
                //use regular tank drive (turn on the spot)
                robot.leftMotor.setPower(-gamepad1.right_stick_x);//set the left and right motor power based on the joystick's vertical position
                robot.rightMotor.setPower(gamepad1.right_stick_x);//(note: The joystick goes negative when pushed forwards, so negate it)
            }



            telemetry.addData("Joystick Position", gamepad1.left_stick_y);
            telemetry.addData("Robot State", "Running");// Send telemetry message to signify robot running
            telemetry.addData("Left Motor Power",  "%.2f", robot.leftMotor.getPowerFloat());
            telemetry.addData("Right Motor Power", "%.2f", robot.rightMotor.getPowerFloat());
            telemetry.addData("Center Motor Power", "%.2f", robot.centerMotor.getPowerFloat());
            updateTelemetry(telemetry);//send telemetry data to driver station
        }*/

        /*
         * Code to run ONCE after the driver hits STOP
         */
        @Override
        public void stop() {
            telemetry.addData("Robot State", "Stopped");
            updateTelemetry(telemetry);
        }

    }


