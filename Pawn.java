import java.awt.Component;
import java.awt.Graphics;
import java.lang.*;
public class Pawn extends Piece {

	boolean firstTurn;
	
	public Pawn(){
		super(1, "images2/pawn1.png");
		firstTurn = true;
	}
	
	public Pawn(int player){
		super(player);
		firstTurn = true;
		
	
	}
	
	public void setFirstTurn(boolean b){
		 firstTurn = b;
	}
	
	
	public boolean isValidMove(Location from, Location to, Piece[][]b){
		
	
	
		
		
	
	int distanceJumped = (int) Math.abs((double) from.row - to.row);
	int distanceJumpedHorizontally = (int) Math.abs((double) from.column - to.column);
	Piece toPiece = b[to.getRow()][to.getColumn()];
	
	switch(super.getPlayer()){
	
		case 1:
		
			if(from.getRow()>to.getRow() && toPiece.getPlayer() != this.getPlayer()){
				if(distanceJumped == 1 && to.getColumn() == from.getColumn() && toPiece.getPlayer() == 3){
					System.out.println("first condition had the error");
					return true;
				}
				else if(distanceJumped == 1 && distanceJumpedHorizontally == 1 && toPiece.getPlayer() == 2){
					System.out.println("second condition had the error");
					return true;
				}
				else if(distanceJumped == 2 && to.getColumn() == from.getColumn() && firstTurn && b[to.getRow()+1][to.getColumn()].getPlayer() == 3){
					System.out.println("third condition had the error");
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
				else if(distanceJumped == 1 && to.getColumn() != from.getColumn() && toPiece.getPlayer() == 1){
				
					return true;
				}
				else if(distanceJumped == 2  && to.getColumn() == from.getColumn() && firstTurn && b[to.getRow()-1][to.getColumn()].getPlayer() == 3){
			
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
	



