package Server;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerGui {
    private JFrame serverFrame;
    private JTextArea chatTextArea;
    protected JTextField chatTextField;
    protected JButton sendMsg;
    private JScrollPane scrollPane;
    private JList list;
    private DefaultListModel model;
    protected String noticeMsg = "공지를 입력하세요　";
    public ServerGui() {
        serverFrame = new JFrame();
        serverFrame.setDefaultCloseOperation(serverFrame.EXIT_ON_CLOSE);
        serverFrame.setBounds(100, 100, 825, 475);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        serverFrame.setContentPane(contentPane);
        contentPane.setLayout(null);

        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setBounds(18, 12, 567, 384);
        chatTextArea.setBackground(Color.WHITE);
        chatTextArea.setCaretColor(Color.WHITE);

        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        chatTextArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        scrollPane = new JScrollPane(chatTextArea, scrollPane.VERTICAL_SCROLLBAR_ALWAYS, scrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBounds(18, 12, 567, 384);
        contentPane.add(scrollPane);
        scrollPane.setVisible(true);

        //contentPane.add(chatTextArea);

        chatTextField = new JTextField();
        chatTextField.setColumns(30);
        chatTextField.setBounds(18, 403, 500, 23);
        chatTextField.setText(noticeMsg);
        //chatTextField.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));

        contentPane.add(chatTextField);

        chatTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (chatTextField.getText().equals(noticeMsg)) {
                    chatTextField.setText("");
                }
            }
        });

        chatTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (chatTextField.getText().equals(noticeMsg)) {
                    chatTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (chatTextField.getText().isEmpty()) {
                    chatTextField.setText(noticeMsg);
                }
            }
        });


        sendMsg = new JButton("전송");
        sendMsg.setBounds(525, 403, 60, 23);
        contentPane.add(sendMsg);

        Label userListLabel = new Label("CHAT USER");
        userListLabel.setBounds(667, 16, 100, 16);
        contentPane.add(userListLabel);

        model = new DefaultListModel();

        list = new JList(model);
        list.setBounds(602, 41, 195, 385);
        //list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        list.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
        contentPane.add(list);

        serverFrame.setResizable(false);
        serverFrame.setTitle("서버");
        serverFrame.setVisible(true);
    }

    public void setFrameVisible() {
        serverFrame.setVisible(true);
    }

    public void setTextFieldBlank() {
        chatTextField.setText("");
    }

    public void appendMessage(String message) {
        chatTextArea.append(message + "\n");
        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
    }
    public void appendUserList(String user) {
        model.addElement(user);
    }
    public void removeUserList(String user) {
        model.removeElement(user);
    }
    public String getChatMessage() {
        return chatTextField.getText();
    }
}
