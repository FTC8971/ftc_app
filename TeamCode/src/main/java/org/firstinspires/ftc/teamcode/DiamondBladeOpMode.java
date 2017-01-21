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
<<<<<<< HEAD
@TeleOp(name = "DiamondBladeOpMode", group = "OpMode")
public class DiamondBladeOpMode extends OpMode {
=======
@TeleOp(name="DiamondBladeOpMode", group="OpMode")
public class DiamondBladeOpMode extends OpMode
{
    //***********initialize motor variables
>>>>>>> origin/master
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

    boolean automatedCage = true;     //variable to determine cage motor behavior
    int goal = 220;
    cageStates cageState = cageStates.inactive;

    enum cageStates {active, inactive}

    enum State {ResetEncoders, StartEncoders, CheckEncoderStatus, Done, WaitForButton}

    State state = State.WaitForButton;

    int cagePosition = 0;
    double cageMotorPower = 0;


    /*********************************
     * <<<<>>>>
     ***********************************/
    /* First, we need something to hold the color sensor, so we declare a ColorSensor variable */
    ColorSensor colorSensor1;
    /*********************************
     * <<<<>>>>
     ***********************************/

<<<<<<< HEAD
    final double INIT_LEFT_SERVO_POS = 0;
    final double INIT_RIGHT_SERVO_POS = 1;
    final double INIT_SHOOT_SERVO_POS = 0.3;
=======
    boolean isWinding = false; //used to keep the shooter from launching before the cord is unwound
    final double INIT_LEFT_SERVO_POS = 1;//initial servo positions for the
    final double INIT_RIGHT_SERVO_POS = 0;//beacon triggers
>>>>>>> origin/master
    boolean isDrawing = false;
    int sGoal = 0;

