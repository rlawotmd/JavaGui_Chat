package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EnterGui extends JFrame {
    private JPanel enterPane;
    private JTextField textField;
    private JButton enterButton;
    private String name;

    public EnterGui() {
        setTitle("입장");
        setResizable(false);
        enterPanel();
        nickNameLabel();
        enterButtonEvent();
        setVisible(true);
    }

    public void enterPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        enterPane = new JPanel();
        setContentPane(enterPane);
        enterPane.setLayout(null);
    }

    public void nickNameLabel() {
        JLabel nickName = new JLabel("이름 입력");
        nickName.setBounds(180, 60, 80, 15);
        enterPane.add(nickName);

        textField = new JTextField();
        textField.setBounds(155, 90, 115, 20);
        enterPane.add(textField);
        textField.setColumns(10); // 글자 수 제한
    }

    public void enterButtonEvent() {
        enterButton = new JButton("입장하기");
        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendToChatGui();
            }
        });
        enterButton.setBounds(165, 134, 97, 23);
        enterPane.add(enterButton);

        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String s = textField.getText();
                    if(!s.isEmpty()) {
                        sendToChatGui();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void sendToChatGui() {
        name = textField.getText();
        setVisible(false);
        new ChatGui(name);
    }



}
