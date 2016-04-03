
import java.util.HashMap;

import com.andrewsoutar.cmp128.Utilities;
import com.andrewsoutar.cmp128.Utilities.BasicMenuAction;
import com.andrewsoutar.cmp128.Utilities.GenericScanner;
import com.andrewsoutar.cmp128.Utilities.MenuAction;
import com.andrewsoutar.cmp128.Utilities.UnaryFunction;
import com.andrewsoutar.cmp128.Utilities.VoidFunction;

public class Main {
    private static GenericScanner kbdScanner;

    private static Record [] recordsByNumber = {};
    private static HashMap <String, Record> recordsByName = new HashMap <> ();

    private static int getCount () {
        return (kbdScanner.<Integer, Integer>
                prompt (Integer.class,
                        "Enter the number of people in your organization",
                        new UnaryFunction <Integer, Integer> () {
                            public Integer call (Integer input) {
                                return (input > 0 ? input : null);
                            }
                        }));
    }

    private static MenuAction [] searchActions = new BasicMenuAction [] {
        new BasicMenuAction ("search by name") {
            public Boolean call () {
                String searchKey = kbdScanner.prompt (String.class, "Which name");
                Record r = recordsByName.get (searchKey);
                if (r != null) {
                    System.out.println ("Found " + r);
                } else {
                    System.out.println ("Sorry, but that name does not exist "
                                        + "in the list!");
                }
                return (false);
            }
        },

        new BasicMenuAction ("search by number") {
            public Boolean call () {
                Record r = kbdScanner.<Record, Integer>
                    prompt (Integer.class, "Which record number",
                            new UnaryFunction <Record, Integer> () {
                                public Record call (Integer number) {
                                    try {
                                        return (recordsByNumber [number - 1]);
                                    } catch (IndexOutOfBoundsException e) {
                                        return (null);
                                    }
                                }
                            });
                System.out.println ("Found " + r);
                return (false);
            }
        }
    };

    private static void pauseBeforeClear () {
        kbdScanner.<String>
            prompt (String.class, "Press enter to continue");
    }

    public static void main (String... args) {
        kbdScanner = new GenericScanner ();
        String companyName = kbdScanner.<String>
            prompt (String.class, "Enter company name");

        Utilities.mainLoop (kbdScanner, new VoidFunction () {
                public void call () {
                    Utilities.clearScreen ();
                    Utilities.printBordered (new String [] {
                            Utilities.centerString (companyName, 58),
                        });
                };
            }, new MenuAction [] {
                new MenuAction () {
                    public String getName () {
                        return ("enter new list of names into array");
                    }

                    public Boolean call () {
                        int count = getCount ();
                        recordsByNumber = new Record [count];
                        recordsByName = new HashMap <String, Record> ();

                        for (int i = 0; i < count; i++) {
                            int num = i + 1;
                            Record r = new Record (kbdScanner, num);

                            recordsByNumber [r.number - 1] = r;
                            recordsByName.put (r.name, r);
                        }
                        return (true);
                    }
                },
                new MenuAction () {
                    public String getName () {
                        return ("display a specific name from the array");
                    };
                    public Boolean call () {
                        Utilities.mainLoop (kbdScanner, null, searchActions);
                        pauseBeforeClear ();
                        return (true);
                    }
                },
                new MenuAction () {
                    public String getName () {
                        return ("display entire list of names from the array");
                    }

                    public Boolean call () {
                        String dashes = Utilities.repeat ('-', 60);

                        System.out.println ("The names list contains the "
                                            + "following entries:");
                        System.out.println (dashes);
                        for (int i = 0; i < recordsByNumber.length; i++) {
                            System.out.println (recordsByNumber [i].name);
                        }
                        System.out.println (dashes);
                        System.out.println ("End of list");
                        System.out.println (dashes);
                        pauseBeforeClear ();
                        return (true);
                    }
                },
                new MenuAction () {
                    public String getName () {
                        return ("exit application");
                    }

                    public Boolean call () {
                        return (Utilities.exitLoop (kbdScanner));
                    }
                }
            });
        System.out.println ("Thanks for using the " + companyName
                            + " Application!");
    }
}
