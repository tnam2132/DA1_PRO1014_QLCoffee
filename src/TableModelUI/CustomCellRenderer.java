package TableModelUI;

import Helper.MoneyHelper;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer extends DefaultTableCellRenderer {

    int type;

    public CustomCellRenderer(int type) {
        this.type = type;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        if (this.type == 0) {
            String text = MoneyHelper.moneyToString(0);
            if (!table.getValueAt(row, 9).toString().equals(text) || !table.getValueAt(row, 10).toString().equals(text)) {
                component.setForeground(Color.red);
            }
            if (table.getValueAt(row, 3) == null) {
                component.setForeground(Color.orange);
            }
        }
        if (this.type == 1) {
            if (table.getValueAt(row, 6) != null) {
                if (isSelected) {
                    component.setForeground(Color.white);
                } else {
                    component.setForeground(Color.blue);
                }
            }
            if (table.getValueAt(row, 7) != null) {
                if (table.getValueAt(row, 7).toString().equals("Huỷ")) {
                    component.setForeground(Color.red);
                }
            }
        }
        return component;
    }
}
