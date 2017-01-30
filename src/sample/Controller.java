package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.PokerCalc.Card;
import sample.PokerCalc.GetPersentage;
import sample.PokerCalc.Hand;
import sample.PokerCalc.Range;

import java.io.IOException;
import java.util.ArrayList;


public class Controller {
    @FXML
    private Button hand1 = new Button();
    @FXML
    private Button closeButton = new Button();
    @FXML
    private Button hand2 = new Button();
    @FXML
    private TextField firstHand = new TextField();
    @FXML
    private TextField secondHand = new TextField();
    @FXML
    private TextField firstHandWins = new TextField();
    @FXML
    private TextField secondHandWins = new TextField();
    @FXML
    private TextField flop = new TextField();
    @FXML
    private TextField turn = new TextField();
    @FXML
    private TextField river = new TextField();
    @FXML
    private TextField trials = new TextField();

    public void pressButtonFlop(ActionEvent event) throws Exception {
        pressButtonFTR(this.flop);
    }

    public void pressButtonTurn(ActionEvent event) throws Exception {
        pressButtonFTR(this.turn);
    }

    public void pressButtonRiver(ActionEvent event) throws Exception {
        pressButtonFTR(this.river);
    }

    public void pressButtonFTR(TextField textField) {
        VBox vBox = new VBox();

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                int intCard = i * 13 + j;
                Card card = new Card(intCard);
                ToggleButton toggleButton = new ToggleButton(card.toString());
                toggleButton.setPrefSize(40, 40);

                toggleButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int i = 1;
                        if (textField.equals(flop)) {
                            i = 3;
                        }
                        setOrRemoveText(toggleButton, textField, i);
                    }
                });
                if (isCardSelected(toggleButton.getText(), textField)) {
                    toggleButton.setSelected(true);
                }
                if (isCardSelected(toggleButton.getText(), firstHand)) {
                    toggleButton.setDisable(true);
                } else if (isCardSelected(toggleButton.getText(), secondHand)) {
                    toggleButton.setDisable(true);
                }
                if (textField.equals(turn) || textField.equals(river)) {
                    if (isCardSelected(toggleButton.getText(), flop)) {
                        toggleButton.setDisable(true);
                    }
                }
                if (textField.equals(river)) {
                    if (isCardSelected(toggleButton.getText(), turn)) {
                        toggleButton.setDisable(true);
                    }
                }
                gridPane.add(toggleButton, i, j);
            }
        }
        Pane pane = new Pane();
        Button add = new Button("Add");
        add.setPrefSize(160, 80);
        add.setDefaultButton(true);
        add.setBackground(Background.EMPTY);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
            }
        });

        pane.getChildren().add(add);

        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(pane);

        Scene scene = new Scene(vBox, 160, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        if (textField.equals(this.flop)) {

        } else if (textField.equals(this.turn)) {

        } else if (textField.equals(this.river)) {

        }
    }

    public void pressButton1(ActionEvent event) throws Exception {
        pressButton(this.firstHand);
    }

    public void pressRangeButton1(ActionEvent event) throws Exception {
        pressButtonRange(true);
    }

    public void pressRangeButton2(ActionEvent event) throws Exception {
        pressButtonRange(false);
    }

    public void pressButton2(ActionEvent event) throws Exception {
        pressButton(this.secondHand);
    }

    public void pressButtonRange(boolean isFirstRange) throws Exception {
        VBox vBox = new VBox();

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        int k = 0;
        for (int i = 0; i < 13; i++) {
            Card cardi = new Card(i);
            for (int j = 0; j < 13; j++) {
                Card cardj = new Card(j);
                ToggleButton toggleButton;
                String s = "";
                if (j > i) {
                    s = "o";
                }
                if (i > j) {
                    s = "s";
                }
                if (cardi.getIndex() > cardj.getIndex()) {
                    toggleButton = new ToggleButton(cardi.toString().substring(0, 1) + cardj.toString().substring(0, 1) + s);
                } else {
                    toggleButton = new ToggleButton(cardj.toString().substring(0, 1) + cardi.toString().substring(0, 1) + s);
                }

                toggleButton.setPrefSize(45, 45);
                if (isRangeCardSelected(toggleButton.getText(), this.firstHand)) {
                    toggleButton.setSelected(true);
                }
                toggleButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!toggleButton.isSelected()) {
                            if (isFirstRange) {
                                firstHand.setText(firstHand.getText().replace(toggleButton.getText() + ",", ""));
                            } else {
                                secondHand.setText(secondHand.getText().replace(toggleButton.getText() + ",", ""));
                            }
                        } else {
                            if (isFirstRange) {
                                firstHand.setText(firstHand.getText() + toggleButton.getText() + ",");
                            } else {
                                secondHand.setText(secondHand.getText() + toggleButton.getText() + ",");

                            }
                        }
                    }
                });
                gridPane.add(toggleButton, 13 - i, 13 - j);
            }
        }
        Pane pane = new Pane();
        Button add = new Button("Add");
        add.setPrefSize(585, 115);
        add.setDefaultButton(true);
        add.setBackground(Background.EMPTY);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
            }
        });

        pane.getChildren().add(add);

        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(pane);

        Scene scene = new Scene(vBox, 585, 700);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void setOrRemoveText(ToggleButton toggleButton, TextField textField, int maxCards) {
        if (toggleButton.isSelected() == Boolean.TRUE) {
            if (textField.getText().isEmpty()) {
                textField.setText(toggleButton.getText());
            } else {
                if (textField.getText().split(" ").length > maxCards - 1) {
                    toggleButton.setSelected(Boolean.FALSE);
                } else {

                    textField.setText(textField.getText() + " " + toggleButton.getText());
                }
            }
        } else {
            textField.setText(textField.getText().replace(toggleButton.getText(), "").replace(" ", ""));
        }

    }

    public void evaluate() throws InterruptedException, IOException {
        GetPersentage getPersentage = new GetPersentage();
        int trials = 20000;
        if (!this.trials.getText().isEmpty()) {
            trials = Integer.parseInt(this.trials.getText());
        }
        if (!this.firstHand.getText().isEmpty() && !this.secondHand.getText().isEmpty()) {
            ArrayList<Double> persentage = getPersentage.getPersentage(this.firstHand.getText(), this.secondHand.getText(), trials, getPostFlopCards());
            publicResults(persentage, false);

        }

    }

    public ArrayList<Card> getPostFlopCards() {
        ArrayList<Card> cardArrayList = new ArrayList<>();
        if (!flop.getText().isEmpty()) {
            String[] flopstring = flop.getText().split(" ");
            for (int i = 0; i < flopstring.length; i++) {
                Card card = new Card(flopstring[i]);
                cardArrayList.add(card);
            }
        } else {
            return cardArrayList;
        }

        if (!turn.getText().isEmpty()) {
            cardArrayList.add(new Card(turn.getText()));
        } else {
            return cardArrayList;
        }
        if (!river.getText().isEmpty()) {
            cardArrayList.add(new Card(river.getText()));
        }
        return cardArrayList;
    }


    public void publicResults(ArrayList<Double> result, boolean reverse) {
        if (!reverse) {
            firstHandWins.setText(result.get(0).toString());
            secondHandWins.setText(result.get(1).toString());
        } else {
            firstHandWins.setText(result.get(1).toString());
            secondHandWins.setText(result.get(0).toString());
        }
    }

    public void pressButton(TextField textField) throws Exception {
        VBox vBox = new VBox();

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                int intCard = i * 13 + j;
                Card card = new Card(intCard);
                ToggleButton toggleButton = new ToggleButton(card.toString());
                toggleButton.setPrefSize(40, 40);

                toggleButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setOrRemoveText(toggleButton, textField, 2);
                    }
                });
                if (isCardSelected(toggleButton.getText(), textField)) {
                    toggleButton.setSelected(true);
                }
                gridPane.add(toggleButton, i, j);
            }
        }
        Pane pane = new Pane();
        Button add = new Button("Add");
        add.setPrefSize(160, 80);
        add.setDefaultButton(true);
        add.setBackground(Background.EMPTY);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) add.getScene().getWindow();
                stage.close();
            }
        });

        pane.getChildren().add(add);

        vBox.getChildren().add(gridPane);
        vBox.getChildren().add(pane);

        Scene scene = new Scene(vBox, 160, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public boolean isCardSelected(String card, TextField textField) {
        boolean isCardSelected = Boolean.FALSE;
        String[] selectedCards;

        selectedCards = textField.getText().split(" ");

        for (int i = 0; i < selectedCards.length; i++) {
            if (selectedCards[i].equals(card)) {
                isCardSelected = Boolean.TRUE;
                break;
            }
        }
        return isCardSelected;
    }

    public boolean isRangeCardSelected(String card, TextField textField) {

        boolean isSelected = false;
        if (textField.equals(this.firstHand)) {
            String[] strings = firstHand.getText().split(",");
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].equals(card)) {
                    isSelected = true;
                }
            }
        } else {
            if (textField.equals(this.secondHand)) {
                String[] strings = secondHand.getText().split(",");
                for (int i = 0; i < strings.length; i++) {
                    if (strings[i].equals(card)) {
                        isSelected = true;
                    }
                }
            }
        }
        return isSelected;
    }


}
