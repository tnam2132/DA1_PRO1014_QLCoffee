package TableModelUI;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class TextFieldEditor extends AbstractCellEditor implements TableCellEditor  {

    JTextField textField = new JTextField();
    
    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.textField.setText(value.toString());
        this.textField.setToolTipText(value.toString());
        return this.textField;
    }
    
}
