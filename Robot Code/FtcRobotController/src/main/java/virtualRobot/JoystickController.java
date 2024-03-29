package virtualRobot;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Created by Yanjun on 11/12/2015.
 */
public class JoystickController {
    AtomicReferenceArray<Boolean> down, pressed, released;
    AtomicReferenceArray<Double> stickValues;
    AtomicBoolean dpad_up, dpad_down, dpad_left, dpad_right;
    List<JoystickEvent> eventQueue;

    JoystickEvent curEvent, prevEvent;

    public JoystickController() {
        down = new AtomicReferenceArray<Boolean>(12);
        pressed = new AtomicReferenceArray<Boolean>(12);
        released = new AtomicReferenceArray<Boolean>(12);

        for (int i = 0; i < 12; i++) {
            down.set(i, false);
            pressed.set(i, false);
            released.set(i, false);
        }

        stickValues = new AtomicReferenceArray<Double>(10);

        for (int i = 0; i < 10; i++) {
            stickValues.set(i, 0.0);
        }

        dpad_up = new AtomicBoolean();
        dpad_down = new AtomicBoolean();
        dpad_left = new AtomicBoolean();
        dpad_right = new AtomicBoolean();

        dpad_up.set(false);
        dpad_down.set(false);
        dpad_left.set(false);
        dpad_right.set(false);

        eventQueue = Collections.synchronizedList(new ArrayList<JoystickEvent>());

        curEvent = null;
        prevEvent = null;
    }

    public synchronized void copyStates(Gamepad gamepad) throws RobotCoreException {
        JoystickEvent newEvent = new JoystickEvent(gamepad);
        if (eventQueue.size() != 0 && newEvent.equals(eventQueue.get(eventQueue.size()-1))) {
            return;
        }
        
        synchronized (this) {
        	eventQueue.add(newEvent);
        }
    }

    public synchronized void logicalRefresh() {
    	
    	synchronized (this) {
    	
	        if (eventQueue.size() == 0) {
	            curEvent = prevEvent;
	        } else {
	
	            prevEvent = curEvent;
	            curEvent = eventQueue.remove(0);
	        }
	
	        if (prevEvent != null && curEvent != null) {
	
	            for (int i = 0; i < 12; i++) {
	                pressed.set(i, !prevEvent.buttonStates[i] && curEvent.buttonStates[i]);
	                released.set(i, prevEvent.buttonStates[i] && !curEvent.buttonStates[i]);
	                down.set(i, curEvent.buttonStates[i]);
	            }
	
	            for (int i = 0; i < stickValues.length(); i++) {
	                stickValues.set(i, curEvent.stickValues[i]);
	            }
	
	            dpad_up.set(curEvent.dpad_up);
	            dpad_down.set(curEvent.dpad_down);
	            dpad_left.set(curEvent.dpad_left);
	            dpad_right.set(curEvent.dpad_right);
	        }
    	}
    }

    public synchronized boolean isDown(int buttonID) {
        return down.get(buttonID);
    }

    public synchronized boolean isPressed(int buttonID) {
        return pressed.get(buttonID);
    }

    public synchronized boolean isReleased(int buttonID) {
        return released.get(buttonID);
    }

    public synchronized double getValue(int ID) {
        return stickValues.get(ID);
    }

    public synchronized boolean isDpadUp() {
        return dpad_up.get();
    }

    public synchronized boolean isDpadDown() {
        return dpad_down.get();
    }

    public synchronized boolean isDpadLeft() {
        return dpad_left.get();
    }

    public synchronized boolean isDpadRight() {
        return dpad_right.get();
    }

    public static int BUTTON_X = 0;
    public static int BUTTON_A = 1;
    public static int BUTTON_Y = 2;
    public static int BUTTON_B = 3;
    public static int BUTTON_LB = 4;
    public static int BUTTON_RB = 5;
    public static int BUTTON_LT = 6;
    public static int BUTTON_RT = 7;
    public static int BUTTON_BACK = 8;
    public static int BUTTON_START = 9;
    public static int BUTTON_LEFT_STICK = 10;
    public static int BUTTON_RIGHT_STICK = 11;

    public static int X_1 = 0;
    public static int Y_1 = 1;
    public static int R_1 = 2;
    public static int THETA_1 = 3;
    public static int X_2 = 4;
    public static int Y_2 = 5;
    public static int R_2 = 6;
    public static int THETA_2 = 7;
    public static int RT_PRESSURE = 8;
    public static int LT_PRESSURE = 9;
}
