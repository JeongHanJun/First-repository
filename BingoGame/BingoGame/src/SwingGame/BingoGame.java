package SwingGame;

import java.awt.*;
import java.awt.event.*;
//import java.io.File;

import javax.swing.*;
import javax.swing.Timer;
import java.util.Random;


public class BingoGame {
	static JPanel panelNorth; 	// top view  게임정보(title)
	static JPanel panelCenter;  // Game view 실제 게임 화면
	static JLabel LabelMessage;
	static JButton[] buttons = new JButton[16];
	static String[] images = {
		"갓 라이언.jpg", "라이언 뀨.png", "라이언 박수.jpg",
		"라이언 볼꼬집.png", "쌍따봉 라이언.jpg", "치어리더 라이언.png",
		"하트 라이언.png",  "히어로즈 라이언.png"	,
		"갓 라이언.jpg", "라이언 뀨.png", "라이언 박수.jpg",
		"라이언 볼꼬집.png", "쌍따봉 라이언.jpg", "치어리더 라이언.png",
		"하트 라이언.png",  "히어로즈 라이언.png"	
	};
	
	static int openCount = 0; // 카드 오픈 카운트
	static int buttonindexsave1 = 0; // 첫번쨰 카드 opened card index
	static int buttonindexsave2 = 0; // 두번쨰 카드 opened card index
	static Timer timer;
	static int trycount = 0; // 시도 횟수
	static int successcount = 0; // bingo count
	
	

	@SuppressWarnings("serial")
	static class MyFrame extends JFrame implements ActionListener 
	{
		public MyFrame(String title) {
			super(title);
			this.setLayout(new BorderLayout());
			this.setSize(512, 612); // 화면크기
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			initUI( this );	//screen initialize setting = screen ui set
			
			mixCard(); // mix cards
			this.pack(); // 가장자리, 여백
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
				LabelMessage.setText("같은 라이언 찾기 ~ " + trycount+" 번째 시도");
				
				//judge logic
				boolean isbingo = checkCard(buttonindexsave1, buttonindexsave2);
				if( isbingo == true)
				{
				//	playsound("good.mp3");
					openCount = 0;
					successcount++;
					if( successcount == 8 )
					{
						LabelMessage.setText(" 게임 끝 "+  trycount + "번째 시도" );
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
				if ( buttons[i] == btn )	// same instance인지
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
			int random = rand.nextInt(15) + 1; // 1~15사이 random generate
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
		LabelMessage = new JLabel("같은 라이언 찾기 ~ " + trycount+" 번째 시도");
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
			
			// 버튼 클릭 이벤트
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


