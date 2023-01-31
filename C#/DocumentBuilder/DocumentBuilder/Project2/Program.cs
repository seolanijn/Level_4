namespace Project2
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Document Builder Console Client\n");
            displayUsage();

            string userInput;
            bool setMode = false;


            Director? dir = null;

            do
            {
                Console.Write("> ");
                userInput = Console.ReadLine()!;
                string[] splitInput = userInput.Split(':');
                for (int i = 0; i < splitInput.Length; i++)
                    splitInput[i] = splitInput[i].ToLower();

                if (!setMode && (splitInput[0] == "branch" ||
                    splitInput[0] == "leaf" || splitInput[0] == "close" || splitInput[0] == "print"))
                {
                    Console.WriteLine("ERROR: mode has not been set.");
                    continue;
                }

                switch (splitInput[0])
                {
                    case "help":
                        displayUsage();
                        break;
                    case "mode":
                        setMode = true;
                        if (splitInput[1] == "json")
                            dir = new Director(new DocumentBuilderLibrary.Json.JSONBuilder());
                        else if (splitInput[1] == "xml")
                            dir = new Director(new DocumentBuilderLibrary.Xml.XMLBuilder());
                        else
                            Console.WriteLine("Error: invalid input.");
                        break;
                    case "branch":
                        if (splitInput.Length == 2)
                        {
                            dir._name = splitInput[1];
                            dir.BuildBranch();
                        }
                        else
                            Console.WriteLine("Error: invalid input.");
                        break;
                    case "leaf":
                        if (splitInput.Length == 3)
                        {
                            dir._name = splitInput[1];
                            dir._content = splitInput[2];
                            dir.BuildLeaf();
                        }
                        else
                            Console.WriteLine("Error: invalid input.");
                        break;
                    case "close":
                        dir.CloseBranch();
                        break;
                    case "print":
                        Console.WriteLine(dir!.GetDocument().Print(0));
                        break;
                    case "exit":
                        Console.WriteLine("Exit the program.");
                        break;
                    default:
                        Console.WriteLine("Error: no such command.");
                        break;

                }
            } while (userInput != "exit");
        }

        public static void displayUsage()
        {
            Console.WriteLine("Usage:\n" +
                              "help                           - Prints Usage (this page).\n" +
                              "mode:< JSON | XML >            - Sets mode to JSON or XML.Must be set before creating or closing.\n" +
                              "branch:<name>                  - Creates a new branch, assigning it the passed name.\n" +
                              "leaf:<name>:<content>          - Creates a new leaf, assigning the passed name and content.\n" +
                              "close                          - Closes the current branch, as long as it is not the root.\n" +
                              "print                          - Prints the document in its current state to the console.\n" +
                              "exit                           - Exits the application.\n");
        }
    }
}