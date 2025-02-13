/*
 * Copyright statement at the bottom of the code.
 */

package sde.virginia.edu.hw3;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
//import org.apache.commons.cli.*;
//import picocli.*;
//import picocli.CommandLine;
//import picocli.CommandLine.Command;
//import picocli.CommandLine.Option;


/**
 * This class handles interpreting the command-line arguments in {@link Main#main(String[])}.
 * Currently, the following arguments format is supported:<br>
 * <code>&lt;filename&gt; [&lt;representatives>&gt;] [--adams] [--population] [--descending]</code>
 *
 * <ul>
 *     <li><code>&lt;filename&gt;</code> is a required text argument to specify the input file. Currently, .csv, .xlsx, and .xls are
 *     supported. See {@link Arguments#getStateSupplier()}</li>
 *     <li><code>[&lt;representatives&gt;]</code> is an <b>optional</b> numeric argument to specify the number of
 *     representatives to allocate. This number must be a positive integer.
 *     See {@link Arguments#getRepresentatives()}</li>
 *     <li>[--adams] is an <b>optional</b> flag argument to specify that you want to use the {@link AdamsMethod Adams
 *     apportionment Method}. By default, the {@link JeffersonMethod Jefferson apportionment method} is used.</li>
 *     <li>[--population] is an <b>optional</b> flag argument to specify that you want the output displayed sorted by
 *     population (by default in ascending order).
 *     <li>[--descending] is an <b>optional</b> flag argument that, when present with --population, specifies that you
 *     want the states printed in descending order. This argument is ignored if --population is missing, and it does not
 *     affect printing in alphabetical order.
 * </ul>
 *
 * Examples:
 * <ul>
 *     <li><code>java -jar Apportionment.jar filename.csv</code> : Apportion with Jefferson method using 435
 *     representatives, displaying in alphabetical order, using data from filename.csv</li>
 *     <li><code>java -jar Apportionment.jar filename.xlsx 1000</code> : Apportion with Jefferson method using 1000
 *       representatives, displaying in alphabetical order, using data from filename.xlsx</li>
 *     <li><code>java -jar Apportionment.jar filename.xlsx --adams --population</code> : Apportion with Adams method
 *     using 1000 representatives, displaying in ascending population order, using data from filename.xlsx</li>
 *     <li><code>java -jar Apportionment.jar filename.csv 100 --population --descending</code> : Apportion with Jefferson
 *     method using 100 representatives, displaying in descending population order, using data from filename.xlsx</li>
 * </ul>
 *
 * @author Will-McBurney
 */

public class Arguments {
    /**
     * The number of representatives in the US House of Representatives since 1913
     */
    public static final int DEFAULT_REPRESENTATIVE_COUNT = 435;
    private final List<String> arguments;

