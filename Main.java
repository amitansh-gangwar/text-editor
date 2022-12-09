import operations.*;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

class Main extends JFrame implements ActionListener, ItemListener {

    JTextArea textArea = new JTextArea("");

    JFrame frame;

    JToolBar tool = new JToolBar();

    Map<String, Operation> operationMap = new HashMap<>();

    private static String[] fontOptions = {"Serif", "Agency FB", "Arial", "Calibri", "Cambrian", "Century Gothic", "Comic Sans MS", "Courier New", "Forte", "Garamond", "Monospaced", "Segoe UI", "Times New Roman", "Trebuchet MS", "Serif"};
    private static String[] sizeOptions = {"16", "18", "20", "22", "24", "26", "28", "30"};

    JComboBox <String> fontName;
    JComboBox <String> fontSize;

    JLabel words, characters;

    JButton findAndReplaceButton = new JButton("Find Or Replace");

    JTextField txtFind = new JTextField();
    JTextField txtReplace = new JTextField();
    JButton btnFind = new JButton("Find");
    JButton btnReplace = new JButton("Replace");
    JButton btnReplaceAll = new JButton("Replace All");

    private Main()
    {

        frame = new JFrame("Text Editor");

        words=new JLabel("Words: ");
        characters=new JLabel("Characters: ");

        // Text component
        textArea = new JTextArea();
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));
        JTextArea finalTextArea = textArea;
        textArea.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {

                String text= finalTextArea.getText();
                characters.setText("Characters: "+text.length());
                String word[]=text.split("\\s");
                words.setText("Words: "+word.length);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        initializeMenuBar(menuBar);
        initializeToolBar(tool);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(tool, BorderLayout.NORTH);
        frame.add(textArea);
        frame.setSize(500, 500);

        JToolBar second = new JToolBar();
        second.add(words);
        second.addSeparator();
        second.add(characters);

        frame.add(second, BorderLayout.SOUTH);
        frame.setVisible(true);

        findAndReplaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                JDialog frDialog = new JDialog(frame);

                frDialog.setLayout(new GridLayout(3,4));

                frDialog.add(new JLabel("Find: "));
                frDialog.add(txtFind);
                frDialog.add(new JLabel(""));
                frDialog.add(btnFind);
                frDialog.add(new JLabel("Replace with: "));
                frDialog.add(txtReplace);
                frDialog.add(new JLabel(""));
                frDialog.add(btnReplace);
                frDialog.add(new JLabel(""));
                frDialog.add(new JLabel(""));
                frDialog.add(new JLabel(""));
                frDialog.add(btnReplaceAll);

                frDialog.pack();
                frDialog.setVisible(true);

            }
        });

        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Highlighter highlighter = textArea.getHighlighter();
                Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
                System.out.println(txtFind.getText());
                System.out.println(textArea.getText());
                if(txtFind.getText().length() <= 0) return;
                int index = 0;
                highlighter.removeAllHighlights();
                while(index != -1){
                    index = textArea.getText().indexOf(txtFind.getText(), index);
                    System.out.println(index);
                    if (index != -1) {
                        int p0 = index;
                        int p1 = p0 + txtFind.getText().length();

                        try {

                            if(p0 >= 0) highlighter.addHighlight(p0, p1, painter);
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                        index++;
                    }
                }

            }
        });

        btnReplaceAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Highlighter highlighter = textArea.getHighlighter();
                Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
                System.out.println(txtFind.getText());
                System.out.println(textArea.getText());
                System.out.println(txtReplace.getText());
                int index = 0;
                highlighter.removeAllHighlights();
                String text = textArea.getText();
                System.out.println(text.replaceAll(txtFind.getText(), txtReplace.getText()));
                textArea.setText(text.replaceAll(txtFind.getText(), txtReplace.getText()));

            }
        });

        btnReplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Highlighter highlighter = textArea.getHighlighter();
                Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
                System.out.println(txtFind.getText());
                System.out.println(textArea.getText());
                System.out.println(txtReplace.getText());
                int index = 0;
                highlighter.removeAllHighlights();
                String text = textArea.getText();
                System.out.println(text.replaceFirst(txtFind.getText(), txtReplace.getText()));
                textArea.setText(text.replaceFirst(txtFind.getText(), txtReplace.getText()));

            }
        });

        operationMap.put("Cut", new CutOperation());
        operationMap.put("Copy", new CopyOperation());
        operationMap.put("Paste", new PasteOperation());
        operationMap.put("Save", new SaveOperation());
        operationMap.put("Load", new LoadOperation());

    }

    private void initializeToolBar(JToolBar tool) {
        JLabel fontLabelText = new JLabel("Font: ");
        JLabel fontSizeLabel = new JLabel("Size: ");
        fontSize = new JComboBox<>(sizeOptions);
        fontSize.addItemListener(this);

        fontName = new JComboBox<>(fontOptions);
        fontName.addItemListener(this);

        tool.add(fontLabelText);
        tool.add(fontName);
        tool.addSeparator();
        tool.add(fontSizeLabel);
        tool.add(fontSize);
        tool.addSeparator();
        tool.add(findAndReplaceButton);
    }

    private void initializeMenuBar(JMenuBar menuBar) {
        // Create amenu for menu
        JMenu firstMenu = new JMenu("File");

        // Create menu items
        JMenuItem loadMenuItem = new JMenuItem("Load");
        JMenuItem saveMenuItem = new JMenuItem("Save");

        // Add action listener
        loadMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);

        firstMenu.add(loadMenuItem);
        firstMenu.add(saveMenuItem);

        // Create amenu for menu
        JMenu secondMenu = new JMenu("Edit");

        // Create menu items
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        // Add action listener
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);

        secondMenu.add(cutMenuItem);
        secondMenu.add(copyMenuItem);
        secondMenu.add(pasteMenuItem);

        menuBar.add(firstMenu);
        menuBar.add(secondMenu);
    }

    public static void main(String args[])
    {
        //Start listeners
        Main main = new Main();
    }

    // If a button on menu is pressed
    public void actionPerformed(ActionEvent actionEvent)
    {
        operationMap.get(actionEvent.getActionCommand()).doOperation(textArea, frame);
    }

    // If a button on tool bar is pressed
    @Override
    public void itemStateChanged(ItemEvent e) {
        String fontType = (String) fontName.getSelectedItem();
        int font = Integer.valueOf((String) fontSize.getSelectedItem());
        textArea.setFont(new Font(fontType, Font.PLAIN, font));
    }
}
