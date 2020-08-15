import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.*;

import java.io.File;
import java.util.Scanner;

import static javafx.scene.paint.Color.*;

public class Map extends Pane {

    private int UNIT = 50;
    private int[][] map;
    private int size;
    private Position start;
    private int value;


    public Map(String ReadMap){
        try {
            File file = new File(ReadMap);
            Scanner scan = new Scanner(file);

            ImagePattern grass = new ImagePattern(new Image("/img/grass.png"));
            ImagePattern bush = new ImagePattern(new Image("/img/bush.png"));

            this.size = scan.nextInt();

            UNIT = (int)Math.min(500/size, 50);

            System.out.println("Map: " + file.getName().substring(0, file.getName().length()-4) + "\nMap size: "  + size);
            this.map = new int[size][size];
            
            for(int row = 0; row < size; row++){
                for(int column = 0; column < size; column++){
                    map[row][column] = scan.nextInt();
                    
                    if (map[row][column] == 2)
                        start = new Position(column, row);

                    Rectangle rectangle = new Rectangle(column*UNIT, row*UNIT, UNIT,UNIT);
                    if ( map[row][column] == 1)
                        rectangle.setFill(bush);
                    else if(map[row][column] == 3){
                        map[row][column] = 1;
                        rectangle.setFill(grass);    
                    }else
                        rectangle.setFill(grass);
                    
                    getChildren().add(rectangle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    //getters
    public int[][] getMap(){
        return map;
    }

    public int getUnit() {
        return UNIT;
    }

    public int getSize() {
        return size;
    }

    public int getValue(int a, int b) {
        return map[a][b];
    }

    public Position getStartPosition() {
        return start;
    }
}