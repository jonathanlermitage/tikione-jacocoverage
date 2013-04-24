package fr.tikione.jacocoverage.plugin;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import org.netbeans.api.project.FileOwnerQuery;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.loaders.DataObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

/**
 * The "Test File with JaCoCoverage" contextual action registration.
 * Start an Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source file and show a coverage report.
 *
 * @author Jonathan Lermitage
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.RunSingleWithJaCoCoAction")
@ActionRegistration(displayName = "#CTL_TestSingleWithJaCoCoAction",
                    lazy = false)
@ActionReferences({
    @ActionReference(path = "Loaders/text/x-java/Actions",
                     position = 1268),
    @ActionReference(path = "Editors/text/x-java/Popup",
                     position = 1785)
})
@Messages("CTL_TestSingleWithJaCoCoAction=Test File with JaCoCoverage")
public final class TestSingleWithJaCoCoAction extends AbstractAction implements ContextAwareAction {

    private static final long serialVersionUID = 1L;

    public @Override
    void actionPerformed(ActionEvent e) {
    }

    public @Override
    Action createContextAwareInstance(Lookup context) {
        return new ContextAction(context);
    }

    /**
     * The "Test File with JaCoCoverage" contextual action.
     */
    private static final class ContextAction extends JaCoCoContextAction {

        private static final long serialVersionUID = 1L;

        /**
         * Enable the context action on supported projects only.
         *
         * @param context the context the action is called from.
         */
        public ContextAction(Lookup context) {
            super(context, FileOwnerQuery.getOwner(context.lookup(DataObject.class).getPrimaryFile()), "test-single");
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
            putValue(NAME, "Test File with JaCoCoverage");
        }

        public @Override
        void actionPerformed(ActionEvent ae) {
            // TODO add needed properties.
//            getAddAntTargetProps().put("classname", "demo.coverage.app.Utility1Test");
//            getAddAntTargetProps().put("javac.includes", "demo.coverage.app.Utility1Test");
//            getAddAntTargetProps().put("test.includes", "demo.coverage.app.Utility1Test");
            super.actionPerformed(ae);
        }
    }
}
