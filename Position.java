public class Position{
	private int x;
	private int y;

	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}

	public void setX(int newX){
		this.x = newX;
	}
	public void setY(int newY){
		this.y = newY;
	}

	public boolean equals(Position p){
		if(this.x == p.getX() && this.y == p.getY())
			return true;
		else 	
			return false;
	}
	public String toString(){
		return x + " " + y;
	}
}