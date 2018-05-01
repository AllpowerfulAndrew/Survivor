package game.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import game.model.basics.Game;
import game.model.constants.GameStatus;
import game.model.constants.StoryStatus;
import game.model.processing.Command;

import static game.model.constants.Messages.INCORRECT;

public class Controller {

    @FXML
    public TextArea lastInput;

    @FXML
    public TextField inputField;

    @FXML
    public TextArea textField;

    public void checkInput(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER) {
            String text = inputField.getText();
            text = text.toLowerCase();
            setLastInput(text);
            showText(Command.getResponse(text));
            Command.penultimateInput = text;
            inputField.setText("");
        }

        if (key.getCode() == KeyCode.ESCAPE) {
            if (Game.status.equals(GameStatus.MENU) || Game.status.equals(StoryStatus.INTRO)) return;
            Game.lastMessage = Game.actualSectionDescription;
            showText(Game.actualSectionDescription);
        }
    }

    private void showText(String text) {
        if (text.equals(INCORRECT)) lastInput.setStyle("-fx-border-color: brown; -fx-border-width: 3px");
        else {
            lastInput.setStyle("-fx-border-width: 0px");
            Game.lastMessage = text;
            textField.setText(text);
        }
    }

    private void setLastInput(String input) {
        if (Command.penultimateInput.equals("")) lastInput.setText(input);
        else lastInput.setText(input + "\n" + Command.penultimateInput);
    }
}