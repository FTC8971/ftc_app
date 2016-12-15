package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.hitechnic.HiTechnicNxtColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by LCSTEAM3 on 12/10/2016.
 */
@TeleOp(name="DiamondBladeOpMode", group="OpMode")
public class DiamondBladeOpMode extends OpMode
{
    DcMotor hDrive;
    DcMotor leftMotor;
    DcMotor rightMotor;
    DcMotor drawBackMotor;
    DcMotor shootMotor;
    Servo leftServo;
    Servo rightServo;
    DcMotor cageMotor;
    DcMotor liftMotor;
    DcMotor scoopMotor;


    /* Hey guys, it's liam. I've tried to make my comments stand out like so:
    ********************************<<<<>>>>***********************************
    * anytime you see that^ it's a comment from me to help you understand the
    * code to get the color sensors to work. Good luck!!!
     */

    /*********************************<<<<>>>>***********************************/
    /* First, we need something to hold the color sensor, so we declare a ColorSensor variable */
    ColorSensor colorSensor1;
    /*********************************<<<<>>>>***********************************/

    final double INIT_LEFT_SERVO_POS = 1;
    final double INIT_RIGHT_SERVO_POS = 0;
    boolean isDrawing = false;

    @Override
    public void init()
    {
        try {
            hDrive = initializeMotor("hDrive", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);//(DcMotor) hardwareMap.get("hDrive");
            leftMotor = initializeMotor("leftMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            rightMotor = initializeMotor("rightMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            drawBackMotor = initializeMotor("drawBackMotor", 0, DcMotor.RunMode.RUN_USING_ENCODER, true);
            shootMotor = initializeMotor("shootMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            leftServo = initializeServo("leftServo", INIT_LEFT_SERVO_POS);
            rightServo = initializeServo("rightServo", INIT_RIGHT_SERVO_POS);
            cageMotor = initializeMotor("cageMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        }
        catch(Exception exception){
            telemetry.addData("Error: ", "Error initializing one or more motors/servos");
            telemetry.addData("Exception Info: ", exception.getMessage());
            telemetry.update();
        }

        //liftMotor = initializeMotor("liftMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        //scoopMotor = initializeMotor("scoopMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);

        /*********************************<<<<START>>>>***********************************/
        /* Second, we'll want to get the color sensor object from the hardware map.
         * This will allow us to communicate with the physical color sensor on the robot.
         * I've named it "colorSensor1" because that's what I named it in the configuration
         * on the phone under "legacy module 2". Feel free to come up with a more creative
         * name. See the method implementation below for more details. */

            colorSensor1 = initializeColorSensor("colorSensor1");



        /*********************************<<<<END>>>>***********************************/

        //telemetry.update();

            /*****unnecessary. the "initializeMotor" function has always included an input for motor power.******
            hDrive.setPower(0);
            leftMotor.setPower(0);
            rightMotor.setPower(0);
            drawBackMotor.setPower(0);
            shootMotor.setPower(0);
            cageMotor.setPower(0);
            liftMotor.setPower(0);
            scoopMotor.setPower(0);
            */
            /*****obsolete; included in the new "initializeServo" function******
             leftServo.setPosition(INIT_LEFT_SERVO_POS);
             rightServo.setPosition(INIT_RIGHT_SERVO_POS);
             */
        
    }

    /*********************************<<<<START>>>>***********************************/
    /* This method will retrieve a color sensor object from the hardware map. Just
     * pass it a string that matches the name of the sensor in the configuration
     * file. As a bonus, I've also added some hocus-pocus so that the code doesn't
     * blow up if it can't find the sensor. */
    public ColorSensor initializeColorSensor(String colorSensorName){
        try {
            ColorSensor temp = hardwareMap.colorSensor.get("colorSensor1");
            telemetry.addData("Success:", " colorSensor1 found!");
            return temp;
        } catch (Exception exception) {
            telemetry.addData("Error: ", exception.getLocalizedMessage());
            return new DummyColorSensor(colorSensorName);
        }
    }
    /*********************************<<<<END>>>>***********************************/

    public Servo initializeServo(String hardwareMapName){
        Servo servo = null;
        try {
            servo = hardwareMap.servo.get("hardwareMapName");
            telemetry.addData("Servo initialized: ", hardwareMapName);
            updateTelemetry(telemetry);
        }
        catch(Exception exception){
            telemetry.addData("Could not initialize servo: ", exception.getMessage());
            telemetry.addData("Servo not initialized: ", hardwareMapName);
            updateTelemetry(telemetry);
        }
        return servo;
    }
    public Servo initializeServo(String hardwareMapName, double InitialPosition){
        Servo servo = null;
        try {
            servo = hardwareMap.servo.get("hardwareMapName");
            servo.setPosition(InitialPosition);
            telemetry.addData("Servo initialized: ", hardwareMapName);
            updateTelemetry(telemetry);
        }
        catch(Exception exception){
            telemetry.addData("Could not initialize servo: ", exception.getMessage());
            telemetry.addData("Servo not initialized: ", hardwareMapName);
            updateTelemetry(telemetry);
        }
        return servo;
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

    @Override
    public void loop()
    {

        double x = gamepad1.left_stick_x; //drive function
        double y = gamepad1.left_stick_y;
        leftMotor.setPower(y * ((x + 1) / 2));
        rightMotor.setPower(y * ((x - 1) / 2));

        hDrive.setPower(gamepad1.right_stick_x);//hDrive code

        if(gamepad1.right_bumper) //if true
        {
            rightServo.setPosition(.3);
        }
        if(!gamepad1.right_bumper) // if false
        {
            rightServo.setPosition(-0.05);
        }

        if(gamepad1.left_bumper)
        {
            leftServo.setPosition(.65);
        }
        if(!gamepad1.left_bumper)
        {
            leftServo.setPosition(1);
        }


        if(gamepad2.left_trigger > 0) //cage motor code
        {
            cageMotor.setPower(gamepad2.left_trigger);
        }
        else if(gamepad2.right_trigger > 0)
        {
            cageMotor.setPower(-1*(gamepad2.right_trigger));
        }
        else
        {
            cageMotor.setPower(0);
        }



        /*********************************<<<<START>>>>***********************************/
        /* Third, we get to interact with our sensor! ColorSensors have 4 fields that
         * will be useful to you. Red, green, blue, and alpha. Each describes the saturation
         * of red, green, or blue light that the sensor sees. Alpha is related to the
         * intensity of the light. Try experimenting with the sensor! */
        int alpha, red, green, blue;
        alpha = colorSensor1.alpha();
        red = colorSensor1.red();
        green = colorSensor1.green();
        blue = colorSensor1.blue();
        /*********************************<<<<END>>>>***********************************/

        if(gamepad1.a)
        {
           drawbackMethod();
        }
        if(gamepad1.b)
        {
            shootTheBall();
        }

        telemetry.addData("left encoder", String.valueOf(leftMotor.getCurrentPosition())); //encoder values
        telemetry.addData("right encoder", String.valueOf(rightMotor.getCurrentPosition()));
        telemetry.addData("hdrive encoder", String.valueOf(hDrive.getCurrentPosition()));
        telemetry.addData("cage encoder", String.valueOf(cageMotor.getCurrentPosition()));
        //telemetry.addData("lift encoder", String.valueOf(liftMotor.getCurrentPosition()));
        telemetry.addData("left servo encoder", String.valueOf(leftServo.getPosition()));
        telemetry.addData("right servo encoder", String.valueOf(rightServo.getPosition()));
        //telemetry.addData("scoop encoder", String.valueOf(scoopMotor.getCurrentPosition()));
        telemetry.addData("shoot encoder", String.valueOf(shootMotor.getCurrentPosition()));
        telemetry.addData("draw-back encoder", String.valueOf(drawBackMotor.getCurrentPosition()));

        /*********************************<<<<START>>>>***********************************/
        /* Last but not least, we'll send all of that data we collected from the light
         * sensor to the driver station so that we can see what the each color level is.
         * I've also written a method for you to use that formats the RGBAlpha data for
         * the driver station. */
        telemetry.addData("Sensor Data ", FormatRGBAlphaData(colorSensor1.getDeviceName(), red, green, blue, alpha));
        /*********************************<<<<END>>>>***********************************/

        telemetry.update(); //updates encoders

    }

    /*********************************<<<<START>>>>***********************************/
    /* All of the string stuff is just a little formatting to make everything appear
     * neat on the driver station */
    String FormatRGBAlphaData(String name, int red, int green, int blue, int alpha){
        String preamble = name.concat(":");
        String formattedRed = " R;";
        String formattedGreen = " G;";
        String formattedBlue = " B;";
        String formattedAlpha = " A;";
        formattedRed = formattedRed.concat(String.valueOf(red));
        formattedBlue = formattedBlue.concat(String.valueOf(blue));
        formattedGreen = formattedGreen.concat(String.valueOf(green));
        formattedAlpha = formattedAlpha.concat(String.valueOf(alpha));
        return preamble.concat(formattedRed).concat(formattedGreen).concat(formattedBlue).concat(formattedAlpha);
    }

    public void drawbackMethod()
    {
        isDrawing = true;
        int start = drawBackMotor.getCurrentPosition();
        drawBackMotor.setTargetPosition(start+100);
        shootMotor.setTargetPosition(shootMotor.getCurrentPosition()+100);
        drawBackMotor.setTargetPosition(start);
        isDrawing = false;
    }
    public void shootTheBall()
    {
        if (!isDrawing){
            shootMotor.setTargetPosition(shootMotor.getCurrentPosition()-100);
        }
    }
    /*********************************<<<<END>>>>***********************************/


    /*********************************<<<<START>>>>***********************************/
    /* What's going on here is a little too much to explain in comments, but we'll
     * get to it eventually. All you need to know about this is that it keeps your
     * code from blowing up if colorSensor1 can't be found. Look for a message on the
     * driver station about a missing sensor if something isn't working! */
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

    /*********************************<<<<END>>>>***********************************/
}
