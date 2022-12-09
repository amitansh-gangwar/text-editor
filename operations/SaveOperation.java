package operations;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SaveOperation implements Operation{
    @Override
    public void doOperation(JTextArea textArea, JFrame frame) {
        JFileChooser j = new JFileChooser("f:");

        // Invoke the showsSaveDialog function to show the save dialog
        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {

            // Set the label to the path of the selected directory
            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                // Create a file writer
                FileWriter wr = new FileWriter(fi, false);

                // Create buffered writer to write
                BufferedWriter w = new BufferedWriter(wr);

                // Write
                w.write(textArea.getText());

                w.flush();
                w.close();
            }
            catch (Exception evt) {
//                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        }
    }
}
