package survivor.model.gameBasics;

import org.apache.log4j.Logger;
import survivor.model.gameConstants.TemperatureStatus;

public abstract class Temperature implements TemperatureStatus {
    private static final Logger LOG = Logger.getLogger(Temperature.class);

    private static int lastHour = 8;

    private static double temperature = 21;

    private static double outsideTemperature;
    private static double roomTemperature;
    private static double inhabitedTemperature;

    private static double globalTemperature = 18;
    private static double changeSpeed = .7;

    public static void setTemperature(boolean isRoom, boolean inhabited) {
        int hour = Time.getHour();
        int day = Integer.parseInt(Time.getDay());

        if (hour != lastHour) {
            lastHour = hour;

            if (hour == 0) {
                changeSpeed = ((Math.random() + 1) / 2);
                LOG.info("Скорость изменения температуры поменялась на: " + changeSpeed);
            }

            LOG.info("Высчитываем температуру. Изначальная: " + temperature);

            if (hour < 16 && hour > 3) globalTemperature += changeSpeed;
            if (hour > 15 || hour < 4) globalTemperature -= changeSpeed;
        }

        outsideTemperature = globalTemperature - 4 + day / 5;

        LOG.info("Мы в комнате: " + isRoom + ". Мы в жилом помещении: " + inhabited + ". Температура: " + temperature);
        inhabitedTemperature = 20 + (outsideTemperature - 10) / 2;
        roomTemperature = 15 + (outsideTemperature - 10) / 4;

        if (isRoom && inhabited) temperature = inhabitedTemperature;
        else if (isRoom) temperature = roomTemperature;
        else temperature = outsideTemperature;

        LOG.info("Сейчас время " + hour + ". Температура на улице " + outsideTemperature + ". В помещении: " + roomTemperature + ". В жилом: " + inhabitedTemperature);
    }

    public static void changeGlobalTemperature(int mod) {
        globalTemperature += mod;
    }

    public static int getAccurateTemperature() {
        return (int) temperature;
    }

    public static String getPerceivedTemperature() {
        if (temperature > 29) return HOT;
        if (temperature > 19) return HEAT;
        if (temperature > 14) return NORMAL;
        if (temperature > 9) return COOL;
        if (temperature > 4) return COLD;
        if (temperature > -1) return FREEZE;

        return ICY;
    }

    public static String getDegColor(String status) {
        if (status.equals(HOT)) return "-fx-fill:#FF0000";
        if (status.equals(HEAT)) return "-fx-fill:#FFF000";
        if (status.equals(NORMAL)) return "-fx-fill:#FFF";
        if (status.equals(COOL)) return "-fx-fill:#77E7EE";
        if (status.equals(COLD)) return "-fx-fill:#00AFFF";
        if (status.equals(FREEZE)) return "-fx-fill:#006FFF";

        return "-fx-fill:#000FFF";
    }

}
