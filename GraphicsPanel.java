// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 12/2/15
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements MouseListener{
	
	private final int SQUARE_WIDTH = 90;    // The width of one space on the board.  Constant used for drawing board.
	
	private Location from;   			    // holds the coordinates of the square that the user would like to move from.
	private Location to;   				    // holds the coordinates of the square that the user would like to move to.
	private Location highlight;				// holds the coordinates of the square that the user would like to move to.
	
	private int click;   						
	private int state;						//0 = playing, 1 = selecting, 2 = game over
	private int player;						//1 = white, 2 = black

	private Piece[][] board; 				// an 8x8 board of 'Pieces'.  Each spot should be filled by one of the chess pieces or a 'space'. 

	private PieceIcon[] promotionSelections;
	
	private Font font;
	
	private Location risingPawn;
	
	public GraphicsPanel()
	{
		setPreferredSize(new Dimension(SQUARE_WIDTH*8+2,SQUARE_WIDTH*8+34));      
        this.setFocusable(true);					 // for keylistener
		this.addMouseListener(this);
		
		//FILLING THE BOARD
		board = new Piece[8][8];
		
		for(int i = 0; i<8; i++){
			for(int j = 0; j<board[i].length; j++){
				board[i][j] = new Filler();
			}
		}
		
		for(int i = 0; i<8; i++){
			board[6][i] = new Pawn(1); 
			board[1][i] = new Pawn(2); 
		}
		
		board[7][3] = new Queen();
		board[7][2] = new Bishop();
		board[7][5] = new Bishop();
		board[7][0] = new Rook();
		board[7][7] = new Rook();
		board[7][4] = new King();
		board[7][1] = new Knight();
		board[7][6] = new Knight();

		board[0][3] = new Queen(2);
		board[0][2] = new Bishop(2);
		board[0][5] = new Bishop(2);
		board[0][0] = new Rook(2);
		board[0][7] = new Rook(2);
		board[0][4] = new King(2);
		board[0][1] = new Knight(2);
		board[0][6] = new Knight(2);
		
		
		highlight = new Location(-99,0);
				
		click = 1;
		state = 0;
		player = 1;
		
		font = new Font("Optima", Font.PLAIN, 30);
		
		promotionSelections = new PieceIcon[4];
		promotionSelections[0] = new PieceIcon("queen", new Point(185, 300));
		promotionSelections[1] = new PieceIcon("bishop", new Point(275, 300));
		promotionSelections[2] = new PieceIcon("rook", new Point(365, 300));
		promotionSelections[3] = new PieceIcon("knight", new Point(455, 300));

	}
	
	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//   			first rendered.  It can also be called by this.repaint()
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		drawBoard(this, g2);
		
		drawHighlight(g2);
		
		drawPieces(g2);
		
		drawConsole(g2);
		
		if (state == 1)
			drawSelectionPanel(g2);

	}

	
	public void mouseClicked(MouseEvent e) {
		
		Location clicked = new Location((int)e.getY()/90, (int)e.getX()/90);
	
		if (state == 0){
			System.out.println("Click "+ click);
			System.out.println("    c = " + clicked.column);
			System.out.println("    r = " + clicked.row);
			
			
			if(click == 1){
				if(board[clicked.row][clicked.column].getPlayer() == player){
					from = clicked;
					highlight = clicked;
					System.out.println("    " + board[clicked.row][clicked.column]);
					
					if(!board[from.getRow()][from.getColumn()].stuck(board, from))
						click = 2;
					else
						click = 1;		
				}
			}
			
			else if(click == 2){
				to = clicked;
				
				if (board[from.row][from.column].isValidMove(from, to, board)){
					if (board[to.row][to.column] instanceof King) {
						state = 2;
						this.move(from, to);
						highlight.setRow(-99);
						System.out.println("    Valid move");
					}
					
					else{
						this.move(from, to);
						highlight.setRow(-99);
						System.out.println("    Valid move");
						click = 1;
						
						if (player == 1)			//If we have time and the will, we should change our player number assignments to 1 
							player++;				//and -1 (for the entire project), so we can just say player *= -1
						else if (player == 2)
							player--;
					}
				}
			}
		}
		
		
		else if (state == 1){
			for (PieceIcon p : promotionSelections){
				if (p.getBounds().contains(e.getPoint()) && player == 1){
					board[risingPawn.row][risingPawn.column] = p.getNewPiece(2);
					state = 0;
				}
				else if (p.getBounds().contains(e.getPoint()) && player == 2){
					board[risingPawn.row][risingPawn.column] = p.getNewPiece(1);
					state = 0;
				}
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
	
	public void move(Location f, Location t){
		Piece p = board[f.getRow()][f.getColumn()];
		board[f.getRow()][f.getColumn()] = new Filler();
		board[t.getRow()][t.getColumn()] = p;
		
		if(p instanceof Pawn){
			Pawn p1 = (Pawn) p;
			p1.setFirstTurn(false);
			
			if (state != 2 && (t.getRow() == 0 && p.getPlayer() == 1) || (t.getRow() == 7 && p.getPlayer() == 2)){
				risingPawn = new Location(t.getRow(), t.getColumn());
				state = 1;
			}
		}
		
	}
	
	public void drawBoard(Component c, Graphics g2){
		g2.setColor(Color.getHSBColor(0, 200, 1));
		g2.fillRect(0, 0, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		
		g2.setColor(Color.BLACK);
		g2.drawLine(SQUARE_WIDTH*8, 0, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		g2.drawLine(0, SQUARE_WIDTH*8, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		g2.drawLine(0, 0, SQUARE_WIDTH*8, 0);
		g2.drawLine(0, 0, 0, SQUARE_WIDTH*8);
		
		for(int i = 0; i <8; i+=2)
			for (int j = 1; j<8; j+=2)
			{
				g2.setColor(Color.gray.darker());
				g2.fillRect(i*SQUARE_WIDTH,j*SQUARE_WIDTH,SQUARE_WIDTH,SQUARE_WIDTH);
			}
		
		for(int i = 1; i <8; i+=2)
			for (int j = 0; j<8; j+=2)
			{
				g2.setColor(Color.gray.darker());
				g2.fillRect(i*SQUARE_WIDTH,j*SQUARE_WIDTH,SQUARE_WIDTH,SQUARE_WIDTH);
			}
		
		ClassLoader cldr = this.getClass().getClassLoader();	
		ImageIcon woodGrain = new ImageIcon(cldr.getResource("images/wood overlay.png"));
		woodGrain.paintIcon(c, g2, 0, 0);
	}
	
	public void drawHighlight(Graphics g2){
		if(highlight.getRow() >= 0 && highlight.getRow() <= 8){
			g2.setColor(Color.YELLOW);
			for (int i = 0; i < 4; i++){
				g2.drawRect(highlight.column*SQUARE_WIDTH+i, highlight.row*SQUARE_WIDTH+i, SQUARE_WIDTH-2*i, SQUARE_WIDTH-2*i);
			}
		}
	}
	
	public void drawPieces(Graphics g2){
		for(int i = 0; i<board.length;  i++){
			for(int j = 0; j<board[i].length; j++){
				board[i][j].draw(g2, this, new Location(i,j));
			}
		}
	}
	
	public void drawConsole(Graphics g2){
		g2.setColor(Color.GREEN.darker().darker().darker());
		g2.fillRect(0, SQUARE_WIDTH*8+2, SQUARE_WIDTH*8, 30);		
		g2.setColor(Color.BLACK);
		g2.drawRect(0, SQUARE_WIDTH*8+2, SQUARE_WIDTH*8, 30);
		
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		
		if (state == 2 && player == 1)
			g2.drawString("WHITE WINS!", 260, 747);
		else if (state == 2 && player == 2)
			g2.drawString("BLACK WINS!", 260, 747);

		else if (player == 1)
			g2.drawString("WHITE'S TURN", 255, 747);
		else if (player == 2)
			g2.drawString("BLACK'S TURN", 255, 747);
	}
	
	public void drawSelectionPanel(Graphics g2){
		g2.setColor(Color.GREEN.darker());
		g2.fillRect(160, 240, 400, 200);
		g2.setColor(Color.BLACK);
		g2.drawRect(160, 240, 400, 200);
		
		for (PieceIcon p : promotionSelections){
			if (player == 1)
				p.draw(this, g2, 2);
			else if (player == 2)
				p.draw(this, g2, 1);
		}
	}

}