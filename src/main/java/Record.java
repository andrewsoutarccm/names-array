
import static java.lang.String.format;

import com.andrewsoutar.cmp128.Utilities.GenericScanner;

public class Record {
    public final int number;
    public final String name;

    private Record (int number, String name) {
        this.number = number;
        this.name = name;
    }

    public Record (GenericScanner kbdScanner, int number) {
        this (number, kbdScanner.prompt
              (String.class, "Please enter name " + number));
    }

    public String toString () {
        return (format ("record %d containing %d", number, name));
    }
}
