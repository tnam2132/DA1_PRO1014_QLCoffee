package TableModelUI;

import java.awt.Component;
import java.awt.Toolkit;
import java.text.ParseException;
import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {

    public final JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));

    public SpinnerEditor() {
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
//                Object obj = SwingUtilities.getAncestorOfClass(JTable.class, spinner);
//                if(obj instanceof JTable) {
//                    JTable table = (JTable) obj;
//                    int row = table.convertRowIndexToModel(table.getEditingRow());
//                    int column = table.convertRowIndexToModel(table.getEditingColumn());
//                    ((DefaultTableModel) table.getModel()).setValueAt(spinner.getValue(), row, column);
                    fireEditingStopped();
//                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.spinner.setValue(value);
        return this.spinner;
    }

    @Override
    public Object getCellEditorValue() {
        return this.spinner.getValue();
    }
    
}
