import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;

public class MyBotPlayer implements BotPlayer{
	private Circle ball;
	private Map map;
	private Position position;
	private Food food;
	private int size;
	private int unit;
	private long speed = 150_000_000;
	private ImagePattern right, left, up, down;

	public  MyBotPlayer(Map map){
		this.map = map;
		size = map.getSize();
		unit = map.getUnit();
		
		//set ball on random position
		int x=0, y=0;
		do{
			x = (int)(Math.random()*size);
			y = (int)(Math.random()*size);
		}while (map.getValue(y, x) != 0);

		right = new ImagePattern(new Image("/img/pacmanRight.png"));
        left = new ImagePattern(new Image("/img/pacmanLeft.png"));
        up = new ImagePattern(new Image("/img/pacmanUp.png"));
        down = new ImagePattern(new Image("/img/pacmanDown.png"));

		position = new Position(x, y);
		ball = new Circle(position.getX()*unit + unit/2, position.getY()*unit + unit/2, unit/2, Color.RED);
		ball.setFill(right);
		map.getChildren().add(ball);
	}

	public void moveRight() {
        position.setX(position.getX() + 1);
        ball.setCenterX(position.getX() * map.getUnit() + map.getUnit() / 2);
        ball.setFill(right);
    }

    public void moveLeft() {
        position.setX(position.getX() - 1);
        ball.setCenterX(position.getX() * map.getUnit() + map.getUnit() / 2);
        ball.setFill(left);
    }

    public void moveUp() {
        position.setY(position.getY() - 1);
        ball.setCenterY(position.getY() * map.getUnit() + map.getUnit() / 2);
        ball.setFill(up);
    }

    public void moveDown() {
        position.setY(position.getY() + 1);
        ball.setCenterY(position.getY() * map.getUnit() + map.getUnit() / 2);
        ball.setFill(down);
    }


	public Position getPosition(){
		return position;
	}
	public void feed(Food food){
		this.food = food;
	}
	public void eat(){
		AnimationTimer animation = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
            	if(now - lastUpdate >= speed){
	                if(food.getPosition().getX() > position.getX())
	            		moveRight();
					else if(food.getPosition().getX() < position.getX())
						moveLeft();
					else if(food.getPosition().getY() > position.getY())
						moveDown();
					else if(food.getPosition().getY() < position.getY())
						moveUp();
	                lastUpdate = now;
            	}
            }
        };
        animation.start();
	}
	public void find(){
		AnimationTimer animation = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
            	if(now - lastUpdate >= speed){
            		int[][] path = new int[size+2][size+2];
            		
            		int sx=0, sy=0;
					while (true) {
						try{
							sy = food.getPosition().getY();
							sx = food.getPosition().getX();
							break;
						}catch (Exception e) {}
					}
					for (int[] row : path) 
            			Arrays.fill(row, 100);
            		path[sy+1][sx+1]=1;

					boolean found = false;
					int st = -1;
					sy = position.getY()+1;
					sx = position.getX()+1;
					//find path
					while(!found)
					for (int y=1; y<size+1 && !found; y++) 
					for (int x=1; x<size+1 && !found; x++) {
						if(x == sx && y == sy && path[y][x] == 1){
							found =true;
							st = path[y][x];
							break;
						}
						if(map.getValue(y-1, x-1) == 1 || path[y][x] < 100)	continue;
						path[y][x] = Math.min(Math.min(path[y-1][x], path[y+1][x]), Math.min(path[y][x-1], path[y][x+1])) + 1;
						if(x == sx && y == sy && path[y][x] < 100){
							found =true;
							st = path[y][x];
							break;
						}
					}
					//make a step
					if(sx>1 && path[sy][sx-1] == st-1)	
						moveLeft();
					else if(sx<size && path[sy][sx+1] == st-1) 
						moveRight();
					else if(sy>1 && path[sy-1][sx] == st-1) 
						moveUp();
					else if(sy<size && path[sy+1][sx] == st-1) 
						moveDown();
            		lastUpdate = now;
            	}
            }
        };
        animation.start();
	}
}