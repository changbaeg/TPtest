import javax.swing.*;
import java.awt.*;
 
public class GameView extends JPanel{
    Image back,rocket;
    int x=15,y=15;
    public GameView()
    {
        back=Toolkit.getDefaultToolkit().
                getImage("C:.\\background.jpg");
        rocket=Toolkit.getDefaultToolkit().
                getImage("C:.\\rpg.png");
    }
    // 이미지 출력..'rpg.png' 파일없음...뭔 이미지인지 모르겠음. 로켓이미지였음.
    public void paint(Graphics g)
    {
        g.drawImage(back,0,0,getWidth(),getHeight(),this);
        g.drawImage(rocket,x,y,150,150,this);
    } 
}

//이거 내가 걍..테스트로 해본것도 같이 위에 딸려 올라간거같은데...뭐임ㅋ
//로켓이미지 이동하는 코드인데..