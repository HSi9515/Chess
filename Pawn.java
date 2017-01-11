import java.awt.Component;
import java.awt.Graphics;
import java.lang.*;
public class Pawn extends Piece {

	
	
	public Pawn(){
		super(1, "images2/pawn1.png");
	
	}
	
	public Pawn(int player){
		super(player);
		super.setFirstTurn(true);
		
	
	}
	
	
	
	
	public boolean isValidMove(Location from, Location to, Piece[][]b){
		
	
	
		
		
	
	int distanceJumped = (int) Math.abs((double) from.row - to.row);
	int distanceJumpedHorizontally = (int) Math.abs((double) from.column - to.column);
	Piece toPiece = b[to.getRow()][to.getColumn()];
	
	switch(super.getPlayer()){
	
		case 1:
		
			if(from.getRow()>to.getRow() && toPiece.getPlayer() != this.getPlayer()){
				if(distanceJumped == 1 && to.getColumn() == from.getColumn() && toPiece.getPlayer() == 3){
				
					return true;
				}
				else if(distanceJumped == 1 && distanceJumpedHorizontally == 1 && toPiece.getPlayer() == 2){
					
					return true;
				}
				else if(distanceJumped == 2 && to.getColumn() == from.getColumn() && super.isFirstTurn() && b[to.getRow()+1][to.getColumn()].getPlayer() == 3){
				
					return true;	
				}
				else
					return false;
			}
		break;
		
		case 2:
			
			if(from.getRow()<to.getRow() && toPiece.getPlayer() != this.getPlayer()){
				if(distanceJumped == 1 && to.getColumn() == from.getColumn() && toPiece.getPlayer() == 3){
				
					return true;
				}
				else if(distanceJumped == 1 && distanceJumpedHorizontally == 1 && toPiece.getPlayer() == 1){
				
					return true;
				}
				else if(distanceJumped == 2  && to.getColumn() == from.getColumn() && super.isFirstTurn() && b[to.getRow()-1][to.getColumn()].getPlayer() == 3){
			
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
	

