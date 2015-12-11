package virtualRobot;

import android.util.Log;

/**
 * Created by shant on 10/27/2015.
 */
public class Rotate implements Command {
    private ExitCondition exitCondition;

    private double THRESHOLD = 1.0;
    private double KP = 0.2;
    private double KI = 0;
    private double KD = 0;

    private double TOLERANCE = 1.0;

    private double power;
    private double angleInDegrees;
    private RunMode runMode;
    private static double globalMaxPower = 1;
    private String name;

    private static double time;
    private static double timeLimit;
    
    private PIDController pidController;

    private static AutonomousRobot robot = Command.AUTO_ROBOT;

    public static void setGlobalMaxPower(double p) {
        globalMaxPower = p;
    }

    public Rotate () {
    	
    	power = globalMaxPower;
    	
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
        
        pidController = new PIDController(KP, KI, KD, THRESHOLD);

        runMode = RunMode.WITH_ANGLE_SENSOR;

        timeLimit = -1;
    }

    public Rotate (double target) {
        this();
        this.angleInDegrees = target;
        
        pidController.setTarget(target);
    }

    public Rotate (double angleInDegrees, double power) {
        this(angleInDegrees);
        this.power = power;
    }

    public Rotate (double angleInDegrees, double power, String name) {
        this (angleInDegrees, power);
        this.name = name;
    }

    private Rotate(double angleInDegrees, double power, double timeLimit) {
        this(angleInDegrees, power);
        this.timeLimit = timeLimit;
    }

    private Rotate (double angleInDegrees, double power, double timeLimit, String name) {
        this(angleInDegrees, power, timeLimit);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeLimit(double timeLimit) {
        this.timeLimit = timeLimit;
    }


    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getAngleInDegrees() {
        return angleInDegrees;
    }

    public void setAngleInDegrees(double angleInDegrees) {
        this.angleInDegrees = angleInDegrees;
    }


    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }


    public ExitCondition getExitCondition () {
        return exitCondition;
    }

    public void setTHRESHOLD (double THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    @Override
    public boolean changeRobotState() throws InterruptedException{
    	boolean isInterrupted = false;
        time = System.currentTimeMillis();
        switch (runMode) {
            case WITH_ANGLE_SENSOR:
                while (!exitCondition.isConditionMet() /*&& Math.abs(angleInDegrees - robot.getHeadingSensor().getValue()) > TOLERANCE*/ && (timeLimit == -1 || (System.currentTimeMillis() - time) < timeLimit)) {

                    double adjustedPower = pidController.getPIDOutput(robot.getHeadingSensor().getValue());
                    adjustedPower = Math.min(Math.max(adjustedPower, -1), 1);
                    adjustedPower *= power;

                    robot.getDriveLeftMotor().setPower(adjustedPower);
                    robot.getDriveRightMotor().setPower(-adjustedPower);

                    if (Thread.currentThread().isInterrupted()) {
                        isInterrupted = true;
                        break;
                    }

                    Log.e("PIDOUTPUT", "PID OUTPUT: " + Double.toString(adjustedPower) + "HEADING: " + Double.toString(robot.getHeadingSensor().getValue()));

                    try {
                        Thread.currentThread().sleep(10);
                    } catch (InterruptedException e) {
                        isInterrupted = true;
                        break;
                    }
                }
                break;
            case WITH_ENCODER:
                robot.getDriveLeftMotorEncoder().clearValue();
                robot.getDriveRightMotorEncoder().clearValue();
                while (!exitCondition.isConditionMet() && Math.abs(Math.abs(pidController.getTarget()) - (Math.abs(robot.getDriveLeftMotorEncoder().getValue()) + Math.abs(robot.getDriveRightMotorEncoder().getValue())) / 2) > 20){
                    robot.getDriveLeftMotor().setPower(Math.signum(angleInDegrees)*power);
                    robot.getDriveRightMotor().setPower(-Math.signum(angleInDegrees)*power);

                    if (Thread.currentThread().isInterrupted()) {
                        isInterrupted = true;
                        break;
                    }

                    Thread.currentThread().sleep(10);
                }
                break;
        }

    	robot.getDriveRightMotor().setPower(0);
        robot.getDriveLeftMotor().setPower(0);
        
        return isInterrupted;
        
    }

    public enum RunMode {
        WITH_ANGLE_SENSOR,
        WITH_ENCODER
    }
}