    @Override
<<<<<<< HEAD
    public void init() {
        telemetry.addData("software version:", 3);
        try {
=======
    public void init()//runs once when 'init' button is pressed
    {
        try {//error catching
            //**************initialize all the motors, using function further down in the code.*******************
>>>>>>> origin/master
            hDrive = initializeMotor("hDrive", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);//(DcMotor) hardwareMap.get("hDrive");
            leftMotor = initializeMotor("leftMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            rightMotor = initializeMotor("rightMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            shootMotor = initializeMotor("shootMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
<<<<<<< HEAD
            leftServo = (Servo) hardwareMap.get("leftServo"); //initializeServo("leftServo", INIT_LEFT_SERVO_POS);
            rightServo = (Servo) hardwareMap.get("rightServo");//initializeServo("rightServo", INIT_RIGHT_SERVO_POS);
            shootServo = (Servo) hardwareMap.get("shootServo");//initializeServo("shootServo",INIT_SHOOT_SERVO_POS);
            cageMotor = initializeMotor("cageMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        } catch (Exception exception) {
            telemetry.addData("Error: ", "Error initializing one or more motors/servos");
            telemetry.addData("Exception Info: ", exception.getMessage());
=======
            cageMotor = initializeMotor("cageMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            leftServo = (Servo) hardwareMap.get("leftServo");//initializeServo("leftServo", INIT_LEFT_SERVO_POS);
            rightServo = (Servo) hardwareMap.get("rightServo");//initializeServo("rightServo", INIT_RIGHT_SERVO_POS);
            //liftMotor = initializeMotor("liftMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
            //scoopMotor = initializeMotor("scoopMotor", 0, DcMotor.RunMode.RUN_WITHOUT_ENCODER, true);
        }
        catch(Exception exception){
            telemetry.addData("Error: ", "Error initializing one or more motors/servos");//tell the driver something went wrong
            telemetry.addData("Exception Info: ", exception.getMessage());//if this happens, CHECK THE NAMES IN THE HARDWARE MAP FIRST!
>>>>>>> origin/master
            telemetry.update();
        }



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
        //*****obsolete; included in the new "initializeServo" function******
        leftServo.setPosition(INIT_LEFT_SERVO_POS);
        rightServo.setPosition(INIT_RIGHT_SERVO_POS);
        shootServo.setPosition(INIT_SHOOT_SERVO_POS);
        while (cageMotor.getCurrentPosition() < 220) {
            cageMotor.setPower(0.5);
            telemetry.addData("cage encoder:", cageMotor.getCurrentPosition());
            telemetry.update();
        }
        cageMotor.setPower(0);


    }

    /*********************************
     * <<<<START>>>>
     ***********************************/
    /* This method will retrieve a color sensor object from the hardware map. Just
     * pass it a string that matches the name of the sensor in the configuration
     * file. As a bonus, I've also added some hocus-pocus so that the code doesn't
     * blow up if it can't find the sensor. */
    public ColorSensor initializeColorSensor(String colorSensorName) {
        try {
            ColorSensor temp = hardwareMap.colorSensor.get("colorSensor1");
            telemetry.addData("Success:", " colorSensor1 found!");
            return temp;
        } catch (Exception exception) {
            telemetry.addData("Error: ", exception.getLocalizedMessage());
            return new DummyColorSensor(colorSensorName);
        }
    }

    /*********************************
     * <<<<END>>>>
     ***********************************/

    public Servo initializeServo(String hardwareMapName) {
        Servo servo = null;
        try {
            servo = hardwareMap.servo.get("hardwareMapName");
            telemetry.addData("Servo initialized: ", hardwareMapName);
            updateTelemetry(telemetry);
        } catch (Exception exception) {
            telemetry.addData("Could not initialize servo: ", exception.getMessage());
            telemetry.addData("Servo not initialized: ", hardwareMapName);
            updateTelemetry(telemetry);
        }
        return servo;
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
<<<<<<< HEAD

=======
    //*******************function to initialize motors***************************
>>>>>>> origin/master
    public DcMotor initializeMotor(String hardwareMapName, double initialPower, DcMotor.RunMode runMode, boolean brakeWhenZero) {
        DcMotor motor = null;//initialize the motor variable
        try {//error catching so the code doesn't crash if the motor isn't found
            motor = hardwareMap.dcMotor.get(hardwareMapName);//get motor from hardware map
            motor.setMode(runMode);//set the runmode
            if (brakeWhenZero) {//based on the input to the function...
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//set the motor to brake when power is zero
            } else {
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);//set the motor to rotate freely when power is zero
            }
            motor.setPower(initialPower);//set the motor power
            telemetry.addData("Success:", hardwareMapName.concat(" found!"));//tell the driver the motor was initialized successfully
            return motor;//return success
        } catch (Exception exception) {//if something went wrong
            telemetry.addData("Error: ", exception.getMessage().concat(exception.getLocalizedMessage()));//tell the driver what happened
            return motor;//return failure
        }
    }

    @Override
<<<<<<< HEAD
    public void loop() {

//        double x = gamepad1.left_stick_x; //drive function
//        double y = gamepad1.left_stick_y;
//        leftMotor.setPower(y * ((x + 1) / 2));
//        rightMotor.setPower(y * ((x - 1) / 2));
//
//        if (gamepad1.right_stick_x > .01 || gamepad1.right_stick_x < -.01){
//            hDrive.setPower(gamepad1.right_stick_x);//hDrive code
//        } else {
//            hDrive.setPower(0);
//        }

        leftMotor.setPower(gamepad1.left_stick_y);
        rightMotor.setPower(-1 * (gamepad1.right_stick_y));

        if (gamepad1.dpad_left) {
            hDrive.setPower(-1);//hDrive code
        } else if (gamepad1.dpad_right) {
            hDrive.setPower(1);
        } else {
            hDrive.setPower(0);
        }


        leftServo.setPosition(gamepad1.left_trigger);
        rightServo.setPosition(1 - gamepad1.right_trigger);

=======
    public void loop()//this loops continuously while the code is running
    {
        //***************drive function******************
        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        leftMotor.setPower(y * ((x + 1) / 2));//some code to use tank drive with
        rightMotor.setPower(y * ((x - 1) / 2));//one joystick

        hDrive.setPower(gamepad1.right_stick_x);//while using the other joystick to control the H-Drive
        //**************servo trigger controls***************
        if(gamepad1.right_bumper) //if true
        {
            rightServo.setPosition(.3);
        }
        if(!gamepad1.right_bumper) // if false
        {
            rightServo.setPosition(-0.05);
        }
>>>>>>> origin/master

        if (gamepad2.right_bumper && cageState == cageStates.inactive) {
            cageState = cageStates.active;
            goal = goal + 3360;
        }
        if (gamepad2.left_bumper) {
            cageState = cageStates.inactive;
        }

<<<<<<< HEAD

        if (cageState == cageStates.active) {
            cagePosition = cageMotor.getCurrentPosition();
            telemetry.addData("Cage Encoder:", cagePosition);
            if (cagePosition < goal - 15) {
                cageMotorPower = (((double) goal - cagePosition) * 1.5 / 3300) + 0.2;
                cageMotor.setPower(cageMotorPower);
                telemetry.addData("target power: ", cageMotorPower);
            }
            if (cagePosition > (goal - 15) && cagePosition < (goal + 15)) {
                cageState = cageStates.inactive;
            }
            if (cagePosition > goal + 15) {
                cageMotor.setPower(-.2);
            }
=======
        //*******************control the cage motor (lift) with the triggers on gamepad 2************
        if(gamepad2.left_trigger > 0) //cage motor code
        {
            cageMotor.setPower(gamepad2.left_trigger);
>>>>>>> origin/master
        }

        if (cageState == cageStates.inactive) {
            if (gamepad2.right_trigger > 0) //cage motor code
            {
                cageMotor.setPower(gamepad2.right_trigger);
            } else if (gamepad2.left_trigger > 0) {
                cageMotor.setPower(-1 * (gamepad2.left_trigger));
            } else {
                cageMotor.setPower(0);
            }
        }


        if (gamepad2.a) { //shoot code that works

            sGoal = sGoal - 1100;
            while (shootMotor.getCurrentPosition() > sGoal) {
                leftMotor.setPower(gamepad1.left_stick_y);
                rightMotor.setPower(-1 * (gamepad1.right_stick_y));

                if (gamepad1.dpad_left) {
                    hDrive.setPower(-1);//hDrive code
                } else if (gamepad1.dpad_right) {
                    hDrive.setPower(1);
                } else {
                    hDrive.setPower(0);
                }
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

        }

        if (gamepad2.x) {
            shootServo.setPosition(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            shootServo.setPosition(INIT_SHOOT_SERVO_POS);
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
<<<<<<< HEAD

//        if(gamepad2.a)
//        {
//            PullLauncherDown();
//        }
//        else if(gamepad2.b)
//        {
//            ReleaseLauncher();
//        }
//        else{
//            drawBackMotor.setPower(0);
//        }
//
//
//        if(gamepad2.y){
//            drawBackMotor.setPower(.25);
//        }
//        else if(gamepad2.x) {
//            shootMotor.setPower(-.25);
//        }
//        else{
//            shootMotor.setPower(0);
//        }

=======
        //*************control the shooter with the number buttons on the driver's gamepad************
        if(gamepad1.a)
        {
            PullLauncherDown();//draws launcher back, locks in place, unwinds. function below.
        }
        else if(gamepad1.b)
        {
            shootLauncher();//unlocks the launcher, causing it to shoot. function below.
        }
        else{
            drawBackMotor.setPower(0);//if neither of the buttons is pressed the motor should be off.
        }
        if(gamepad1.x){
           shootMotor.setPower(.25);
        }
        else if(gamepad1.y){
            shootMotor.setPower(-.25);
        }
        else{
            shootMotor.setPower(0);
        }
        //******************add encoder data to telemetry************************************
>>>>>>> origin/master
        telemetry.addData("left encoder", String.valueOf(leftMotor.getCurrentPosition())); //encoder values
        telemetry.addData("right encoder", String.valueOf(rightMotor.getCurrentPosition()));
        telemetry.addData("hdrive encoder", String.valueOf(hDrive.getCurrentPosition()));
        telemetry.addData("cage encoder", String.valueOf(cageMotor.getCurrentPosition()));
        //telemetry.addData("lift encoder", String.valueOf(liftMotor.getCurrentPosition()));
        telemetry.addData("left servo encoder", String.valueOf(leftServo.getPosition()));
        telemetry.addData("right servo encoder", String.valueOf(rightServo.getPosition()));
        //telemetry.addData("scoop encoder", String.valueOf(scoopMotor.getCurrentPosition()));
        telemetry.addData("shoot encoder", String.valueOf(shootMotor.getCurrentPosition()));
        // telemetry.addData("draw-back encoder", String.valueOf(drawBackMotor.getCurrentPosition()));

        /*********************************<<<<START>>>>***********************************/
        /* Last but not least, we'll send all of that data we collected from the light
         * sensor to the driver station so that we can see what the each color level is.
         * I've also written a method for you to use that formats the RGBAlpha data for
         * the driver station. */
        telemetry.addData("Sensor Data ", FormatRGBAlphaData(colorSensor1.getDeviceName(), red, green, blue, alpha));
        /*********************************<<<<END>>>>***********************************/

        telemetry.update(); //updates encoders

    }

    /*********************************
     * <<<<START>>>>
     ***********************************/
    /* All of the string stuff is just a little formatting to make everything appear
     * neat on the driver station */
    String FormatRGBAlphaData(String name, int red, int green, int blue, int alpha) {
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
<<<<<<< HEAD
//    public boolean PullLauncherDown() {
//        //*************************run motor for certain distance***************************
//        double goal = -5000; // or whatever you determine by testing
//        double initialposition = drawBackMotor.getCurrentPosition();
//        drawBackMotor.setPower(-1);//or -1 if it needs to turn the other direction
//        while (drawBackMotor.getCurrentPosition() > goal + initialposition) {//might have to be > if the motor turns the other direction
//            telemetry.addData("Encoder Value: ", drawBackMotor.getCurrentPosition());
//            updateTelemetry(telemetry);
//        }
//        drawBackMotor.setPower(0);
//        shootMotor.setPower(-0.25);
//        while (shootMotor.getCurrentPosition() > -50) {//might have to be > if the motor turns the other direction
//            telemetry.addData("Encoder Value: ", shootMotor.getCurrentPosition());
//            updateTelemetry(telemetry);
//        }
//        double goal2 = 5000; // or whatever you determine by testing
//        double initialposition2 = drawBackMotor.getCurrentPosition();
//        drawBackMotor.setPower(1);//or -1 if it needs to turn the other direction
//        while (drawBackMotor.getCurrentPosition() < goal2 + initialposition2) {//might have to be > if the motor turns the other direction
//            telemetry.addData("Encoder Value: ", drawBackMotor.getCurrentPosition());
//            updateTelemetry(telemetry);
//        }
//        return true;
//    }
//    public boolean ReleaseLauncher(){
//        shootMotor.setPower (0.25);
//        while (shootMotor.getCurrentPosition() < -30) {//might have to be > if the motor turns the other direction
//            telemetry.addData("Encoder Value: ", shootMotor.getCurrentPosition());
//            updateTelemetry(telemetry);
//        }
//        return true;
//    }


    //**************************end*****************************************************
=======
    //*****************function to pull back launcher******************
    public void PullLauncherDown(){
        isWinding = true;//when this is true the shoot button is disabled
        //*************************run drawBackMotor to pull back launcher***************************
        double goal = 3000; // or whatever you determine by testing
        double initialposition = drawBackMotor.getCurrentPosition();//so we don't have to wait to reset the encoder
        drawBackMotor.setPower(1);//turn the motor on
        while(drawBackMotor.getCurrentPosition() < goal+initialposition){//run the motor until the position reaches the goal
            telemetry.addData("Encoder Value: ", drawBackMotor.getCurrentPosition());//add telemetry data for the encoder value
            updateTelemetry(telemetry);//and push it to the driver station
        }
        //while the drawBackMotor is still running...
        //*************************run shootMotor to lock in place***************************
        goal = 30; // this is just a guess, as is the direction of the motor
        initialposition = shootMotor.getCurrentPosition();//so we don't have to wait to reset the encoder
        shootMotor.setPower(1);//turn on the motor
        while(shootMotor.getCurrentPosition() < goal+initialposition){//run the motor until it reaches the goal
            telemetry.addData("Encoder Value: ", shootMotor.getCurrentPosition());//and send the encoder value
            updateTelemetry(telemetry);//                                           to the driver station
        }
        shootMotor.setPower(0);//turn off the motor
        //*************************run drawBackMotor the other way to unwind the cord***************************
        goal = -3000; // or whatever you determine by testing
        initialposition = drawBackMotor.getCurrentPosition();//so we don't have to reset the encoders
        drawBackMotor.setPower(-1);//turn on the motor in reverse
        while(drawBackMotor.getCurrentPosition() > goal+initialposition){//run till it reaches the goal (note the > symbol, since the motor is turning the other way)
            telemetry.addData("Encoder Value: ", drawBackMotor.getCurrentPosition());//send data
            updateTelemetry(telemetry);//                                              to the driver
        }
        drawBackMotor.setPower(0);//turn off the motor
        isWinding = false;//reenable the shoot button
        //**************************end*****************************************************

    }
    //******************function to shoot launcher*******************
    public void shootLauncher(){
        //*************************run shootMotor to release***************************
        if (!isWinding){//as long as the PullLauncherDown function isn't still running
            double goal = -30; // or whatever you determine by testing
            double initialposition = shootMotor.getCurrentPosition();
            shootMotor.setPower(-1);//or -1 if it needs to turn the other direction
            while(shootMotor.getCurrentPosition() > goal+initialposition){//might have to be > if the motor turns the other direction
                telemetry.addData("Encoder Value: ", shootMotor.getCurrentPosition());
                updateTelemetry(telemetry);
            }
            shootMotor.setPower(0);
        }else{//otherwise
            telemetry.addData("Conflict: ", "Launcher still preparing for shot");//tell the driver he can't do that right now
            updateTelemetry(telemetry);
        }

    }
    //*************************run motor for certain distance***************************
    public boolean moveMotorDistance(DcMotor Motor, double encoderDistance, boolean isDirectionForward){
        double initialposition = Motor.getCurrentPosition();
        if (isDirectionForward){
            Motor.setPower(1);
            while(Motor.getCurrentPosition() < encoderDistance+initialposition){//might have to be > if the motor turns the other direction
                telemetry.addData("Encoder Value: ", Motor.getCurrentPosition());
                updateTelemetry(telemetry);
            }

            drawBackMotor.setPower(0);
            return true;
        }
        else {
            Motor.setPower(-1);
            while (Motor.getCurrentPosition() > encoderDistance + initialposition) {
                telemetry.addData("Encoder Value: ", Motor.getCurrentPosition());
                updateTelemetry(telemetry);
            }
            drawBackMotor.setPower(0);
            return true;
        }


    }
>>>>>>> origin/master
    /*
    /*********************************<<<<END>>>>***********************************/


    /*********************************
     * <<<<START>>>>
     ***********************************/
    /* What's going on here is a little too much to explain in comments, but we'll
     * get to it eventually. All you need to know about this is that it keeps your
     * code from blowing up if colorSensor1 can't be found. Look for a message on the
     * driver station about a missing sensor if something isn't working! */
    class DummyColorSensor implements ColorSensor {
        String Name;

        DummyColorSensor(String name) {
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