    /**
     * Constructor for {@link Arguments}
     *
     * @param args the command-line arguments passed into {@link Main#main(String[])}
     */
    public Arguments(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("""
                    This program requires command line arguments:
                       java -jar Apportionment.jar <filename.csv> [number of representatives to allocate]""");
        }
        arguments = Arrays.asList(args);
    }
    public ArrayList<String> singleToken() {
        ArrayList<String> combinedArguments = new ArrayList<>();
        for (int i = 0; i < arguments.size(); i++) {
            String argument = arguments.get(i);
            if (argument.startsWith("-")) {
                String flags = argument.substring(1);
                for (int j = 0; j < flags.length(); j++) {
                    combinedArguments.add(String.valueOf(flags.charAt(j)));
                }
            }
            else {
                combinedArguments.add(argument);
            }
        }
        return combinedArguments;
    }

    /**
     * Gets a {@link StateSupplier} from the command-line arguments. {@link Arguments} currently supports
     * the following file formats:
     * <ul>
     *     <li>.csv - comma separated values (see {@link CSVStateReader}</li>
     *     <li>.xlsx and .xls - Excel, Google Sheets, and OpenOffice Calc (see {@link SpreadsheetStateReader}</li>
     * </ul>
     *
     * @return a StateSupplier associated with that file.
     * @throws UnsupportedFileFormatException if an invalid filename argument is provided
     */
    public StateSupplier getStateSupplier() {
        var filename = arguments.get(0);
        StateSupplierFactory factory = new StateSupplierFactory();
        StateSupplier supplier = factory.getStateSupplier(filename);
        return supplier;
    }

    /**
     * Determine the number of representatives to allocate form the command-line arguments.
     *
     * @return the number of representatives to allocate, based on the command-line arguments. If
     * <code>[representatives]</code> is not specified, then {@link Arguments#DEFAULT_REPRESENTATIVE_COUNT return the
     * default number of representatives}.
     * @see Main#main(String[])
     */
    public int getRepresentatives() {
        ArrayList<String> combinedArguments = new ArrayList<>(singleToken());
        if (arguments.contains("--representatives") || arguments.contains("-r")) {
            int index = 0;
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i).equals("--representatives") || arguments.get(i).equals("-r")) {
                    index = i;
                    break;
                }
            }
            try {
                var targetRepresentatives = Integer.parseInt(arguments.get(index + 1));
                if (targetRepresentatives <= 0) {
                    throw new IllegalArgumentException("Number of representatives argument must be a positive integer.");
                }
                return targetRepresentatives;
            }
            catch (NumberFormatException e) {
                return DEFAULT_REPRESENTATIVE_COUNT;
            }
        }
        else if (combinedArguments.contains("r")) {
            for (int i = 0; i < combinedArguments.size(); i++) {
                if (combinedArguments.get(i).equalsIgnoreCase("r")) {
                    for (int j = i + 1; j < combinedArguments.size(); j++) {
                        String target = combinedArguments.get(j);
                        if (target.matches("\\d+")) {
                            int parsedValue = Integer.parseInt(target);
                            if (parsedValue > 0) {
                                var targetRepresentatives = parsedValue;
                                return targetRepresentatives;
                            }
                            else {
                                throw new IllegalArgumentException("Number of representatives argument must be a positive integer.");
                            }
                        }
                    }
                }
            }
        }
        return DEFAULT_REPRESENTATIVE_COUNT;
    }

    /**
     * Determine the {@link ApportionmentMethod apportionment method} to use from the command-line arguments.
     * Specifically, if the arguments contain <code>--adams</code>, then the {@link AdamsMethod Adams apportionment
     * algorithm} is used. Otherwise, the {@link JeffersonMethod Jefferson apportionment method is used.}
     *
     * @return the {@link ApportionmentMethod apportionment method} to use when allocating representatives.
     * @see Main#main(String[])
     */
    public ApportionmentMethod getApportionmentMethod() {
        ApportionmentMethodFactory factory = new ApportionmentMethodFactory();
        ArrayList<String> combinedArguments = new ArrayList<>(singleToken());
        if (arguments.contains("--method") || arguments.contains("-m")) {
            int index = 0;
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i).equals("--method") || arguments.get(i).equals("-m")) {
                    index = i;
                    break;
                }
            }
            if (index + 1 < arguments.size()) {
                var method = arguments.get(index + 1);
                ApportionmentMethod apportionment = factory.getDefaultMethod(method);
                return apportionment;
            }
        }
        else if (combinedArguments.contains("m")) {
            for (int i = 0; i < combinedArguments.size(); i++) {
                if (combinedArguments.get(i).equals("m")) {
                    for (int j = i + 1; j < combinedArguments.size(); j++) {
                        String method = combinedArguments.get(j);
                        if (method.equalsIgnoreCase("jefferson") || method.equalsIgnoreCase("adams") || method.equalsIgnoreCase("huntington")) {
                            ApportionmentMethod apportionment = factory.getDefaultMethod(method);
                            return apportionment;
                        }
                    }
                }
            }
        }
        return factory.getDefaultMethod();
    }

    /**
     * Determine the {@link RepresentationFormat representation format} to use from the command-line arguments.
     * Specifically, if the arguments contains the following:
     * <ul>
     *     <li><code>--population</code> : display sorted by population in ascending order </li>
     *     <li><code>--population AND --descending</code> : display sorted by population in ascending order</li>
     *     <li>Otherwise, display sorted by state name in alphabetical order. (The --descending tag does not affect
     *     alphabetical order)</li>
     * </ul>
     *
     * @return a {@link RepresentationFormat} used in to print the Apportionment based on the command-line arguments
     * used.
     * @see Main#main(String[])
     */
    public RepresentationFormat getRepresentationFormat() {
        var ascending = "--ascending";
        var descending = "--descending";
        RepresentationFormatFactory factory = new RepresentationFormatFactory();
        ArrayList<String> combinedArguments = new ArrayList<>(singleToken());
        if (arguments.contains("--format") || arguments.contains("-f")) {
            int index = 0;
            for (int i = 0; i < arguments.size(); i++) {
                if (arguments.get(i).equals("--format") || arguments.get(i).equals("-f")) {
                    index = i;
                    break;
                }
            }
            var name = arguments.get(index + 1);
            if (arguments.contains(ascending) || arguments.contains("-a")) {
                RepresentationFormat representation = factory.getFormat(name, DisplayOrder.ASCENDING);
                return representation;
            }
            else if (arguments.contains(descending) || arguments.contains("-d")) {
                RepresentationFormat representation = factory.getFormat(name, DisplayOrder.DESCENDING);
                return representation;
            }
            else {
                RepresentationFormat representation = factory.getFormat(name);
                return representation;
            }
        }
        else if (combinedArguments.contains("f")) {
            for (int i = 0; i < combinedArguments.size(); i++) {
                if (combinedArguments.get(i).equals("f")) {
                    for (int j = i + 1; j < combinedArguments.size(); j++) {
                        String name = combinedArguments.get(j);
                        if (name.equalsIgnoreCase("benefit") ||  name.equalsIgnoreCase("population")) {
                            if (combinedArguments.contains("a") || combinedArguments.contains(ascending) || combinedArguments.contains("-a")) {
                                RepresentationFormat representation = factory.getFormat(name, DisplayOrder.ASCENDING);
                                return representation;
                            }
                            else if (combinedArguments.contains("d") || combinedArguments.contains(descending) || combinedArguments.contains("-d")) {
                                RepresentationFormat representation = factory.getFormat(name, DisplayOrder.DESCENDING);
                                return representation;
                            }
                            else {
                                RepresentationFormat representation = factory.getFormat(name);
                                return representation;
                            }
                        }
                    }
                }
            }
        }
        return factory.getDefaultFormat();
    }
}


/*
 * Copyright (c) 2023. Paul "Will" McBurney <br>
 *
 * This software was written as part of an education experience by Prof. Paul "Will" McBurney at the University of Virginia, for the course CS 3140, Software Development Essentials. This source code, or any derivative source code (such as the student's own work building off this source code) is subject to the CS 3140 collaboration policy which can be found here: <a href="https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy">https://cs-3140-fa23.github.io/syllabus.html#homework-collaboration-policy</a>
 *
 * This source code and any derivative work may not be shared publicly through any means. This includes a prohibition on posting this work or derivative work on a public GitHub repository, course help website, file sharing platform, email, job application, etc. Sharing this code or derivative works with other students may be subject to referral to UVA Student Honor, as well as additional penalties.
 *
 * THE SOFTWARE IS PROVIDED &ldquo;AS IS&rdquo;, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */