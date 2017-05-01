public class Testing {
    public static void main(String[] args) {
        int day = 1;
        int hour = 7;
        double outsideTemperature;
        double roomTemperature;
        double inhabitedTemperature;

        double globalTemperature = 10;
        double changeSpeed = .7;

        for (int i = 0; i < 24 * 20; i++) {
            hour++;

            if (hour == 24) {
                hour = 0;
                changeSpeed = (Math.random() + .5);
            }
            if (hour < 16 && hour > 3) globalTemperature += changeSpeed;
            if (hour > 15 || hour < 4) globalTemperature -= changeSpeed;
            if (hour == 0) day++;

            outsideTemperature = globalTemperature - 4 + day/10;
            inhabitedTemperature = 20 + (outsideTemperature - 10) / 2;
            roomTemperature = 15 + (outsideTemperature - 10) / 4;

            if (hour == 3 || hour == 15)
            System.out.println("День: " + day +
                    " || Время: " + hour +
                    " || Температура на улице: " + outsideTemperature +
                    " || Температура в нежилом помещении: " + roomTemperature +
                    " || Температура в жилом помещении: " + inhabitedTemperature +
                    " || Мод: " + changeSpeed);
        }
    }
}
