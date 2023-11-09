package Client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class ChatGui extends JFrame {
    private JPanel chatPanel;
    private JTextField textMsg;
    private JTextArea chatLog;
    private JButton sendMsg;
    private JScrollBar scrollBar;
    private JScrollPane scrollPane;
    private BufferedReader reader;
    public PrintWriter writer;

    public ChatGui(String id) {
        String writeMsg = "메세지를 입력하세요　";
        String msg = "login/" + id;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 500);
        chatPanel = new JPanel();
        setTitle("채팅_" + id);
        setResizable(false);
        chatPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(chatPanel);
        chatPanel.setLayout(null);

        JLabel chatLabel = new JLabel("채팅방");
        chatLabel.setBounds(240, 10, 95, 15);
        chatPanel.add(chatLabel);

        chatLog = new JTextArea();
        chatLog.setEditable(false); // 편집 여부
        chatLog.setCaretColor(Color.white);
        chatLog.setText("채팅 로그 입니다.\n");
        chatLog.setBounds(18, 25, 500, 400);

        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        chatLog.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        chatLog.getScrollableTracksViewportHeight();
        /*
        chatPanel.add(chatLog);*/

        scrollPane = new JScrollPane(chatLog, scrollPane.VERTICAL_SCROLLBAR_ALWAYS, scrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBounds(18, 25, 500, 400);
        chatPanel.add(scrollPane);
        scrollPane.setVisible(true);

        textMsg = new JTextField();
        textMsg.setText(writeMsg);
        textMsg.setBounds(18, 430, 440, 20);
        chatPanel.add(textMsg);
        textMsg.setColumns(10);

        textMsg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textMsg.getText().equals(writeMsg)) {
                    textMsg.setText("");
                }
            }
        });

        textMsg.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textMsg.getText().equals(writeMsg)) {
                    textMsg.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textMsg.getText().isEmpty()) {
                    textMsg.setText(writeMsg);
                }
            }
        });

        textMsg.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String s = textMsg.getText();
                    if(!s.isEmpty()) {
                        if(!s.equals(writeMsg)) {
                            String text = textMsg.getText();
                            writer.println(text);
                            //uploadText(text);
                            textMsg.setText(""); // 초기화
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        sendMsg = new JButton("전송");
        sendMsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = textMsg.getText();
                if(!s.isEmpty()) {
                    if(!s.equals(writeMsg)) {
                        String text = textMsg.getText();
                        writer.println(text);
                        //uploadText(text);
                        textMsg.setText(""); // 초기화
                    }
                }
            }
        });
        sendMsg.setBounds(460, 430, 58, 20);
        chatPanel.add(sendMsg);

        setVisible(true);

        try {
            // 서버 정보
            String serverIP = "localhost";
            int serverPort = 8888;

            // 서버 연결
            Socket socket = new Socket(serverIP, serverPort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 서버로 부터 메세지를 읽는 스레드
            Thread readThread = new Thread(new ServerMessageReader());
            readThread.start();

            // 서버로 메세지 전송
            OutputStream outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);
            writer.println(msg);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerMessageReader implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("SERVER-Console : " + message);
                    uploadText(message);
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadText(String message) {
        /*scrollPane.add(message + "\n", );
        scrollPane.();*/
        chatLog.append(message + "\n");
        chatLog.setCaretPosition(chatLog.getDocument().getLength());
    }
}