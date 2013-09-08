package fr.tikione.jacocoexec.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * JaCoCo XML reports parser and related utilities.
 *
 * @author Jonathan Lermitage
 */
public class JaCoCoXmlReportParser extends DefaultHandler {

    /** The coverage data of each Java class. */
    private final Map<String, JavaClass> classes = new LinkedHashMap<String, JavaClass>(32);

    /** Used to remember current Java package while XML parsing. */
    private String currentPackage = null;

    /** Used to remember current Java method while XML parsing. */
    private JavaMethod currentJavaMethod = null;

    /** Used to remember if we are in a Java method description while XML parsing. */
    private boolean inMethod = false;

    /** Used to remember current Java class while XML parsing. */
    private JavaClass currentJavaClass = null;

    /**
     * Extract coverage data from a JaCoCo XML report file.
     *
     * @param xml the JaCoCo XML report file.
     * @return the coverage data of each Java class registered in the JaCoCo XML report.
     * @throws ParserConfigurationException if an error occurs during the parsing of the JaCoCo XML report.
     * @throws SAXException if an error occurs during the parsing of the JaCoCo XML report.
     * @throws IOException if an error occurs during the parsing of the JaCoCo XML report.
     */
    public static Map<String, JavaClass> getCoverageData(File xml)
            throws ParserConfigurationException,
                   SAXException,
                   IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        JaCoCoXmlReportParser handler = new JaCoCoXmlReportParser();
        saxParser.parse(xml, handler);
        return handler.getClasses();
    }

    /**
     * Get the coverage data of each Java class.
     *
     * @return coverage data.
     */
    public Map<String, JavaClass> getClasses() {
        return classes;
    }

    @Override
    public void startDocument()
            throws SAXException {
    }

    @Override
    public void endDocument()
            throws SAXException {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("PACKAGE")) {
            for (int idx = 0; idx < attributes.getLength(); idx++) {
                if (attributes.getQName(idx).equalsIgnoreCase("NAME")) {
                    currentPackage = '/' + attributes.getValue(idx) + '/';
                    break;
                }
            }
        } else if (qName.equalsIgnoreCase("SOURCEFILE")) {
            for (int idx = 0; idx < attributes.getLength(); idx++) {
                if (attributes.getQName(idx).equalsIgnoreCase("NAME")) {
                    String classname = attributes.getValue(idx);
                    if (classes.containsKey(currentPackage + classname)) {
                        currentJavaClass = classes.get(currentPackage + classname);
                    } else {
                        currentJavaClass = new JavaClass(currentPackage, classname);
                        classes.put(currentPackage + classname, currentJavaClass);
                    }
                    break;
                }
            }
        } else if (qName.equalsIgnoreCase("CLASS")) {
            for (int idx = 0; idx < attributes.getLength(); idx++) {
                if (attributes.getQName(idx).equalsIgnoreCase("NAME")) {
                    String classname = attributes.getValue(idx);
                    classname = classname.substring(classname.lastIndexOf('/') + 1);
                    if (classname.contains("$")) {
                        classname = classname.substring(0, classname.indexOf('$'));
                    }
                    classname += ".java";
                    if (classes.containsKey(currentPackage + classname)) {
                        currentJavaClass = classes.get(currentPackage + classname);
                    } else {
                        currentJavaClass = new JavaClass(currentPackage, classname);
                        classes.put(currentPackage + classname, currentJavaClass);
                    }
                    break;
                }
            }
        } else if (qName.equalsIgnoreCase("METHOD")) {
            inMethod = true;
            currentJavaMethod = new JavaMethod();
            for (int idx = 0; idx < attributes.getLength(); idx++) {
                if (attributes.getQName(idx).equalsIgnoreCase("LINE")) {
                    currentJavaMethod.setLineNumber(Integer.parseInt(attributes.getValue(idx)) - 1);
                } else if (attributes.getQName(idx).equalsIgnoreCase("NAME")) {
                    currentJavaMethod.setName(attributes.getValue(idx));
                }
            }
        } else if (qName.equalsIgnoreCase("COUNTER") && inMethod) {
            String type = null;
            int missed = 0;
            int covered = 0;
            for (int idx = 0; idx < attributes.getLength(); idx++) {
                if (attributes.getQName(idx).equalsIgnoreCase("TYPE")) {
                    type = attributes.getValue(idx);
                } else if (attributes.getQName(idx).equalsIgnoreCase("MISSED")) {
                    missed = Integer.parseInt(attributes.getValue(idx));
                } else if (attributes.getQName(idx).equalsIgnoreCase("COVERED")) {
                    covered = Integer.parseInt(attributes.getValue(idx));
                }
            }
            if (type != null) {
                if (type.equalsIgnoreCase("INSTRUCTION")) {
                    currentJavaMethod.setInstructionsCovered(covered);
                    currentJavaMethod.setInstructionsMissed(missed);
                } else if (type.equalsIgnoreCase("LINE")) {
                    currentJavaMethod.setLinesCovered(covered);
                    currentJavaMethod.setLinesMissed(missed);
                }
            }
        } else if (qName.equalsIgnoreCase("LINE")) {
            // Get line's coverage data.
            int lineNumber = 0;
            int missedInstructions = 0;
            int coveredInstructions = 0;
            int missedBranches = 0;
            int coveredBranches = 0;
            for (int idx = 0; idx < attributes.getLength(); idx++) {
                if (attributes.getQName(idx).equalsIgnoreCase("NR")) {
                    lineNumber = Integer.parseInt(attributes.getValue(idx)) - 1; // NetBeans Editor starting index is 0, not 1.
                } else if (attributes.getQName(idx).equalsIgnoreCase("MI")) {
                    missedInstructions = Integer.parseInt(attributes.getValue(idx));
                } else if (attributes.getQName(idx).equalsIgnoreCase("CI")) {
                    coveredInstructions = Integer.parseInt(attributes.getValue(idx));
                } else if (attributes.getQName(idx).equalsIgnoreCase("MB")) {
                    missedBranches = Integer.parseInt(attributes.getValue(idx));
                } else if (attributes.getQName(idx).equalsIgnoreCase("CB")) {
                    coveredBranches = Integer.parseInt(attributes.getValue(idx));
                }
            }
            boolean someMissed = missedInstructions > 0 || missedBranches > 0;
            boolean someCovered = coveredInstructions > 0 || coveredBranches > 0;
            // Set coverage state. Will indicate the color of code highlighting.
            if (someCovered) {
                if (someMissed) {
                    currentJavaClass.addPartiallyCoveredLine(lineNumber);
                } else {
                    currentJavaClass.addCoveredLine(lineNumber);
                }
            } else {
                currentJavaClass.addNotCoveredLine(lineNumber);
            }
            // Set coverage description when possible (currently: branches coverage). Will enable glyphed annotations.
            if (missedBranches > 0) {
                if (coveredBranches > 0) {
                    currentJavaClass.getCoverageDesc().put(lineNumber, missedBranches + " of " + (missedBranches + coveredBranches)
                            + " branches missed.");
                } else {
                    currentJavaClass.getCoverageDesc().put(lineNumber, "All " + missedBranches + " branches missed.");
                }
            } else if (coveredBranches > 0) {
                currentJavaClass.getCoverageDesc().put(lineNumber, "All " + coveredBranches + " branches covered.");
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("COUNTER")) {
            if (!currentJavaMethod.getName().equals("<init>")) {
                currentJavaMethod.setCoverageDesc(""); // TODO anno desc if needed
                int totalMissed = currentJavaMethod.getInstructionsMissed() + currentJavaMethod.getLinesMissed();
                int totalCovered = currentJavaMethod.getInstructionsCovered() + currentJavaMethod.getLinesCovered();
                if (totalMissed > 0) {
                    if (totalCovered > 0) {
                        currentJavaMethod.setCoverageState(CoverageStateEnum.PARTIALLY_COVERED);
                    } else {
                        currentJavaMethod.setCoverageState(CoverageStateEnum.NOT_COVERED);
                    }
                } else {
                    currentJavaMethod.setCoverageState(CoverageStateEnum.COVERED);
                }
                currentJavaClass.addMethodCoverage(currentJavaMethod.getLineNumber(), currentJavaMethod.getCoverageState());
            }
        } else if (qName.equalsIgnoreCase("METHOD")) {
            inMethod = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
}
