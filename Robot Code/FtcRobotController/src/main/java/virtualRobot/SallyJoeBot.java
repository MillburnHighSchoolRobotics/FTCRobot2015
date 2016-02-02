package virtualRobot;

import java.util.ArrayList;

import virtualRobot.components.ColorSensor;
import virtualRobot.components.Motor;
import virtualRobot.components.Sensor;
import virtualRobot.components.Servo;

/**
 * Created by DOSullivan on 10/5/15.
 * Can be accesed by
 */
public class SallyJoeBot implements AutonomousRobot, TeleopRobot {

    private Motor driveLeftMotor, driveRightMotor, tapeMeasureBackMotor, tapeMeasureFrontMotor;
    private Sensor driveLeftMotorEncoder, driveRightMotorEncoder, tapeMeasureFrontMotorEncoder, tapeMeasureBackMotorEncoder;
    private Sensor headingSensor, pitchSensor, rollSensor, ultrasoundSensor;
    private ColorSensor colorSensor;
    private Servo tapeMeasureServo, flipperLeftServo, flipperRightServo, frontShieldServo, backShieldServo, dumperServo, buttonPusherServo, hangServo;

    private JoystickController joystickController1, joystickController2;

    private ArrayList<String> commands;
    private ArrayList<String> robotProgress;


    public SallyJoeBot() {
        driveLeftMotor = new Motor();
        driveRightMotor = new Motor();
        tapeMeasureBackMotor = new Motor();
        tapeMeasureFrontMotor = new Motor();

        driveLeftMotorEncoder = new Sensor();
        driveRightMotorEncoder = new Sensor();
        tapeMeasureFrontMotorEncoder = new Sensor();
        tapeMeasureBackMotorEncoder = new Sensor();

        headingSensor = new Sensor();
        colorSensor = new ColorSensor();
        pitchSensor = new Sensor();
        rollSensor = new Sensor();
        ultrasoundSensor = new Sensor();
        
        tapeMeasureServo = new Servo();
        flipperLeftServo = new Servo();
        flipperRightServo = new Servo();
        frontShieldServo = new Servo();
        backShieldServo = new Servo();
        dumperServo = new Servo();
        buttonPusherServo = new Servo();
        hangServo = new Servo();

        joystickController1 = new JoystickController();
        joystickController2 = new JoystickController();

        robotProgress = new ArrayList<String>();
        commands = new ArrayList<String>();



        tapeMeasureServo.setPosition(0.485);
    }

    public synchronized Motor getDriveRightMotor() {
        return driveRightMotor;
    }

    public synchronized Motor getDriveLeftMotor() {
        return driveLeftMotor;
    }

    public synchronized Motor getTapeMeasureBackMotor() {
        return tapeMeasureBackMotor;
    }

    public synchronized Motor getTapeMeasureFrontMotor() {
        return tapeMeasureFrontMotor;
    }

    public synchronized Sensor getDriveRightMotorEncoder() {
        return driveRightMotorEncoder;
    }

    public synchronized Sensor getDriveLeftMotorEncoder() {
        return driveLeftMotorEncoder;
    }

    public synchronized Sensor getTapeMeasureFrontMotorEncoder() {
        return tapeMeasureFrontMotorEncoder;
    }

    public synchronized Sensor getTapeMeasureBackMotorEncoder() {
        return tapeMeasureBackMotorEncoder;
    }

    public synchronized Sensor getHeadingSensor() {
        return headingSensor;
    }

    public synchronized ColorSensor getColorSensor() {
        return colorSensor;
    }

    public synchronized Sensor getPitchSensor() {
        return pitchSensor;
    }

    public synchronized Sensor getRollSensor() {
        return rollSensor;
    }

    public synchronized Sensor getUltrasoundSensor() {
        return ultrasoundSensor;
    }

    public synchronized Servo getTapeMeasureServo() {
        return tapeMeasureServo;
    }

    public synchronized Servo getFlipperLeftServo() {
        return flipperLeftServo;
    }

    public synchronized Servo getFlipperRightServo() {
        return flipperRightServo;
    }

    public synchronized Servo getFrontShieldServo() {
        return frontShieldServo;
    }

    public synchronized Servo getBackShieldServo() {
        return backShieldServo;
    }

    public synchronized Servo getDumperServo() {
        return dumperServo;
    }

    public synchronized Servo getButtonPusherServo() {
        return buttonPusherServo;
    }

    public synchronized Servo getHangServo() {
        return hangServo;
    }

    public synchronized JoystickController getJoystickController1() {
        return joystickController1;
    }

    public synchronized JoystickController getJoystickController2() {
        return joystickController2;
    }

    public synchronized void addToProgress (String s) {
        robotProgress.add(s);
    }

    public synchronized ArrayList<String> getProgress () {
        return robotProgress;
    }

}
