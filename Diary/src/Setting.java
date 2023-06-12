import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Setting {
    private JFrame mainFrame;
    private boolean isBgmOn = true; // BGM 상태 변수
    private Player bgmPlayer; // BGM 재생용 Player 객체

    public Setting() {
        initializeUI();
    }

    private void initializeUI() {
       mainFrame = new JFrame("Option");
       mainFrame.setLayout(new BorderLayout());
        
        ImageIcon settingsIcon = new ImageIcon("img\\setting.png"); // 설정 이미지 파일 경로에 맞게 수정
        settingsIcon = new ImageIcon(settingsIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); // 아이콘 크기 조정
        JLabel settingsLabel = new JLabel(settingsIcon);
        
        settingsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showSettingsPanel();
            }
        });
        
        JPanel eastPanel = new JPanel(); // 우측 상단에 위치할 빈 패널 생성
        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(settingsLabel, BorderLayout.NORTH); // settingsLabel을 NORTH 위치에 추가


        mainFrame.add(eastPanel, BorderLayout.EAST);
        mainFrame.setSize(200, 200);
        mainFrame.setLocationRelativeTo(null);
//      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void showSettingsPanel() {
        JOptionPane.showMessageDialog(mainFrame, createSettingsPanel());
    }

    private JPanel createSettingsPanel() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());

        // BGM On/Off 토글 버튼 구현
        JToggleButton bgmToggleButton = new JToggleButton();
        ImageIcon bgmOnIcon = new ImageIcon("img\\bgm_icon.png"); // BGM On 이미지 파일 경로에 맞게 수정
        ImageIcon bgmOffIcon = new ImageIcon("img\\bgm_off_icon.png"); // BGM Off 이미지 파일 경로에 맞게 수정
        bgmToggleButton.setIcon(new ImageIcon(bgmOnIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT))); // 아이콘 크기 조정
        bgmToggleButton.setSelectedIcon(new ImageIcon(bgmOffIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT))); // 아이콘 크기 조정
        bgmToggleButton.setSelected(isBgmOn); // 초기 상태 설정
        bgmToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isBgmOn = bgmToggleButton.isSelected(); // BGM 상태 변경
                if (isBgmOn) {
                    playBgm(); // BGM 재생
                    JOptionPane.showMessageDialog(mainFrame, "BGM이 켜졌습니다.");
                } else {
                    stopBgm(); // BGM 정지
                    JOptionPane.showMessageDialog(mainFrame, "BGM이 꺼졌습니다.");
                }
            }
        });
        settingsPanel.add(bgmToggleButton, BorderLayout.CENTER);

        // 로그아웃 버튼 구현
        JButton logoutButton = new JButton();
        ImageIcon logoutIcon = new ImageIcon("img\\logout_icon.png"); // 로그아웃 버튼 이미지 파일 경로에 맞게 수정
        logoutButton.setIcon(new ImageIcon(logoutIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT))); // 아이콘 크기 조정
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame, "로그아웃 되었습니다.");
                // 로그아웃 기능 실행
                // TODO: 로그아웃 기능 구현
            }
        });
        settingsPanel.add(logoutButton, BorderLayout.SOUTH);

        return settingsPanel;
    }

    private void playBgm() {
        try {
            bgmPlayer = new Player(new FileInputStream("music\\bgm.mp3"));
            Thread bgmThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        bgmPlayer.play();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }
                }
            });
            bgmThread.start();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    private void stopBgm() {
        if (bgmPlayer != null) {
            bgmPlayer.close();
        }
    }

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainFrame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Setting app = new Setting();
        app.start();
    }
}