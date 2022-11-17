package TableModelUI;

import java.awt.Component;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellRenderer;

public class SpinnerRenderer implements TableCellRenderer {

    public final JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.spinner.setValue(value);
        return this.spinner;
    }
}
