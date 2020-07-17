package net.ogalab.util.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ogalab.util.fundamental.CalendarUtil;
import net.ogalab.util.fundamental.Type;
import net.ogalab.util.os.FileIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
//import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
//import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.DefaultParser;

/**
 * Provides access to configuration parameters.
 *
 * <pre>
 *
 * public static void main(String[] args) {
 * Parameter obj = new Parameter();
 * obj.setParameters(args, checkObject(obj));
 *
 * }
 *
 *
 * private void setParameters(String[] args, String progName) {
 *
 * //param  = new Parameter();
 *
 * setDefault("Generation", 10000);
 * setDefault("Interval", 1000);
 *
 * // Specific parameters
 * setDefault("MinimumRnaLevel",   1.0);
 * setDefault("TotalRna",          300000);
 * setDefault("NumberOfGenes",     20000);
 * setDefault("StandardDeviation", 0.05);
 *
 * // Set parameters on the object.
 * setDefault("ProgramName", progName);
 * parseCommandLine(args, progName);
 * printValidParameters();
 *
 * }
 * </pre>
 *
 *
 * @author oogasawa
 *
 */
public class Parameter {

    TreeMap<String, String> entries = new TreeMap<String, String>();
    TreeSet<String> validKeys = new TreeSet<String>();

    public static boolean HIDE = false;
    public static boolean SHOW = true;

    String defaultConfInJar = null;

    // These patterns are used in reading configuration file.
    Pattern keyValuePairPattern_ = Pattern.compile("^\\s*?(.+?)\\s*(\\t|=)\\s*(.*)$");

    /*
     public static String checkObject(Object obj) {
     String callerClassName = sun.reflect.Reflection.getCallerClass(2).getName();
     String objClassName    = obj.getClass().getName();
		
     if ( !objClassName.equals(callerClassName) ) {
     System.err.println("Error: main(): obj class is not caller class.: " 
     + objClassName
     + ", "
     + callerClassName);
     System.exit(1);
     }
     return objClassName;
     }*/
    /*
     private void setParameters(String[] args, String progName) {
		
     //param  = new Parameter();
				
     setDefault("Generation", 10000);
     setDefault("Interval", 1000);

     // Specific parameters
     setDefault("MinimumRnaLevel",   1.0); 	<dependency>
     <groupId>mysql</groupId>
     <artifactId>mysql-connector-java</artifactId>
     <version>5.1.21</version>
     </dependency>
     <dependency>
     <groupId>commons-configuration</groupId>
     <artifactId>commons-configuration</artifactId>
     <version>1.8</version>
     </dependency>
     setDefault("TotalRna",          300000);
     setDefault("NumberOfGenes",     20000);
     setDefault("StandardDeviation", 0.05);
		
     // Set parameters on the object.
     setDefault("ProgramName", progName);
     parseCommandLine(args, progName);	
     printValidParameters(HIDE);

     }

     */
    public String[] parseCommandLine(String[] args, String progName) {
        set("ProgramName", progName);

        Options options = createOptions();

        // create a parser object
//        CommandLineParser parser = new PosixParser();
        CommandLineParser parser = new DefaultParser();
        CommandLine parseResult = null;

        // parse command line argument.
        try {
            parseResult = parser.parse(options, args);
            execCommandLine(parseResult, options);
        } catch (org.apache.commons.cli.ParseException exp) {
            // when something has gone wrong ...
            System.err.println("Error at command line parser: " + exp.getMessage());
            // print the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(progName, options);
        } catch (IOException exp) {
            // when something has gone wrong ...
            System.err.println("Error at command line parser: " + exp.getMessage());
            // print the help statement
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(progName, options);
        }

        if (parseResult != null) {
            return parseResult.getArgs();
        } else {
            return null;
        }
    }

