package TableModelUI;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer implements TableCellRenderer  {
    
    public JButton button;
    
    public ButtonRenderer(int type) {
        if (type == 0) {
            button = new JButton("E");
        } else {
            button = new JButton("X");
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return button;
    }
    
}
