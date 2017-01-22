
//12/22/16
//Joshua Ren

public class Knight extends Piece{
	public Knight(){
		this(1);
	}
	
	public Knight(int player){
		super(player, "knight");
	}
	
	public boolean isValidMove(Location from, Location to, Piece[][]b){
		if (b[to.row][to.column].getPlayer() == b[from.row][from.column].getPlayer()) {
			//System.out.println("same player");
			return false;
		}
		else if(isTurn() && kingWillBeChecked(from, to, b))
			return false;
		
		if(Math.abs(to.row-from.row)==2 && Math.abs(to.column-from.column)==1){
			return true;
		}
		else if(Math.abs(to.row-from.row)==1 && Math.abs(to.column-from.column)==2){
			return true;
		}
		
		return false;
	}
	
}
