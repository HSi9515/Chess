// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 12/2/15
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  
////



import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements MouseListener{ //THINGS TO ADD: glow around clicked player	
	
	private final int SQUARE_WIDTH = 90;    // The width of one space on the board.  Constant used for drawing board.
	private final int OFFSET = 0;
	private Location from;   			    // holds the coordinates of the square that the user would like to move from.
	private Location to; 
	private Location highlight;// holds the coordinates of the square that the user would like to move to.
	private int click;   				// false until the game has started by somebody clicking on the board.  should also be set to false
	                         				// after an attempted move.
	private Piece[][] board; 				// an 8x8 board of 'Pieces'.  Each spot should be filled by one of the chess pieces or a 'space'. 
	
	public GraphicsPanel()
	{
		setPreferredSize(new Dimension(SQUARE_WIDTH*8+OFFSET+2,SQUARE_WIDTH*8+OFFSET+2));   // Set these dimensions to the width 
        											 // of your background picture.   
        this.setFocusable(true);					 // for keylistener
		this.addMouseListener(this);
		
		board = new Piece[8][8];
		
		highlight = new Location(-99,0);
		
		for(int i = 0; i<8; i++){
			for(int j = 0; j<board[i].length; j++){
				board[i][j] = new Filler();
			}
		}
		
		for(int i = 0; i<8; i++){
			board[0][i] = new Pawn(2);  //this is where you can instantiate your pieces to test them
			board[7][i] = new Pawn(1);   //just delete new Pawn and replace it with whichever pieces your making
		}
		
		board[1][1] = new Queen();
		board[1][3] = new Bishop();
				
		click = 1;
	}
	
	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//   			first rendered.  It can also be called by this.repaint()
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		// Draw the board
		g2.setColor(Color.gray);
		g2.drawLine(SQUARE_WIDTH*8+OFFSET, OFFSET, SQUARE_WIDTH*8+OFFSET, SQUARE_WIDTH*8+OFFSET);
		g2.drawLine(OFFSET, SQUARE_WIDTH*8+OFFSET, SQUARE_WIDTH*8+OFFSET, SQUARE_WIDTH*8+OFFSET);
		g2.drawLine(OFFSET, OFFSET, SQUARE_WIDTH*8+OFFSET, 0+OFFSET);
		g2.drawLine(OFFSET, OFFSET, OFFSET, SQUARE_WIDTH*8+OFFSET);
		
		for(int i = 0; i <8; i+=2)
			for (int j = 0; j<8; j+=2)
			{
				
				if(highlight.getRow() != -99){
					g2.setColor(Color.YELLOW);
					g2.fillRect(highlight.getColumn()*SQUARE_WIDTH+OFFSET,highlight.getRow()*SQUARE_WIDTH+OFFSET,SQUARE_WIDTH,SQUARE_WIDTH);
					
				}
				
				g2.setColor(Color.gray);
				g2.fillRect(i*SQUARE_WIDTH+OFFSET,j*SQUARE_WIDTH+OFFSET,SQUARE_WIDTH,SQUARE_WIDTH);
			}
		
		for(int i = 1; i <8; i+=2)
			for (int j = 1; j<8; j+=2)
			{
				if(highlight.getRow() != -99){
					g2.setColor(Color.YELLOW);
					g2.fillRect(highlight.getColumn()*SQUARE_WIDTH+OFFSET,highlight.getRow()*SQUARE_WIDTH+OFFSET,SQUARE_WIDTH,SQUARE_WIDTH);
					
				}
				g2.setColor(Color.gray);
				g2.fillRect(i*SQUARE_WIDTH+OFFSET,j*SQUARE_WIDTH+OFFSET,SQUARE_WIDTH,SQUARE_WIDTH);
			}
		
		//Drawing the pieces and spaces
		for(int i = 0; i<board.length;  i++){
			for(int j = 0; j<board[i].length; j++){
				//if(board[i][j].getPlayer() != 3)
					board[i][j].draw(g2, this, new Location(i,j));
			}
		}
		
	}

	
	public void mouseClicked(MouseEvent e) {
	
		System.out.println("Click "+ click);
		System.out.println("    x = " + (int)e.getX()/90);
		System.out.println("    y = " + (int)e.getY()/90);
		
		
		if(click == 1){
			
			if(board[(int)e.getY()/90][ (int)e.getX()/90].getPlayer() != 3){
			
				from = new Location((int)e.getY()/90, (int)e.getX()/90);
				highlight = from;
				System.out.println("    Player " + board[from.getRow()][from.getColumn()].getPlayer());
				
				//NEW PARTS  1/4/2017*************
				if(!board[from.getRow()][from.getColumn()].stuck(board, from))
					click = 2;
				else
					click = 1;
				//**********************************
			
			}
			
		}
		
		else if(click == 2){
						
			to = new Location((int)e.getY()/90, (int)e.getX()/90);
			
			if (board[from.getRow()][from.getColumn()].isValidMove(from, to, board)){
					
				System.out.println("    Valid move");
			
				this.move(from, to);
				
				//NEW PARTS 1/4/2017**************
				if(board[to.getRow()][to.getColumn()] instanceof Pawn){
					Pawn p = (Pawn) board[to.getRow()][to.getColumn()];
					p.setFirstTurn(false);
				}
				//***************
				
				
				highlight.setRow(-99);
				
				for(int i = 0; i<board.length; i++){
					if(board[0][i] instanceof Pawn && board[0][i].getPlayer() == 1)
						board[0][i] = new Queen(1);
					if(board[7][i] instanceof Pawn && board[7][i].getPlayer() == 2)
						board[7][i] = new Queen(2);	
				}
				
				
				
				click = 1;
			}
			
		
		}
		
		this.repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//New move method
	public void move(Location f, Location t){
		Piece p = board[f.getRow()][f.getColumn()];
		board[f.getRow()][f.getColumn()] = new Filler();
		board[t.getRow()][t.getColumn()] = p;
		p.setFirstTurn(false);
		
		
		
		//Castling Code
		if(p instanceof King){
			King p2 = (King) p;
			if(p.getPlayer() == 1){
			
				
				if(p2.castleStatus().equals("left")){
					move(new Location(7,0),new Location(7,3));
					board[7][7].setFirstTurn(false);
					
				}
				else if(p2.castleStatus().equals("right"))
					move(new Location(7,7), new Location(7,5));
					board[7][0].setFirstTurn(false);
			}
			else if(p.getPlayer() == 2){
				if(p2.castleStatus().equals("left")){
					move(new Location(0,0),new Location(0,3));
					board[0][7].setFirstTurn(false);
				}
				else if(p2.castleStatus().equals("right"))
					move(new Location(0,7), new Location(0,5));
					board[0][0].setFirstTurn(false);
			}
		}
		
		
	}
	
	

}

