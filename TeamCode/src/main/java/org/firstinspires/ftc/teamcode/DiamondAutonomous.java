package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by LCSTEAM3 on 12/16/2016.
 */

@Autonomous(name="Diamondbot Autonomous", group="Autonomous")
public class DiamondAutonomous extends LinearOpMode {
    DcMotor hDrive;
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor shootMotor;
    Servo leftServo;
    Servo rightServo;
    Servo shootServo;
    DcMotor cageMotor;
    DcMotor liftMotor;
    DcMotor scoopMotor;
    ColorSensor colorSensor1;
    ColorSensor beaconSensor;
    //UltrasonicSensor sonar;

    final double INIT_LEFT_SERVO_POS = 0;
    final double INIT_RIGHT_SERVO_POS = 1;
    final double INIT_SHOOT_SERVO_POS = 0.3;

    @Override
    public void runOpMode() throws InterruptedException
    {
        init1();
        int i=0;
        while(i<2){
            int goal = -1100 + shootMotor.getCurrentPosition();
            while (shootMotor.getCurrentPosition() > goal) {

                shootMotor.setPower(-0.5);
                telemetry.addData("shoot encoder:", shootMotor.getCurrentPosition());
                telemetry.update();
            }
                shootMotor.setPower(0);
                shootServo.setPosition(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                shootServo.setPosition(INIT_SHOOT_SERVO_POS);

                i=i+1;
        }

        //////////////////////////////////////////////////////////////////
        ////////////////////////////shoot/////////////////////////////////
        //////////////////////////////////////////////////////////////////

        //move left
        while (hDrive.getCurrentPosition() >  -4400) {

            hDrive.setPower(-0.5);
            telemetry.addData("hdrive encoder:", hDrive.getCurrentPosition());
            telemetry.update();
        }
        hDrive.setPower(0);
        //rotate 180
        while (rightMotor.getCurrentPosition() >  -2200) {

            leftMotor.setPower(-0.5);
            rightMotor.setPower(-0.5);
            telemetry.addData("rightMotor encoder:", rightMotor.getCurrentPosition());
            telemetry.update();
        }
        //move forward
        while (rightMotor.getCurrentPosition() < 1600) {

            leftMotor.setPower(0.5);
            rightMotor.setPower(-0.5);
            telemetry.addData("rightMotor encoder:", rightMotor.getCurrentPosition());
            telemetry.update();
        }
        while (colorSensor1.alpha()<25) {

            hDrive.setPower(-0.5);
            telemetry.addData("reflection:", colorSensor1.alpha());
            telemetry.update();
        }
    }









    public void init1() {
        telemetry.addData("software version:", 1);
        try {
            hDrive = initializeMotor("hDrive", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);//(DcMotor) hardwareMap.get("hDrive");
            leftMotor = initializeMotor("leftMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            rightMotor = initializeMotor("rightMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            shootMotor = initializeMotor("shootMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            leftServo = (Servo) hardwareMap.get("leftServo");//initializeServo("leftServo", INIT_LEFT_SERVO_POS);
            rightServo = (Servo) hardwareMap.get("rightServo");//initializeServo("rightServo", INIT_RIGHT_SERVO_POS);
            shootServo = (Servo) hardwareMap.get("shootServo");
            cageMotor = initializeMotor("cageMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            colorSensor1 = initializeColorSensor("colorSensor1");
            beaconSensor = initializeColorSensor("beacon sensor");
            //sonar = (UltrasonicSensor) hardwareMap.get("sonar");


        } catch (Exception exception) {
            telemetry.addData("Error: ", "Error initializing one or more motors/servos");
            telemetry.addData("Exception Info: ", exception.getMessage());
            telemetry.update();
        }


        leftServo.setPosition(INIT_LEFT_SERVO_POS);
        rightServo.setPosition(INIT_RIGHT_SERVO_POS);
        shootServo.setPosition(INIT_SHOOT_SERVO_POS);

    }

    public Servo initializeServo(String hardwareMapName, double InitialPosition) {
        Servo servo = null;
        try {
            servo = hardwareMap.servo.get("hardwareMapName");
            servo.setPosition(InitialPosition);
            telemetry.addData("Servo initialized: ", hardwareMapName);
            updateTelemetry(telemetry);
        } catch (Exception exception) {
            telemetry.addData("Could not initialize servo: ", exception.getMessage());
            telemetry.addData("Servo not initialized: ", hardwareMapName);
            updateTelemetry(telemetry);
        }
        return servo;
    }
    public ColorSensor initializeColorSensor(String colorSensorName)
    {
        try {
            ColorSensor temp = hardwareMap.colorSensor.get("colorSensor1");
            telemetry.addData("Success:", " colorSensor1 found!");
            return temp;
        } catch (Exception exception) {
            telemetry.addData("Error: ", exception.getLocalizedMessage());
            return new DummyColorSensor(colorSensorName);
        }
    }

    public DcMotor initializeMotor(String hardwareMapName, double initialPower, DcMotor.RunMode runMode, boolean brakeWhenZero) {
        DcMotor motor = null;
        try {
            motor = hardwareMap.dcMotor.get(hardwareMapName);//get motor from hardware map
            motor.setMode(runMode);//set the runmode
            if (brakeWhenZero) {
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//set the motor to brake when power is zero
            } else {
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);//set the motor to rotate freely when power is zero
            }
            motor.setPower(initialPower);//set the motor power
            telemetry.addData("Success:", hardwareMapName.concat(" found!"));
            return motor;//return success
        } catch (Exception exception) {//if something went wrong
            telemetry.addData("Error: ", exception.getMessage().concat(exception.getLocalizedMessage()));
            return motor;//return failure
        }
    }

    class DummyColorSensor implements ColorSensor
    {
        String Name;
        DummyColorSensor(String name){
            Name = name;
        }


        @Override
        public int red() {
            telemetry.addData("No Red Data: ", Name.concat(" could not be found!"));
            return 0;
        }

        @Override
        public int green() {
            telemetry.addData("No Green Data: ", Name.concat(" could not be found!"));
            return 0;
        }

        @Override
        public int blue() {
            telemetry.addData("No Blue Data: ", Name.concat(" could not be found!"));
            return 0;
        }

        @Override
        public int alpha() {
            telemetry.addData("No Alpha Data: ", Name.concat(" could not be found!"));
            return 0;
        }

        @Override
        public int argb() {
            telemetry.addData("Error: ", Name.concat(" could not be found!"));
            return 0;
        }

        @Override
        public void enableLed(boolean enable) {

        }

        @Override
        public void setI2cAddress(I2cAddr newAddress) {

        }

        @Override
        public I2cAddr getI2cAddress() {
            return null;
        }

        @Override
        public Manufacturer getManufacturer() {
            return null;
        }

        @Override
        public String getDeviceName() {
            return Name;
        }

        @Override
        public String getConnectionInfo() {
            return null;
        }

        @Override
        public int getVersion() {
            return 0;
        }

        @Override
        public void resetDeviceConfigurationForOpMode() {

        }

        @Override
        public void close() {

        }
    }
}