package operations;

import javax.swing.*;

public class PasteOperation implements Operation{
    @Override
    public void doOperation(JTextArea textArea, JFrame frame) {
        textArea.paste();
    }
}
