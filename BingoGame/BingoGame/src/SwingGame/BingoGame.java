package SwingGame;

import java.awt.*;
import java.awt.event.*;
//import java.io.File;

import javax.swing.*;
import javax.swing.Timer;
import java.util.Random;


public class BingoGame {
	static JPanel panelNorth; 	// top view  ��������(title)
	static JPanel panelCenter;  // Game view ���� ���� ȭ��
	static JLabel LabelMessage;
	static JButton[] buttons = new JButton[16];
	static String[] images = {
		"�� ���̾�.jpg", "���̾� ��.png", "���̾� �ڼ�.jpg",
		"���̾� ������.png", "�ֵ��� ���̾�.jpg", "ġ��� ���̾�.png",
		"��Ʈ ���̾�.png",  "������� ���̾�.png"	,
		"�� ���̾�.jpg", "���̾� ��.png", "���̾� �ڼ�.jpg",
		"���̾� ������.png", "�ֵ��� ���̾�.jpg", "ġ��� ���̾�.png",
		"��Ʈ ���̾�.png",  "������� ���̾�.png"	
	};
	
	static int openCount = 0; // ī�� ���� ī��Ʈ
	static int buttonindexsave1 = 0; // ù���� ī�� opened card index
	static int buttonindexsave2 = 0; // �ι��� ī�� opened card index
	static Timer timer;
	static int trycount = 0; // �õ� Ƚ��
	static int successcount = 0; // bingo count
	
	

	@SuppressWarnings("serial")
	static class MyFrame extends JFrame implements ActionListener 
	{
		public MyFrame(String title) {
			super(title);
			this.setLayout(new BorderLayout());
			this.setSize(512, 612); // ȭ��ũ��
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			initUI( this );	//screen initialize setting = screen ui set
			
			mixCard(); // mix cards
			this.pack(); // �����ڸ�, ����
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Button Clicked");
			
			if(openCount == 2)
			{
				return;
			}
			
			JButton btn = (JButton)e.getSource();
			int index = getButtonIndex( btn );
			//System.out.println(" Button Index "+index);
			
			btn.setIcon(changeImage(images[index]));
			openCount++;
			if(openCount == 1) // first card
			{
				buttonindexsave1 = index;
			}
			else if( openCount == 2) // second card
			{
				buttonindexsave2 = index;
				trycount++;
				LabelMessage.setText("���� ���̾� ã�� ~ " + trycount+" ��° �õ�");
				
				//judge logic
				boolean isbingo = checkCard(buttonindexsave1, buttonindexsave2);
				if( isbingo == true)
				{
				//	playsound("good.mp3");
					openCount = 0;
					successcount++;
					if( successcount == 8 )
					{
						LabelMessage.setText(" ���� �� "+  trycount + "��° �õ�" );
					}
				}
				else
				{
					backToQuestion();
				}
			}
			
		}
		public void backToQuestion () {
			// timer
			timer = new Timer(500, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Timer . ");
					
				//	playsound("again.mp3");
					openCount = 0;
					buttons[buttonindexsave1].setIcon( changeImage("question.png"));
					buttons[buttonindexsave2].setIcon( changeImage("question.png"));
					timer.stop();
					
				}
			});
			timer.start();
		}
		
		public boolean checkCard(int index1, int index2)
		{
			if( index1 == index2)
			{
				return false;
			}
			if(images[index1].equals(images[index2]))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	
		public int getButtonIndex ( JButton btn ) {
			int index = 0;
			for( int i = 0; i < 16 ; i++)
			{
				if ( buttons[i] == btn )	// same instance����
				{
					index = i;
				}
			}
			return index;
		}
	
	}
	static void mixCard( ) {
		Random rand = new Random();
		for( int i = 0; i < 100 ; i++)
		{
			int random = rand.nextInt(15) + 1; // 1~15���� random generate
			// swap logic
			String temp = images[0];
			images[0] = images[random];
			images[random] = temp;
		}
	}
	
	static void initUI( MyFrame myFrame) {
		panelNorth = new JPanel();
		panelNorth.setPreferredSize(new Dimension(400, 100));
		panelNorth.setBackground(Color.BLUE);
		LabelMessage = new JLabel("���� ���̾� ã�� ~ " + trycount+" ��° �õ�");
		LabelMessage.setPreferredSize(new Dimension(400, 100));
		LabelMessage.setForeground(Color.WHITE);
		LabelMessage.setFont(new Font("MONACO", Font.BOLD, 20));
		LabelMessage.setHorizontalAlignment(JLabel.CENTER);
		panelNorth.add(LabelMessage);
		myFrame.add("North", panelNorth);
		
		panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(4, 4));
		panelCenter.setPreferredSize(new Dimension(400, 400));
		for ( int i = 0; i < 16; i++)
		{
			buttons[i] = new JButton();
			buttons[i].setPreferredSize(new Dimension(100, 100));
			buttons[i].setIcon(changeImage("question.png"));
			
			// ��ư Ŭ�� �̺�Ʈ
			buttons[i].addActionListener(myFrame);
			
			
			panelCenter.add(buttons[i]);
		}
		myFrame.add("Center", panelCenter);
		
			
	}
	static ImageIcon changeImage(String filename) {
		ImageIcon icon = new ImageIcon("./img/"+ filename);
		Image originImage = icon.getImage();
		Image changedImage = originImage.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
		ImageIcon icon_new = new ImageIcon(changedImage);
		return icon_new;
		
		
	}
	
	
	public static void main(String[] args) {
		new MyFrame("Bingo Game");
		

	}
	
}


