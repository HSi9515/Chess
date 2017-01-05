

public class Pawn extends Piece{
	boolean firstTurn;

	public Pawn(){
		this(1);
	}
	
	public Pawn(int player){
		super(player, "pawn");
		firstTurn = true;

	}
	
	public void setFirstTurn(boolean b){
		 firstTurn = b;
	}
	
	public String toString(){
		return super.toString() + ": Pawn";
	}

	public boolean isValidMove(Location from, Location to, Piece[][]b){
		//System.out.println(Math.abs(from.row - to.row));	
		
		int verticalDistance = Math.abs(from.row - to.row);
		int horizantalDistance = Math.abs(from.column - to.column);
		Piece toPiece = b[to.getRow()][to.getColumn()];
		
		switch(super.getPlayer()){
		
			case 1:
			
				if(from.getRow()>to.getRow() && toPiece.getPlayer() != this.getPlayer()){
					if(verticalDistance == 1 && horizantalDistance == 0 && toPiece.getPlayer() == 3){
						return true;
					}
					else if(verticalDistance == 1 && horizantalDistance == 1 && toPiece.getPlayer() == 2){
						return true;
					}
					else if(verticalDistance == 2 && horizantalDistance == 0 && firstTurn && b[to.getRow()+1][to.getColumn()].getPlayer() == 3){
						return true;	
					}
					else
						return false;
				}
			break;
			
			case 2:
				
				if(from.getRow()<to.getRow() && toPiece.getPlayer() != this.getPlayer()){
					if(verticalDistance == 1 && horizantalDistance == 0 && toPiece.getPlayer() == 3){
						return true;
					}
					else if(verticalDistance == 1 && horizantalDistance == 1 && toPiece.getPlayer() == 1){
						return true;
					}
					else if(verticalDistance == 2  && horizantalDistance == 0 && firstTurn && b[to.getRow()-1][to.getColumn()].getPlayer() == 3){
						return true;
					}
					else
						return false;
				}
			break;	
			
			}
			return false;
	}
	
}
