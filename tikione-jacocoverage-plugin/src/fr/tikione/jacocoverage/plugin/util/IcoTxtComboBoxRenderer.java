package fr.tikione.jacocoverage.plugin.util;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

/**
 * A combo-box used to display a list of icons of left-side and text on right-side.
 *
 * @author Jonathan Lermitage
 */
public class IcoTxtComboBoxRenderer extends JLabel implements ListCellRenderer {

    private static final long serialVersionUID = 1L;

    public IcoTxtComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        ImageIcon icon = (ImageIcon) value;
        setText(icon.getDescription());
        setIcon(icon);
        return this;
    }
}