    public Options createOptions() {

        // create Option objects.
        Option help = new Option("help", "print this message");

//        OptionBuilder.hasArgs(2);
//        OptionBuilder.withArgName("property=value");
//        OptionBuilder.withValueSeparator(); // default separator is '='.
//        OptionBuilder.withDescription("use value for given property");
//        Option property = OptionBuilder.create("P");
        
        Option property = Option.builder("P")
        		.numberOfArgs(2)
        		.argName("property=value")
        		.valueSeparator()
        		.desc("use value for given property")
        		.build();

//        OptionBuilder.hasArgs(2);
//        OptionBuilder.withArgName("property=value");
//        OptionBuilder.withValueSeparator(); // default separator is '='.
//        OptionBuilder.withDescription("use value for given property (same as the parameter -P)");
//        Option property2 = OptionBuilder.create("D");

        Option property2 = Option.builder("D")
        		.numberOfArgs(2)
        		.argName("property=value")
        		.valueSeparator()
        		.desc("use value for given property (same as the parameter -P)")
        		.build();
        		
//        OptionBuilder.hasArg(true);
//        OptionBuilder.withArgName("file");
//        OptionBuilder.withDescription("use given configuration file.");
//        OptionBuilder.withLongOpt("conf");
//        Option conf = OptionBuilder.create();
        
        Option conf = Option.builder()
        		.hasArg(true)
        		.argName("file")
        		.desc("use given configuration file.")
        		.longOpt("conf")
        		.build();

//        OptionBuilder.hasArg(false);
//        OptionBuilder.withDescription("print a list of parameters and their values");
//        OptionBuilder.withLongOpt("list");
//        Option list = OptionBuilder.create();
        
        Option list = Option.builder()
        		.hasArg(false)
        		.desc("print a list of parameters and their values")
        		.longOpt("list")
        		.build();

        // create an Options object.				
        Options options = new Options();

        // set Option objects to the Options object.
        options.addOption(conf);
        options.addOption(help);
        options.addOption(property);  // -P
        options.addOption(property2); // -D (the same as -P)
        options.addOption(list);

        return options;
    }

    public void execCommandLine(CommandLine parseResult, Options options) throws IOException {

        String progName = getString("ProgramName");

        execHelpOption(parseResult, options, progName);

        readConfFile(parseResult, options);
        readParametersInCL(parseResult, options);

        execListOption(parseResult, options);
    }

    public void execHelpOption(CommandLine parseResult, Options options, String progName) {
        if (parseResult.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(progName, options, true);
            System.exit(0);
        }
    }

    public void execListOption(CommandLine parseResult, Options options) {

        if (parseResult.hasOption("list")) {

            String[] keyStr = getKeys();
            for (int i = 0; i < keyStr.length; i++) {
                print(keyStr[i], false);
            }
            System.exit(0);

        }

    }

    /*
     * param.setDefaultConfInJar()をあらかじめ呼び出しておくこと。
     * 
     */
    public void readDefaultConfInJar(Object obj) throws IOException {
        if (defaultConfInJar == null) {
            return;
        }

        URL url = obj.getClass().getResource(defaultConfInJar);
        if (url == null) // defaultConfInJar is not found.
        {
            return;
        }

        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(url.openStream()));
        readConfig(br);
        br.close();
    }

    public void readConfFile(CommandLine parseResult, Options options) throws IOException {
        if (parseResult.hasOption("conf")) {
            String configFile = parseResult.getOptionValue("conf");
            readConfig(configFile);
        }
    }

    public void readParametersInCL(CommandLine parseResult, Options options) {
        if (parseResult.hasOption("P")) {
            Properties props = parseResult.getOptionProperties("P");
            Enumeration<?> keys = props.propertyNames();
            while (keys.hasMoreElements()) {
                String k = keys.nextElement().toString();
                String v = props.getProperty(k);
                set(k, v);
            }

        }

        if (parseResult.hasOption("D")) {
            Properties props = parseResult.getOptionProperties("D");
            Enumeration<?> keys = props.propertyNames();
            while (keys.hasMoreElements()) {
                String k = keys.nextElement().toString();
                String v = props.getProperty(k);
                set(k, v);
            }
        }
    }

    //------------------------------------------------------------------
    public void printValidParameters(boolean comment_out) {
        String[] keyStr = getValidKeys();
        for (int i = 0; i < keyStr.length; i++) {
            print(keyStr[i], comment_out);
        }
        System.out.println("# " + CalendarUtil.currentTime());
    }

    public void printValidParameters() {
        printValidParameters(true);
    }

    public void printValidParametersToStderr(boolean comment_out) {
        String[] keyStr = getValidKeys();
        for (int i = 0; i < keyStr.length; i++) {
            printToStderr(keyStr[i], comment_out);
        }
        System.err.println("# " + CalendarUtil.currentTime());
    }

    public void printValidParametersToStderr() {
        printValidParametersToStderr(true);
    }

	//---------------------------------------------------------------
    public void set(String paramName, int value) {
        set(paramName, Type.toString(value));
    }

    public void set(String paramName, double value) {
        set(paramName, Type.toString(value));
    }

    public void set(String paramName, String value) {
        entries.put(paramName, value);
    }

