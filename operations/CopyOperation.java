package operations;

import javax.swing.*;

public class CopyOperation implements Operation{
    @Override
    public void doOperation(JTextArea textArea, JFrame frame) {
        textArea.copy();
    }
}
