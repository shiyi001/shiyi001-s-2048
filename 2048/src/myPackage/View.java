package myPackage;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
	Control control = null;
	Model model = null;
	
	JFrame mainFrame;
	JPanel paintCanvas;
	JPanel head;
	Label labelScore;
	Label labelSteps;
	Label[][] label = null; //new Label[model.maxX][model.maxY];
	
	public static final int canvasWidth = 200;
	public static final int canvasHeight = 300;
	
	public View(Model model, Control control){
		this.model = model;
		this.control = control;
		
		mainFrame = new JFrame("2048");
		
		//Container cp = mainFrame.getContentPane();
		head = new JPanel();
		head.setLayout(new GridLayout(1,2));
		mainFrame.add(head,BorderLayout.NORTH);
		labelScore = new Label("Score:");
		labelSteps = new Label("Steps:");
//		labelScore.setBackground (Color.ORANGE);
//		labelSteps.setBackground (Color.ORANGE);
		labelScore.setAlignment(Label.CENTER);
		labelSteps.setAlignment(Label.CENTER);
		labelScore.setFont(new Font("",2,15));
		labelSteps.setFont(new Font("",2,15));
		
		head.add(labelScore);
		head.add(labelSteps);
		
		paintCanvas = new JPanel();
		paintCanvas.setPreferredSize(new Dimension(canvasWidth+1, canvasHeight+1));
		paintCanvas.addKeyListener(control);
		mainFrame.add(paintCanvas, BorderLayout.CENTER);
		paintCanvas.setLayout(new GridLayout(model.maxX,model.maxY));
		
		label = new Label[model.maxX][model.maxY];
        for ( int i = 0; i < label.length; i++ )
        {
            for ( int j = 0; j < label[i].length; j++ )
            {
                label[i][j] = new Label ();
                label[i][j].setFont(new Font("",1,30));
                label[i][j].setAlignment(Label.CENTER);
                if (( i + j ) % 2 == 0)
                    label[i][j].setBackground (Color.WHITE);
                else
                    label[i][j].setBackground (Color.pink);
                paintCanvas.add (label[i][j]);
            }
        }
		
		mainFrame.addKeyListener(control);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		
		int[][] ma = model.matrix;
		for (int i = 0; i < model.maxX; i++){
			for (int j = 0; j < model.maxY; j++){
				String str;
				if (ma[i][j] != 0) str = ""+ma[i][j];
					else str = ""; 
				label[i][j].setText(str);
			}
		}
		
		updateScore();
	}
	
	public void updateScore(){
		String str1 = "Score:   " + model.score;
		String str2 = "Steps:    "+model.countMove;
		labelScore.setText(str1);
		labelSteps.setText(str2);
	}
	
    public void update(Observable o, Object arg) {
    	//System.out.println("YEs");
        paint(paintCanvas.getGraphics());
    }
    
//    private Image iBuffer;
//    private Graphics gBuffer;
//    public void repaint(Graphics g) {
//        if (iBuffer == null) {
//            iBuffer = paintCanvas.createImage(paintCanvas.getSize().width, paintCanvas.getSize().height);
//            gBuffer = iBuffer.getGraphics();
//        }
//        gBuffer.setColor(paintCanvas.getBackground());
//        gBuffer.fillRect(0, 0, paintCanvas.getSize().width, paintCanvas.getSize().height);
//        paint(gBuffer);
//        g.drawImage(iBuffer, 0, 0, paintCanvas);
//    }   
}
