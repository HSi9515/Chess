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
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements MouseListener{
	
	private final int SQUARE_WIDTH = 90;    // The width of one space on the board.  Constant used for drawing board.
	private Location from;   			    // holds the coordinates of the square that the user would like to move from.
	private Location to;   				    // holds the coordinates of the square that the user would like to move to.
	private Location highlight;				// holds the coordinates of the square that the user would like to move to.
	private int click;   						
	private Piece[][] board; 				// an 8x8 board of 'Pieces'.  Each spot should be filled by one of the chess pieces or a 'space'. 
	
	private int player;						//1 = white, 2 = black
	private Font font;
	
	public GraphicsPanel()
	{
		setPreferredSize(new Dimension(SQUARE_WIDTH*8+2,SQUARE_WIDTH*8+34));      
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
			board[0][i] = new Pawn(1);  //this is where you can instantiate your pieces to test them
			board[7][i] = new Pawn(2);   //just delete new Pawn and replace it with whichever pieces your making
		}
		
		board[1][1] = new Queen();
		board[1][2] = new King();
		board[1][3] = new Bishop();
		board[6][6] = new Queen(2);
				
		click = 1;
		
		player = 1;
		
		font = new Font("Optima", Font.PLAIN, 30); //Andale Mono is ok too
	}
	
	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//   			first rendered.  It can also be called by this.repaint()
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.getHSBColor(0, 200, 1));
		g2.fillRect(0, 0, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		
		// Draw the board
		g2.setColor(Color.BLACK);
		g2.drawLine(SQUARE_WIDTH*8, 0, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		g2.drawLine(0, SQUARE_WIDTH*8, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		g2.drawLine(0, 0, SQUARE_WIDTH*8, 0);
		g2.drawLine(0, 0, 0, SQUARE_WIDTH*8);
		
		for(int i = 0; i <8; i+=2)
			for (int j = 0; j<8; j+=2)
			{
				g2.setColor(Color.gray.darker());
				g2.fillRect(i*SQUARE_WIDTH,j*SQUARE_WIDTH,SQUARE_WIDTH,SQUARE_WIDTH);
			}
		
		for(int i = 1; i <8; i+=2)
			for (int j = 1; j<8; j+=2)
			{
				g2.setColor(Color.gray.darker());
				g2.fillRect(i*SQUARE_WIDTH,j*SQUARE_WIDTH,SQUARE_WIDTH,SQUARE_WIDTH);
			}
		
		ClassLoader cldr = this.getClass().getClassLoader();	
		ImageIcon woodGrain = new ImageIcon(cldr.getResource("images/wood overlay.png"));
		woodGrain.paintIcon(this, g2, 0, 0);
		
		if(highlight.getRow() >= 0 && highlight.getRow() <= 8){
			g2.setColor(Color.YELLOW);
			//g2.fillRect(highlight.getColumn()*SQUARE_WIDTH, highlight.getRow()*SQUARE_WIDTH,SQUARE_WIDTH,SQUARE_WIDTH);
			for (int i = 0; i < 4; i++){
				g2.drawRect(highlight.column*SQUARE_WIDTH+i, highlight.row*SQUARE_WIDTH + i, SQUARE_WIDTH - 2*i, SQUARE_WIDTH - 2*i);
			}
		}
		
		//Drawing the pieces and spaces
		for(int i = 0; i<board.length;  i++){
			for(int j = 0; j<board[i].length; j++){
				board[i][j].draw(g2, this, new Location(i,j));
			}
		}
		
		g2.setColor(Color.GREEN.darker().darker().darker());
		g2.fillRect(0, SQUARE_WIDTH*8+2, SQUARE_WIDTH*8, 30);		
		g2.setColor(Color.BLACK);
		g2.drawRect(0, SQUARE_WIDTH*8+2, SQUARE_WIDTH*8, 30);
		
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		if (player == 1)
			g2.drawString("WHITE'S TURN", 255, 747);
		else if (player == 2)
			g2.drawString("BLACK'S TURN", 255, 747);

	}

	
	public void mouseClicked(MouseEvent e) {
		
		Location clicked = new Location((int)e.getY()/90, (int)e.getX()/90);
	
		System.out.println("Click "+ click);
		System.out.println("    c = " + clicked.column);
		System.out.println("    r = " + clicked.row);
		
		
		if(click == 1){
			
			if(board[clicked.row][clicked.column].getPlayer() == player){ //1 || board[clicked.row][clicked.column].getPlayer() == 2
			
				from = clicked;
				highlight = clicked;
				System.out.println("    Player " + player);
				click = 2;			
			
			}
			
		}
		
		else if(click == 2){
						
			to = clicked;
			
			if (board[from.row][from.column].isValidMove(from, to, board)){
					
				this.move(from, to);
				highlight.setRow(-99);
				System.out.println("    Valid move");
				click = 1;
				
				if (player == 1)			//see how this takes 4 lines of code? If we have time and the will, we should change our
					player++;				//player number assignments to 1 and -1 (for the entire project), so we can just say
				else if (player == 2)		//player *= -1;
					player--;
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
		
	}
	

}