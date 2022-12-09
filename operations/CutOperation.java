package operations;

import javax.swing.*;

public class CutOperation implements Operation{
    @Override
    public void doOperation(JTextArea textArea, JFrame frame) {
        textArea.cut();
    }
}
