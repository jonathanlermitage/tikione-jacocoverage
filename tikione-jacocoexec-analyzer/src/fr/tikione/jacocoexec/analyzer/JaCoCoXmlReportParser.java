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
 * JaCoCo XML reports related utilities.
 *
 * @author Jonathan Lermitage
 */
public class JaCoCoXmlReportParser extends DefaultHandler {

    /** The coverage data of each Java class. */
    private final Map<String, JavaClass> classes = new LinkedHashMap<String, JavaClass>(32);

    private String currentPackage = null;

    private JavaClass currentJavaClass = null;

    /**
     * Extract coverage data from a JaCoCo XML report file.
     *
     * @param xml the JaCoCo XML report file.
     * @return the coverage data of each Java class registered in the JaCoCo XML report.
     * @throws ParserConfigurationException if an errors occurs during the parsing of the JaCoCo XML report.
     * @throws SAXException if an errors occurs during the parsing of the JaCoCo XML report.
     * @throws IOException if an errors occurs during the parsing of the JaCoCo XML report.
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
            //
        } else if (qName.equalsIgnoreCase("COUNTER")) {
            //
        } else if (qName.equalsIgnoreCase("LINE")) {
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
            boolean noCoverage = missedInstructions > 0 || missedBranches > 0;
            boolean someCoverage = coveredInstructions > 0 || coveredBranches > 0;
            if (!noCoverage) {
                currentJavaClass.addCoveredLine(lineNumber);
            } else if (noCoverage && someCoverage) {
                currentJavaClass.addPartiallyCoveredLine(lineNumber);
            } else {
                currentJavaClass.addNotCoveredLine(lineNumber);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
}
