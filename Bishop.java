

public class Bishop extends Piece{
	
	public Bishop(){
		this(1);
	}
	
	public Bishop(int player){
		super(player, "bishop");
	}
	
	public boolean isValidMove(Location from, Location to, Piece[][]b){
		if (b[to.row][to.column].getPlayer() == b[from.row][from.column].getPlayer()) {
			//System.out.println("same player");
			return false;
		}
				
		else if (Math.abs(from.row - to.row) == Math.abs(from.column - to.column)){
			int rowToCheck, columnToCheck;
			
			for (int i = 1; i < Math.abs(from.row-to.row); i++){
				rowToCheck = from.row + i*(int)Math.signum(to.row-from.row);
				columnToCheck = from.column + i*(int)Math.signum(to.column-from.column);
				
				if (b[rowToCheck][columnToCheck].getPlayer() == 1 || b[rowToCheck][columnToCheck].getPlayer() == 2) {
					//System.out.println("same diagonal");
					return false;
				}
			}
			
			if (isTurn() && kingWillBeChecked(from, to, b))
				return false;
			
			return true;
		}
		
		return false;
	}
	
	public String toString(){
		return super.toString() + ": Bishop";
	}
	
}
