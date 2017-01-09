public class King extends Piece{

	
	private String castled;
	
	public King(){
		this(1);
	}
	
	public King(int player){
		super(player, "king");
		super.setFirstTurn(true);
		castled = "hasn't";
	}
	
	
	
	
	public boolean isValidMove(Location from, Location to, Piece[][]b) {
		
		if(isFirstTurn() && to.getRow() == from.getRow() && to.getColumn() == 6 || to.getColumn() == 2){
		
			System.out.println("are here");
			boolean leftOfKingEmpty = false;
			boolean rightOfKingEmpty = false;
		
			int leftCounter = 0;
			int rightCounter = 0;
			
			for(int i = from.getColumn()+1; i<7; i++)
				if(b[from.getRow()][i].getPlayer() == 3)
					rightCounter++;
		
			for(int i = from.getColumn()-1; i>0; i--)
				if(b[from.getRow()][i].getPlayer() == 3)
					leftCounter++;
			
			
				if(b[from.getRow()][7].isFirstTurn() && rightCounter == 2){
					System.out.println("Success, to the right");
					castled = "right";
					return true;
				}
				if(b[from.getRow()][0].isFirstTurn() && leftCounter == 3){
					System.out.println("success, to the left");
					castled = "left";
					return true;
				}
			
			
		
		}
		

		
		
		
		
		
		
		
		
		
		
		//******************************************************************************
		if (b[to.row][to.column].getPlayer() == b[from.row][from.column].getPlayer()) {
			System.out.println("    same player");
			castled = "has";
			return false;
		}
		
		else if (Math.abs(from.column - to.column) > 1 || Math.abs(from.row - to.row) > 1) {
			System.out.println("    out of range");
			castled = "has";
			return false;
		}
		
		for (int i = 0; i < b.length; i++){
			for (int j = 0; j < b[0].length; j++){
				if (b[i][j].getPlayer() != this.getPlayer() && b[i][j].isValidMove(new Location(i, j), to, b)) {
					System.out.println("    check by " + b[i][j] + " at "+ i  + ","+ j);
					castled = "has";
					return false;
				}
			}
		}
			
		return true;
	}
	
	public String toString(){
		return super.toString() + ": King";
	}
	public String castleStatus(){
		return castled;
	}
	
}
