package fr.tikione.jacocoverage.plugin.config;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;

/**
 * Table model for the packages filter UI.
 *
 * @author Jonathan Lermitage
 */
public class PackageFilterModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    /** Table columns type. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private final Class<Object>[] types = new Class[]{Boolean.class, Icon.class, String.class};

    /** Indicates if table columns are editable. */
    private final boolean[] canEdit = new boolean[]{true, false, false};

    @Override
    public Class<Object> getColumnClass(int columnIndex) {
        return types[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

    public PackageFilterModel() {
        super(new Object[][]{}, new String[]{"Cover", "", "Packages"});
    }

    public static Map<String, Boolean> packageFilterTableToMap(PackageFilterModel model) {
        Map<String, Boolean> map = new LinkedHashMap<>(8);
        int nbLines = model.getRowCount();
        for (int line = 0; line < nbLines; line++) {
            map.put((String) model.getValueAt(line, 2), (Boolean) model.getValueAt(line, 0));
        }
        return map;
    }
}