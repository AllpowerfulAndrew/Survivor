package survivor.controller;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameBasics.Player;
import survivor.model.processing.Command;

import static survivor.model.gameBasics.Temperature.getAccurateTemperature;
import static survivor.model.gameBasics.Temperature.getPerceivedTemperature;
import static survivor.model.gameBasics.Time.*;

public class Controller {
    private static final Logger LOG = Logger.getLogger(Controller.class);

    public TextArea lastInput;
    public TextField inputField;
    public TextArea textField;
    public Text time;
    public Text temperature;
    public Text health;
    public Text day;

    public void checkInput(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            String text = inputField.getText();
            text = text.toLowerCase();
            LOG.info("Введено: " + text);
            setLastInput(text);
            showText(Command.getResponse(text));
            Command.penultimateInput = text;
            inputField.setText("");
        }

        if (key.getCode() == KeyCode.ESCAPE) {
            Game.lastMessage = Game.actualSectionDescription;
            showText(Game.actualSectionDescription);
        }
    }

    private void showText(String text) {
        if (text.equals(Game.incorrect)) lastInput.setStyle("-fx-border-color: brown; -fx-border-width: 3px");
        else {
            lastInput.setStyle("-fx-border-width: 0px");
            Game.lastMessage = text;
            setTimeAndDay();
            setTemperature();
            textField.setText(text);
        }
    }

    private void setLastInput(String input) {
        if (Command.penultimateInput.equals("")) lastInput.setText(input);
        else lastInput.setText(input + "\n" + Command.penultimateInput);
    }

    private void setTimeAndDay() {
        if (Player.hasClock) time.setText(getTime());
        else if (Game.isTimingOn) time.setText(getDayTime());

        day.setText(getDay());
    }

    private void setTemperature() {
        if (Player.hasThermometer) {
//            temperature.setStyle(getDegColor(getPerceivedTemperature()));
            temperature.setText(getAccurateTemperature() + "°");
        } else {
//            temperature.setStyle(getDegColor(getPerceivedTemperature()));
            temperature.setText(getPerceivedTemperature());
        }
    }
}