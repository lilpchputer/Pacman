import java.util.*;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

//import java.awt.*;
import java.io.File;

import static java.awt.Color.WHITE;

public class Game extends Application {
    private static Map map;
    private Food food;
    private Player player;
    private BotPlayer bot;
    private ArrayList<File> maps = new ArrayList<>();

    @Override
    public void start(Stage primaryStage){
        File folder = new File("maps/");
        mapsInFolder(folder);
        
        primaryStage.setTitle("Eater");

        menu(primaryStage);

        primaryStage.show();
    }

    public void menu(Stage primaryStage){
        BorderPane menuPane = new BorderPane();
        Scene scene = new Scene(menuPane, 500, 400);
        
        Text eater = new Text("Eater");
        eater.setFont(Font.font("Roboto", FontWeight.BLACK, 36));
        eater.setFill(Color.web("1520A6"));
        VBox header = new VBox(eater);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30, 30, 30, 30));
        menuPane.setTop(header);
        
        VBox vBox = new VBox(10);
        Text choose = new Text("Choose map");
        choose.setFont(Font.font("Roboto", FontWeight.NORMAL, 24));
        choose.setFill(Color.web("63C5DA"));
        vBox.getChildren().add(choose);
        //buttons - maps
        for (File map : maps) {
            Button mapButton = new Button(map.getName().substring(0,map.getName().length()-4));
            vBox.getChildren().add(mapButton);
            mapButton.setOnAction(e ->  gameType(new Map( map.getPath()), primaryStage ) );

            mapButton.setStyle("-fx-font: 16px Roboto; -fx-background-color: #FFF; -fx-text-fill: #63C5DA; -fx-border-color: #63C5DA; -fx-border-radius: 5;");
        }
        vBox.setAlignment(Pos.BASELINE_CENTER);

        menuPane.setCenter(vBox);
        menuPane.setStyle("-fx-background-color: #FFF;");
        primaryStage.setScene(scene);
    }

    public void gameType(Map map, Stage primaryStage){
        BorderPane choosePane = new BorderPane();
        Scene scene = new Scene(choosePane, 500, 400);
        
        Text eater = new Text("Eater");
        eater.setFont(Font.font("Roboto", FontWeight.BLACK, 36));
        eater.setFill(Color.web("1520A6"));
        VBox header = new VBox(eater);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30, 30, 30, 30));
        choosePane.setTop(header);
        
        VBox vBox = new VBox(10);
        Text choose = new Text("Choose Game Type");
        choose.setFont(Font.font("Roboto", FontWeight.NORMAL, 24));
        choose.setFill(Color.web("63C5DA"));
        vBox.getChildren().add(choose);
        //buttons - maps
        Button playerButton = new Button("Player type");
        Button botButton = new Button("Bot type");
        vBox.getChildren().addAll(playerButton, botButton);
        
        playerButton.setOnAction(e ->  PlayerGame(map, primaryStage ) );
        playerButton.setStyle("-fx-font: 16px Roboto; -fx-background-color: #FFF; -fx-text-fill: #63C5DA; -fx-border-color: #63C5DA; -fx-border-radius: 5;");

        botButton.setOnAction(e ->  BotGame(map, primaryStage ) );
        botButton.setStyle("-fx-font: 16px Roboto; -fx-background-color: #FFF; -fx-text-fill: #63C5DA; -fx-border-color: #63C5DA; -fx-border-radius: 5;");

        vBox.setAlignment(Pos.BASELINE_CENTER);

        choosePane.setCenter(vBox);
        choosePane.setStyle("-fx-background-color: #FFF;");
        primaryStage.setScene(scene);
    }

    public void PlayerGame(Map map, Stage primaryStage){
        player = new MyPlayer(map);
        food = new Food(map, player);
        Scene game = new Scene(map);

        game.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case W:
                case UP: player.moveUp(); break;
                case S:
                case DOWN: player.moveDown(); break;
                case A:
                case LEFT: player.moveLeft(); break;
                case D:
                case RIGHT: player.moveRight(); break;
                case ESCAPE:
                    menu(primaryStage); 
                    break;
            }
        });
        
        primaryStage.setScene(game);
    }


    public void BotGame(Map map, Stage primaryStage){
        bot = new MyBotPlayer(map);
        Scene game = new Scene(map);

        food = new Food(map, bot);
        bot.feed(food);
        game.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case F:
                    System.out.println("F (find) key pressed");
                    bot.find(); 
                    break;
                case E:
                    System.out.println("E (eat) key pressed");
                    bot.eat(); 
                    break;
                case ESCAPE:
                    System.out.println("Points: " + food.getPoints());
                    food = null;
                    menu(primaryStage); 
                    break;
            }
        });
        
        primaryStage.setScene(game);
    }


    private void mapsInFolder(File folder){
        try{
            for(File entry : folder.listFiles()){
                if(entry.getName().endsWith(".txt"))
                    maps.add(entry);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        String musicFile = "pacman.mp3";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        launch(args);
    }
}
