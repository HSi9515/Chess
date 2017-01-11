public class Rook extends Piece{
	
	private boolean firstTurn;
	
	public Rook(){
		this(1);
	}
	
	public Rook(int player){
		super(player, "rook");
		super.setFirstTurn(true);
	}
	
	
	public void setFirstTurn(boolean b){
		 firstTurn = b;
	}
	
	public boolean isValidMove(Location from, Location to, Piece[][] b){
		if (b[to.row][to.column].getPlayer() == b[from.row][from.column].getPlayer()) {
			
			return false;
		}
			
		
		else if (from.row == to.row){
			for (int i = Math.min(from.column, to.column) + 1; i < Math.max(from.column, to.column); i++){
				if (b[from.row][i].getPlayer() == 1 || b[from.row][i].getPlayer() == 2) {
					
					return false;
				}
			}
			return true;
		}
		
		else if (from.column == to.column){
			for (int i = Math.min(from.row, to.row) + 1; i < Math.max(from.row, to.row); i++){
				if (b[i][from.column].getPlayer() == 1 || b[i][from.column].getPlayer() == 2){
					
					return false;
				}
			}
			return true;
		}
		
		else
			return false;
	}
	
	

}
