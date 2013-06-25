package fr.tikione.jacocoverage.plugin.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener to fire changes on project side configuration (or any {@link IStorable} object).
 *
 * @author Jonathan Lermitage
 */
public class PrjcfgAntJavaseListener implements ActionListener {

    private final IStorable storable;

    public PrjcfgAntJavaseListener(IStorable storable) {
        super();
        this.storable = storable;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        storable.store();
    }
}
