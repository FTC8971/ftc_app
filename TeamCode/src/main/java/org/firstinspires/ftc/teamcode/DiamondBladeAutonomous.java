package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by LCSTEAM3 on 12/13/2016.
 */
@Autonomous(name="Diamondbot Autonomous", group="Autonomous")

class DiamondBladeAutonomous extends OpMode
{
    DcMotor hDrive;
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor shootMotor;
    Servo leftServo;
    Servo rightServo;
    DcMotor cageMotor;
    DcMotor liftMotor;
    DcMotor scoopMotor;
    ColorSensor colorSensor1;

    final double INIT_LEFT_SERVO_POS = 1;
    final double INIT_RIGHT_SERVO_POS = 0;
    int step = 1; // step = autonomous steps


    @Override
    public void init()
    {
        hDrive = initializeMotor("hDrive", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);//(DcMotor) hardwareMap.get("hDrive");
        leftMotor = initializeMotor("leftMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        rightMotor = initializeMotor("rightMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        //shootMotor = initializeMotor("shootMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        leftServo = (Servo) hardwareMap.get("leftServo");
        rightServo = (Servo) hardwareMap.get("rightServo");
        cageMotor = initializeMotor("cageMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        //liftMotor = initializeMotor("liftMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        //scoopMotor = initializeMotor("scoopMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);

        colorSensor1 = initializeColorSensor("colorSensor1");

        telemetry.update();

        hDrive.setPower(0);
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        //shootMotor.setPower(0);
        leftServo.setPosition(INIT_LEFT_SERVO_POS);
        rightServo.setPosition(INIT_RIGHT_SERVO_POS);
        cageMotor.setPower(0);
        //liftMotor.setPower(0);
        //scoopMotor.setPower(0);

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

    @Override
    public void loop()
    {
        colorSensor1.enableLed(false);

        switch(step) //each case represents autonomous steps
        {
            case 1: // first step
                leftMotor.setPower(1);
                rightMotor.setPower(-1);
                findWhite();

            case 2:
                leftMotor.setPower(0);


        }
    }

    public void findWhite()//method to find white tape
    {
        if(colorSensor1.alpha() >= 240) //robot stops when it reaches white tape
        {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
            step = 2; //starts step 2 case in loop
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
