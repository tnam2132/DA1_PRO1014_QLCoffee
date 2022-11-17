package TableModelUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor {

    public JButton button;

    public ButtonEditor(int type) {
        if (type == 0) {
            button = new JButton("E");
        } else {
            button = new JButton("X");
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Object obj = SwingUtilities.getAncestorOfClass(JTable.class, button);
//                if(obj instanceof JTable) {
//                    JTable table = (JTable) obj;
//                    int row = table.convertRowIndexToModel(table.getEditingRow());
//                    int column = table.convertRowIndexToModel(table.getEditingColumn());
//                    ((DefaultTableModel) table.getModel()).setValueAt(true, row, column);
                    fireEditingStopped();
//                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

}
