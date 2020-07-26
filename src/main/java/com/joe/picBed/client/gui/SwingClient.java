package com.joe.picBed.client.gui;

import com.hujinwen.client.minio.MinIOCluster;
import com.hujinwen.entity.minio.MinIONode;
import com.hujinwen.exception.minio.MinioInitializeException;
import com.hujinwen.exception.minio.MinioPutObjectException;
import com.hujinwen.utils.FileUtils;
import com.hujinwen.utils.RandomUtils;
import com.joe.picBed.utils.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Created by joe on 2020/7/21
 */
public class SwingClient {

    private MinIOCluster minIOCluster;

    private String[] endpoints;

    private String bucketName;

    private String accessKeyId;

    private String secretAccessKey;

    private static final MessageFormat URL_FORMAT = new MessageFormat("{0}/{1}/{2}");


    private void init() {
        final Properties properties = new Properties();
        try (
                final FileInputStream fileInputStream = new FileInputStream("conf.properties");
        ) {
            properties.load(fileInputStream);
            endpoints = properties.getProperty("endpoints").split(";");
            accessKeyId = properties.getProperty("accessKeyId");
            secretAccessKey = properties.getProperty("secretAccessKey");
            bucketName = properties.getProperty("bucketName");

            final MinIONode[] nodes = new MinIONode[endpoints.length];
            for (int i = 0; i < endpoints.length; i++) {
                if (!endpoints[i].startsWith("http")) {
                    endpoints[i] = "http://" + endpoints[i];
                }
                nodes[i] = new MinIONode(endpoints[i], accessKeyId, secretAccessKey);
            }
            minIOCluster = new MinIOCluster(nodes);
        } catch (IOException | MinioInitializeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    /**
     * 创建校验面板
     */
    private void createPassPanel(JFrame frame, final CardLayout cardLayout) {
        final JPanel jPanel = new JPanel();
        final BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);

        final JPanel jPanel1 = new JPanel(new BorderLayout());
        final JPanel jPanel2 = new JPanel(new BorderLayout());
        final JPanel jPanel3 = new JPanel(new BorderLayout());
        final JPanel jPanel4 = new JPanel(new BorderLayout());

        final JLabel jLabel = new JLabel("endpoints:      ");
        final JLabel jLabel1 = new JLabel("bucketName:     ");
        final JLabel jLabel2 = new JLabel("accessKeyId:    ");
        final JLabel jLabel3 = new JLabel("secretAccessKey:");

        final JTextField jTextField = new JTextField();
        final JTextField jTextField1 = new JTextField();
        final JTextField jTextField2 = new JTextField();
        final JTextField jTextField3 = new JTextField();

        final Button submit = new Button("submit");

        jPanel1.add(jLabel, BorderLayout.WEST);
        jPanel1.add(jTextField, BorderLayout.CENTER);
        jPanel2.add(jLabel1, BorderLayout.WEST);
        jPanel2.add(jTextField1, BorderLayout.CENTER);
        jPanel3.add(jLabel2, BorderLayout.WEST);
        jPanel3.add(jTextField2, BorderLayout.CENTER);
        jPanel4.add(jLabel3, BorderLayout.WEST);
        jPanel4.add(jTextField3, BorderLayout.CENTER);

        jPanel.add(jPanel1);
        jPanel.add(jPanel2);
        jPanel.add(jPanel3);
        jPanel.add(jPanel4);
        jPanel.add(submit);

        final Container contentPane = frame.getContentPane();
        contentPane.add(jPanel, "passPanel");

        submit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final String endpoints = jTextField.getText();
                final String bucketName = jTextField1.getText();
                final String accessKeyId = jTextField2.getText();
                final String secretAccessKey = jTextField3.getText();
                try (
                        final FileOutputStream fileOutputStream = new FileOutputStream("conf.properties")
                ) {
                    final Properties properties = new Properties();
                    properties.setProperty("endpoints", endpoints);
                    properties.setProperty("bucketName", bucketName);
                    properties.setProperty("accessKeyId", accessKeyId);
                    properties.setProperty("secretAccessKey", secretAccessKey);
                    properties.store(fileOutputStream, "");
                    cardLayout.show(contentPane, "updatePanel");
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

            }
        });

    }

    /**
     * 创建上传图片面板
     */
    private void createUpdatePanel(JFrame frame, final CardLayout cardLayout) {
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        final JButton jButton = new JButton("点击上传");
        final JButton jButton1 = new JButton(new ImageIcon(FileUtils.getResourcePath() + "/static/img/1.jpg"));

        final JPanel jPanel1 = new JPanel();
        jPanel1.add(jButton);
        final JScrollPane jScrollPane = new JScrollPane(jButton1);

        jPanel.add(jPanel1, BorderLayout.NORTH);
        jPanel.add(jScrollPane, BorderLayout.CENTER);

        final Container contentPane = frame.getContentPane();
        contentPane.add(jPanel, "updatePanel");

        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final File file = new File("conf.properties");
                if (!file.exists()) {
                    cardLayout.show(contentPane, "passPanel");
                    return;
                }
                if (minIOCluster == null) {
                    init();
                }

                final JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.showOpenDialog(null);
                File imgFile = jFileChooser.getSelectedFile();

                try {
                    final String objectName = Tools.getFilePath(imgFile.getPath());
                    minIOCluster.putImg(bucketName, objectName, new FileInputStream(imgFile));
                    final String imgUrl = URL_FORMAT.format(new Object[]{RandomUtils.randomChoice(endpoints), bucketName, objectName});
                    jButton1.setIcon(new ImageIcon(new URL(imgUrl)));
                    frame.pack();
                } catch (MinioPutObjectException | FileNotFoundException | MalformedURLException minioPutObjectException) {
                    minioPutObjectException.printStackTrace();
                }

            }
        });
    }

    /**
     * 创建菜单
     */
    private void createMenu(JFrame frame) {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("File");
        final JMenu menu1 = new JMenu("Edit");

        menuBar.add(menu);
        menuBar.add(menu1);

        final JMenuItem jMenuItem = new JMenuItem("Quit", KeyEvent.SHIFT_MASK);
        jMenuItem.addActionListener(e -> {
            final int sel = JOptionPane.showConfirmDialog(frame, "你选择了 Quit", "这是确认对话框", JOptionPane.YES_NO_CANCEL_OPTION);
            if (sel == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        menu.add(jMenuItem);

        final JMenuItem jMenuItem1 = new JMenuItem("Save");
        jMenuItem1.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "你选择了 Save", "这是输出信息对话框！", JOptionPane.PLAIN_MESSAGE);
        });
        menu1.add(jMenuItem1);

        frame.setJMenuBar(menuBar);
    }


    public static void main(String[] args) {
        final SwingClient swingClient = new SwingClient();

        final JFrame jFrame = new JFrame();

        final CardLayout cardLayout = new CardLayout();

        final Container contentPane = jFrame.getContentPane();
        contentPane.setLayout(cardLayout);

        swingClient.createMenu(jFrame);
        swingClient.createUpdatePanel(jFrame, cardLayout);
        swingClient.createPassPanel(jFrame, cardLayout);

        cardLayout.show(contentPane, "updatePanel");

        jFrame.setVisible(true);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
