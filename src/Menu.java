import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.*;

/**
 * Created by ashkanmehrkar on 12/8/16.
 */
public  class Menu extends JFrame {
    Tree tree;
    Tree illegal;
    ArrayList<String> commandHistory;
    private int tmp = 0;
    public Menu() {
        commandHistory = new ArrayList<String>();

        setSize(640, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Inverted Index");
        setLayout(null);

        final JRadioButton TSTButton = new JRadioButton("TST");
        final JRadioButton BSTButton = new JRadioButton("BST");
        final JRadioButton TrieButton = new JRadioButton("Trie");

        TSTButton.setLocation(400, 430);
        TSTButton.setSize(60, 25);

        BSTButton.setLocation(480, 430);
        BSTButton.setSize(60, 25);

        TrieButton.setLocation(560, 430);
        TrieButton.setSize(60, 25);

        final ButtonGroup treeSelector = new ButtonGroup();
        treeSelector.add(TSTButton);
        treeSelector.add(BSTButton);
        treeSelector.add(TrieButton);

        getContentPane().add(TSTButton);
        getContentPane().add(BSTButton);
        getContentPane().add(TrieButton);

        final JLabel text1 = new JLabel("Please enter address.");
        text1.setFont(new Font("Monospaced", text1.getFont().getStyle(), 17));
        text1.setLocation(10, 10);
        text1.setSize(500, 15);
        getContentPane().add(text1);

        final JTextField directoryTextField = new JTextField();
        directoryTextField.setFont(new Font("Monospaced", text1.getFont().getStyle(), 13));
        directoryTextField.setSize(500, 25);
        directoryTextField.setLocation(10, 30);
        getContentPane().add(directoryTextField);


        final JButton browseButton = new JButton("Browse");
        browseButton.setSize(100, 25);
        browseButton.setLocation(520, 30);
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setLocation(500, 500);
                fc.setSize(500, 500);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.showOpenDialog(browseButton);
                if (fc.getSelectedFile() != null)
                    directoryTextField.setText(fc.getSelectedFile().getPath());
            }
        });
        getContentPane().add(browseButton);

        final JTextArea result = new JTextArea();
        int resultSX = 610, resultSY = 295, resultLX = 10, resultLY = 65;
        result.setSize(resultSX, resultSY);
        result.setLocation(resultLX, resultLY);
        result.setEditable(false);
        result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        result.setFont(new Font("Monospaced", text1.getFont().getStyle(), 14));
        result.setLineWrap(true);
        JScrollPane SP = new JScrollPane(result);
        SP.setBounds(resultLX, resultLY, resultLX + resultSX, resultLY + resultSY);
        SP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(SP);

        final JLabel text2 = new JLabel("Please enter your command :");
        text2.setFont(new Font("Monospaced", text2.getFont().getStyle(), 17));
        text2.setLocation(10, 435);
        text2.setSize(300, 15);
        getContentPane().add(text2);

        final JTextField commandTextField = new JTextField();
        commandTextField.setFont(new Font("Monospaced", text1.getFont().getStyle(), 13));
        commandTextField.setSize(611, 25);
        commandTextField.setLocation(10, 455);
        getContentPane().add(commandTextField);
        commandTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] ss = commandTextField.getText().split(" ");
                commandHistory.add(commandTextField.getText());
                tmp++;
                switch (ss[0]) {
                    case "add" :
                        String[] s = commandTextField.getText().split(" ");
                        s[1] = s[1].concat(".txt");
                        boolean existInFolder = false;
                        for(int i = 0; i < tree.filesInFolder.size(); i++) {
                            if(s[1].equals(tree.filesInFolder.get(i)))
                                existInFolder = true;
                        }
                        if(existInFolder) {

                            boolean existInCurrent = false;
                            for(int i = 0; i < tree.currentFiles.size(); i++) {
                                if(s[1].equals(tree.currentFiles.get(i)))
                                    existInCurrent = true;
                            }
                            if(!existInCurrent) {

                                tree.add(new File(directoryTextField.getText().concat("/".concat(ss[1].concat(".txt")))), illegal);
                                result.setText("");
                                result.setText(result.getText() + "\n" + ss[1].concat(".txt") + " Added Successfully.");
                                commandTextField.setText("");

                            }

                            else
                                result.setText("Document Already Added.");


                        }
                        else
                        result.setText("Document Not Found.");

                        break;
                    case "del" :
                        String[] q = commandTextField.getText().split(" ");
                        q[1] = q[1].concat(".txt");
                        boolean existInFolder1 = false;
                        for(int i = 0; i < tree.filesInFolder.size(); i++) {
                            if(q[1].equals(tree.filesInFolder.get(i)))
                                existInFolder1 = true;
                        }
                        if (existInFolder1) {

                            boolean existInCurrent1 = false;
                            for(int i = 0; i < tree.currentFiles.size(); i++) {
                                if(q[1].equals(tree.currentFiles.get(i)))
                                    existInCurrent1 = true;
                            }

                            if(existInCurrent1) {

                                tree.delete(new File(directoryTextField.getText().concat("/".concat(ss[1].concat(".txt")))));
                                result.setText("");
                                result.setText(result.getText() + "\n" + ss[1].concat(".txt") + " Deleted Successfully.");

                            }

                            else
                                result.setText("Document Already Deleted.");

                        }
                        else
                            result.setText("Document Not Found.");
                        break;
                    case "update" :
                        tree.update(new File(directoryTextField.getText().concat("/".concat(ss[1].concat(".txt")))), illegal);
                        result.setText("");
                        result.setText(result.getText() + "\n" + ss[1].concat(".txt") + " Has Been Updated Successfully.");
                        break;
                    case "list" :
                        switch (ss[1]) {
                            case "-w":
                                result.setText("");
                                tree.words = new ArrayList<String>();
                                tree.wordsList();
                                for (String z : tree.words)
                                    result.setText(result.getText() + "\n" + z);
                                break;
                            case "-l" :
                                result.setText("");
                                for(String z : tree.currentFiles)
                                    result.setText(result.getText() + "\n" + z);
                                break;
                            case "-f" :
                                result.setText("");
                                for(String z : tree.filesInFolder)
                                    result.setText(result.getText() + "\n" + z);
                                break;
                        }
                        break;
                    case "search" :
                        switch (ss[1]) {
                            case "-w" :
                                String x = ss[2].substring(1, ss[2].length() - 1);
                                tree.search(x);
                                result.setText(tree.findWord(x));
                                break;
                            case "-s" :
                                ss[2] = ss[2].substring(1);
                                ss[ss.length - 1] = ss[ss.length - 1].substring(0, ss[ss.length - 1].length() - 1);
                                String tmp ="";
                                for(int i = 2; i < ss.length; i++) {
                                    if(!illegal.search(ss[i]))
                                        tmp = tmp.concat(ss[i] + " ");
                                }
                                result.setText(tree.findSentence(tmp));
                                break;
                        }
                        break;
                }
            }
        });
        commandTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP :
                        if(tmp > 0)
                            tmp--;
                        commandTextField.setText(commandHistory.get(tmp));
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        final JButton buildButton = new JButton("Build");
        buildButton.setSize(100, 25);
        buildButton.setLocation(10, 485);
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(BSTButton.isSelected()) {
                    tree = new BST();
                    illegal = new BST();
                }
                else if(TSTButton.isSelected()) {
                    tree = new TST();
                    illegal = new TST();
                }
                else if(TrieButton.isSelected()) {
                    tree = new Trie();
                    illegal = new Trie();
                }
                else
                    return;
                if(directoryTextField.getText().isEmpty())
                    return;
                illegal.add(new File("StopWords.txt"), null);
                File file = new File(directoryTextField.getText());
                long start = new Date().getTime();
                for(int i = 0; i < file.listFiles().length; i++ ) {
                    if(file.listFiles()[i].isFile() && file.listFiles()[i].getName().substring(file.listFiles()[i].getName().indexOf('.')).equals(".txt"))
                        tree.add(file.listFiles()[i], illegal);

                }
                long duration = new Date().getTime() - start;
                result.setText(file.listFiles().length + " Files Has Been Checked" + "\n" + tree.wordsNumber() + " Words" + "\n" + (duration) + " Milliseconds" + "\n"+ "Tree Has Been Built Successfully.");
            }
        });
        getContentPane().add(buildButton);
        final JButton resetButton = new JButton("Reset");
        resetButton.setSize(100, 25);
        resetButton.setLocation(180, 485);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        getContentPane().add(resetButton);

        final JButton helpButton = new JButton("Help");
        helpButton.setSize(100, 25);
        helpButton.setLocation(350, 485);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        getContentPane().add(helpButton);

        final JButton exitButton = new JButton("Exit");
        exitButton.setSize(100, 25);
        exitButton.setLocation(520, 485);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        getContentPane().add(exitButton);


        setVisible(true);
    }

public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception ex) {
        System.err.println(ex.getMessage());
    }
    new Menu();
    }
}
