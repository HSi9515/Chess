//12/21
//Hannah Si

public class Queen extends Piece{
	
	public Queen(){
		this(1);
	}
	
	public Queen(int player){
		super(player, "queen");
	}
	
	public boolean isValidMove(Location from, Location to, Piece[][]b){
		if (b[to.row][to.column].getPlayer() == b[from.row][from.column].getPlayer()) {
			System.out.println("    same player");
			return false;
		}
		
		else if (from.row == to.row){
			for (int i = Math.min(from.column, to.column) + 1; i < Math.max(from.column, to.column); i++){
				if (b[from.row][i].getPlayer() == 1 || b[from.row][i].getPlayer() == 2) {
					System.out.println("    same row");
					return false;
				}
			}
			return true;
		}
		
		else if (from.column == to.column){
			for (int i = Math.min(from.row, to.row) + 1; i < Math.max(from.row, to.row); i++){
				if (b[i][from.column].getPlayer() == 1 || b[i][from.column].getPlayer() == 2){
					System.out.println("    same column");
					return false;
				}
			}
			return true;
		}
		
		else if (Math.abs(from.row - to.row) == Math.abs(from.column - to.column)){
			int rowToCheck, columnToCheck;
			
			for (int i = 1; i < Math.abs(from.row-to.row); i++){
				rowToCheck = from.row + i*(int)Math.signum(to.row-from.row);
				columnToCheck = from.column + i*(int)Math.signum(to.column-from.column);
				
				if (b[rowToCheck][columnToCheck].getPlayer() == 1 || b[rowToCheck][columnToCheck].getPlayer() == 2) {
					System.out.println("   same diagonal");
					return false;
				}
			}
			return true;
		}
		
		return false;
	}
	
	public String toString(){
		return super.toString() + ": Queen";
	}
	
}
