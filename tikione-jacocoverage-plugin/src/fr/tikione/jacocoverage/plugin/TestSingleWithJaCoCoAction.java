package fr.tikione.jacocoverage.plugin;

/**
 * The "Test File with JaCoCoverage" contextual action registration.
 * Start the "test-single" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source file and show a coverage report.
 *
 * @author Jonathan Lermitage
 */
/*
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.TestSingleWithJaCoCoAction")
@ActionRegistration(displayName = "#CTL_TestSingleWithJaCoCoAction",
                    lazy = false)
@ActionReferences({
    @ActionReference(path = "Loaders/text/x-java/Actions",
                     position = 1268),
    @ActionReference(path = "Editors/text/x-java/Popup",
                     position = 1785)
})
@Messages("CTL_TestSingleWithJaCoCoAction=Test File with JaCoCoverage")
* */
public final class TestSingleWithJaCoCoAction /*extends AbstractAction implements ContextAwareAction*/ {

//    private static final long serialVersionUID = 1L;
//
//    public @Override
//    void actionPerformed(ActionEvent e) {
//    }
//
//    public @Override
//    Action createContextAwareInstance(Lookup context) {
//        return new ContextAction(context);
//    }
//
//    /**
//     * The "Test File with JaCoCoverage" contextual action.
//     */
//    private static final class ContextAction extends JaCoCoContextAction implements Presenter.Popup {
//
//        private static final long serialVersionUID = 1L;
//
//        /**
//         * Enable the context action on supported projects only.
//         *
//         * @param context the context the action is called from.
//         */
//        public ContextAction(Lookup context) {
//            super(context, FileOwnerQuery.getOwner(context.lookup(DataObject.class).getPrimaryFile()), "test-single");
//            putValue(NAME, Bundle.CTL_TestSingleWithJaCoCoAction());
//            putValue(SMALL_ICON, ImageUtilities.loadImageIcon(Globals.TEST_ICON, false));
//            setEnabled(false);
//        }
//
//        public @Override
//        void actionPerformed(ActionEvent ae) {
//            // TODO add needed properties.
////            getAddAntTargetProps().put("classname", "demo.coverage.lib.Utility1Test");
////            getAddAntTargetProps().put("javac.includes", "demo.coverage.lib.Utility1Test");
////            getAddAntTargetProps().put("test.includes", "demo.coverage.lib.Utility1Test");
//            super.actionPerformed(ae);
//        }
//
//        @Override
//        public JMenuItem getPopupPresenter() {
//            return new JMenuItem(this);
//        }
//    }
}
