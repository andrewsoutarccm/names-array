
import java.util.HashMap;

import com.andrewsoutar.cmp128.Utilities;
import com.andrewsoutar.cmp128.Utilities.BasicMenuAction;
import com.andrewsoutar.cmp128.Utilities.GenericScanner;
import com.andrewsoutar.cmp128.Utilities.VoidFunction;

public class NamesArray {
    private final GenericScanner kbdScanner;
    private final String company;

    private HashMap <String, Record> names = new HashMap <> ();
    private Record [] namesByRecord = new Record [0];

    public NamesArray (GenericScanner kbdScanner, String company) {
        this.kbdScanner = kbdScanner;
        this.company = company;
    }

    public NamesArray (GenericScanner kbdScanner) {
        this (kbdScanner, kbdScanner.prompt
              (String.class, "Enter company name"));
    }

    public void run () {
        Utilities.mainLoop (kbdScanner, new VoidFunction () {
                public void call () {
                    Utilities.clearScreen ();
                    Utilities.printBordered (new String [] {
                            Utilities.centerString (company, 58),
                        });
                }
            }, new BasicMenuAction [] {
                new BasicMenuAction ("enter new list of names into the array") {
                    public Boolean call () {
                        int count = kbdScanner.prompt
                            (Integer.class,
                             "Number of people in your organization");
                        names = new HashMap <> ();
                        namesByRecord = new Record [count];

                        for (int i = 0; i < count; i++) {
                            Record r = new Record (kbdScanner, i + 1);
                            names.put (r.name, r);
                            namesByRecord [i] = r;
                        }
                    }
                }
            });
    }
}
