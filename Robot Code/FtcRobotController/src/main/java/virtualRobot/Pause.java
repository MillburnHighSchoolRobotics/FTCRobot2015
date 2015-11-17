package virtualRobot;

/**
 * Created by Yanjun on 11/12/2015.
 */
public class Pause implements Command {

    private int nMillis;

    public Pause(int time) {
        nMillis = time;
    }

    @Override
    public boolean changeRobotState() throws InterruptedException {
        boolean isInterrupted = true;

        try {
            Thread.currentThread().sleep(nMillis);
        } catch (InterruptedException e) {
            isInterrupted = true;
        }

        return isInterrupted;
    }
}