//	--------------------------------------------------------	
    public String setDefault(String key, String value, boolean show) {
        
        String result = value;
        if (entries.containsKey(key)) 
            result = entries.get(key);
        else
            set(key, value);
            

        if (show == true) {
            setValidKey(key);
        }
        return result;
    }

    public String setDefault(String key, String value) {
        return setDefault(key, value, true);
    }

    public int setDefault(String key, int value) {
        return Type.to_int(setDefault(key, Type.toString(value)));
    }

    public int setDefault(String key, int value, boolean show) {
        return Type.to_int(setDefault(key, Type.toString(value), show));
    }

    public long setDefault(String key, long value) {
        return Type.to_long(setDefault(key, Type.toString(value)));

    }

    public long setDefault(String key, long value, boolean show) {
        return Type.to_long(setDefault(key, Type.toString(value), show));
    }

    public double setDefault(String key, double value) {
        return Type.to_double(setDefault(key, Type.toString(value)));
    }

    public double setDefault(String key, double value, boolean show) {
        return Type.to_double(setDefault(key, Type.toString(value), show));

    }

    public void setDefault(Parameter p) {
        Set<String> keys = p.entries.keySet();
        for (String k : keys) {
            this.setDefault(k, p.getString(k));
        }
    }

	//------------------------------------------------------
    public boolean has(String key) {
        return entries.containsKey(key);
    }

//--------------------------------------------------
    public String getString(String key) {
        if (this.validKeys.contains(key)) {
            return entries.get(key);
        } else {
            throw new RuntimeException("Parmeter : Undefined parameter key.");
        }
    }

    public int getInt(String key) {
        String ret = getString(key);
        return Type.to_int(ret);
    }

    public long getLong(String key) {
        String ret = getString(key);
        return Type.to_long(ret);
    }

    public double getDouble(String key) {
        String ret = getString(key);
        return Type.to_double(ret);
    }

        //--------------------------------------------------
    public String getString(String key, String defValue) {
        if (this.validKeys.contains(key)) {
            return entries.get(key);
        } else {
            setDefault(key, defValue);
            return entries.get(key);
        }

    }

    public int getInt(String key, int val) {
        String ret = getString(key, Type.toString(val));
        return Type.to_int(ret);
    }

    public long getLong(String key, long val) {
        String ret = getString(key, Type.toString(val));
        return Type.to_long(ret);
    }

    public double getDouble(String key, double val) {
        String ret = getString(key, Type.toString(val));
        return Type.to_double(ret);
    }

//------------------------------------------------------------------------	
    public void setValidKey(String key) {
        validKeys.add(key);
    }

    public String[] getValidKeys() {
        String[] ret = new String[validKeys.size()];
        validKeys.toArray(ret);
        return ret;
    }

    public String[] getKeys() {
        Set<String> tmp = entries.keySet();
        String[] ret = new String[tmp.size()];
        tmp.toArray(ret);

        return ret;
    }

    public int getNumOfKeys() {
        return entries.keySet().size();
    }

//--------------------------------------------------------------------------	
    public void print(String key, boolean comment_out) {

        if (comment_out) {
            System.out.print("# ");
        }
        System.out.println(key + " = " + entries.get(key));

    }

    public void printToStderr(String key, boolean comment_out) {

        if (comment_out) {
            System.err.print("# ");
        }
        System.err.println(key + " = " + entries.get(key));

    }

    public void overwrite(Parameter pm) {
        String[] keys = pm.getKeys();
        for (int i = 0; i < keys.length; i++) {
            set(keys[i], pm.entries.get(keys[i]));
        }
    }

    public void readConfig(BufferedReader br) throws IOException {

        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("#") || line.trim().equals("")) {
                continue;
            } else {
                readLine(line);
            }
        }

    }

    public void readConfig(String fname) throws IOException {
        BufferedReader br = FileIO.getBufferedReader(fname);
        readConfig(br);
        br.close();
    }

    public void readLine(String line) {

        Matcher m = keyValuePairPattern_.matcher("");

        String key = null;
        String vStr = null;

        m.reset(line);
        if (m.matches()) {
            key = m.group(1);
            vStr = m.group(3);
            set(key, vStr);
        } else {
            System.err.println("Invalid parameter string: " + line);
        }
    }

    public String getDefaultConfInJar() {
        return defaultConfInJar;
    }

    public void setDefaultConfInJar(String defaultConfInJar) {
        this.defaultConfInJar = defaultConfInJar;
    }

    // jenkins自動ビルドテスト
}
