package TableModelUI;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

public class TextFieldRenderer implements TableCellRenderer {

    JTextField textField = new JTextField();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.textField.setText(value.toString());
        this.textField.setToolTipText(value.toString());
        return this.textField;
    }
    
    
}
