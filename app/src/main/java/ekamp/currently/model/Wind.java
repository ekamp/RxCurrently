package ekamp.currently.model;

/**
 * Contains information relating to Wind conditions, such as wind speed and direction.
 *
 * @author Erik Kamp
 * @since 09/20/2015
 */
public class Wind {

    public static final double DEFAULT_TEMP_VALUE = 0;

    private double speed, direction;

    public Wind(double speed, double direction) {
        this.speed = speed;
        this.direction = direction;
    }

    public static Wind getDefaultValue() {
        return new Wind(DEFAULT_TEMP_VALUE, DEFAULT_TEMP_VALUE);
    }

    public double getSpeed() {
        return speed;
    }

    public String getSpeedForDisplay(){
        return Double.toString(speed);
    }

    public double getDirection() {
        return direction;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "direction=" + direction +
                ", speed=" + speed +
                '}';
    }
}
