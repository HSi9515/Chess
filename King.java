

public class King extends Piece{

	public King(){
		this(1);
	}
	
	public King(int player){
		super(player, "king");
	}
	
	public boolean isValidMove(Location from, Location to, Piece[][]b) {
		if (b[to.row][to.column].getPlayer() == b[from.row][from.column].getPlayer()) {
			System.out.println("    same player");
			return false;
		}
		
		else if (Math.abs(from.column - to.column) > 1 || Math.abs(from.row - to.row) > 1) {
			System.out.println("    out of range");
			return false;
		}
		
		for (int i = 0; i < b.length; i++){
			for (int j = 0; j < b[0].length; j++){
				if (b[i][j] instanceof King && b[i][j].getPlayer() != this.getPlayer() && Math.abs(to.row - i) <= 1 && Math.abs(to.column - j) <= 1){
					System.out.println("    check by King");
					return false;
				}
				else if (!(b[i][j] instanceof King) && b[i][j].getPlayer() != this.getPlayer() && b[i][j].isValidMove(new Location(i, j), to, b)) {
					System.out.println("    check by " + b[i][j] + " at "+ i  + ","+ j);
					return false;
				}
			}
		}
			
		return true;
	}
	
	public String toString(){
		return super.toString() + ": King";
	}
	
}
