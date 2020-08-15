import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

public class MyPlayer implements Player {

    private Map map;
    private Food food;
    private Circle ball;
    private Position position;
    private ImagePattern right, left, up, down;


    public MyPlayer(Map map) {
        this.map = map;

        right = new ImagePattern(new Image("/img/pacmanRight.png"));
        left = new ImagePattern(new Image("/img/pacmanLeft.png"));
        up = new ImagePattern(new Image("/img/pacmanUp.png"));
        down = new ImagePattern(new Image("/img/pacmanDown.png"));

        position = map.getStartPosition();
        
        ball = new Circle(position.getX() * map.getUnit() + map.getUnit() / 2, position.getY() * map.getUnit() + map.getUnit() / 2, map.getUnit() / 2 - 5, Color.RED);
        ball.setFill(right);
        map.getChildren().add(ball);
    }




    public void moveRight() {
        if ((position.getX() < map.getSize() - 1) && (map.getMap()[position.getY()][position.getX() + 1] != 1)) {
            position.setX(position.getX() + 1);
            ball.setCenterX(position.getX() * map.getUnit() + map.getUnit() / 2);
            ball.setFill(right);
        } else {
            System.out.println("Invalid Position!");
        }
    }

    public void moveLeft() {
        if ((position.getX() > 0) && (map.getMap()[position.getY()][position.getX() - 1] != 1)) {
            position.setX(position.getX() - 1);
            ball.setCenterX(position.getX() * map.getUnit() + map.getUnit() / 2);
            ball.setFill(left);
        } else {
            System.out.println("Invalid Position!");
        }
    }


    public void moveUp() {
        if ((position.getY() > 0) && (map.getMap()[position.getY() - 1][position.getX()] != 1)) {
            position.setY(position.getY() - 1);
            ball.setCenterY(position.getY() * map.getUnit() + map.getUnit() / 2);
            ball.setFill(up);
        } else {
            System.out.println("Invalid Position!");
        }
    }


    public void moveDown() {
        if ((position.getY() < map.getSize() - 1) && (map.getMap()[position.getY() + 1][position.getX()] != 1)) {
            position.setY(position.getY() + 1);
            ball.setCenterY(position.getY() * map.getUnit() + map.getUnit() / 2);
            ball.setFill(down);
        } else {
            System.out.println("Invalid Position!");
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }



}



