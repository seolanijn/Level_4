/*
 * Program:         PasswordManager.exe
 * Module:          PasswordManager.cs
 * Date:            June 5, 2022
 * Author:          Seolan Jin, Junyeong Jo
 * Description:     This program reads the JSON file into an object and then allows the user to do some operations.
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;            // File class
using Newtonsoft.Json;  // JsonConvert class
using System.Web.Script.Serialization;
using Newtonsoft.Json.Schema;
using Newtonsoft.Json.Linq;

namespace PasswordManager
{
    class Program
    {
        // Declare constants for the file path names 
        private const string DATA_FILE = "account_data.json";
        private const string SCHEMA_FILE = "account_schema.json";

        private static List<Account> accounts = new List<Account>();
        static void Main(string[] args)
        {

            // Convert JSON to string
            string json_schema;
            try {
                json_schema = File.ReadAllText(SCHEMA_FILE);
            }
            catch {
                Console.WriteLine("ERROR:\tUnable to read the schema file.");
                return;
            }

            // Convert JSON to string
            string json_data;
            try
            {
                json_data = File.ReadAllText(DATA_FILE);
            }
            catch
            {
                Console.WriteLine("There is no json data file.");
                json_data = null;
            }

            // populate account list
            setAccountList(json_data);


            Console.WriteLine("PASSWORD MANAGEMENT SYSTEM");
            string userEnter;
            do
            {
                // display account entries
                showAccountEntries(accounts);

                Console.Write("Enter a command:\t");
                userEnter = Console.ReadLine();

                switch (userEnter)
                {
                    case "X": // Quit
                        Console.WriteLine("Terminate the program.");
                        return;
                    case "A": // Add new account
                        addAccount(json_schema);
                        break;
                    default: // show menu options
                        showMenuOptions(Convert.ToInt32(userEnter), json_schema);
                        break;
                }
                saveAccounts(accounts); // write account list to the JSON file
            } while (userEnter != "X");

        } // end Main()

        private static void addAccount(string json_schema)
        {
            Account newAccount = new Account();
            bool done = true;
            do
            {
                Console.WriteLine("\nPlease key-in values for the following fields..\n");

                Console.Write("Description:      ");
                newAccount.Description = Console.ReadLine();

                Console.Write("User ID:          ");
                newAccount.UserId = Console.ReadLine();

                Console.Write("Password:         ");
                Password newPassword = new Password();
                PasswordTester pt = new PasswordTester(Console.ReadLine());

                newPassword.Value = pt.Value;
                newPassword.StrengthText = pt.StrengthLabel;
                newPassword.StrengthNum = pt.StrengthPercent;
                newPassword.LastReset = DateTime.Now.ToShortDateString();

                newAccount.Password = newPassword;

                Console.Write("Login url:        ");
                newAccount.LoginUrl = Console.ReadLine();

                Console.Write("Account #:        ");
                newAccount.AccountNum = Console.ReadLine();

                // validate the new account
                if (!ValidateAccount(newAccount, json_schema))
                {
                    done = false;
                    Console.WriteLine("ERROR: Invalid account information. Please Try Again.");
                }
                else
                    done = true;

            } while (!done);

            accounts.Add(newAccount);
        }

        private static void saveAccounts(List<Account> acc)
        {
            try
            {
                File.WriteAllText(DATA_FILE, JsonConvert.SerializeObject(acc));
            }
            catch (IOException ex)
            {
                Console.WriteLine("Error: " + ex.Message);
            }
        } // end saveAccounts()

        private static void showMenuOptions(int idx, string schema)
        {
            Account userAccount = null;

            //find an account in account list
            int i = 1;
            foreach(var a in accounts)
            {
                if (i == idx)
                {
                    userAccount = a;
                    break;
                }
                i++;
            }

            //display account information
            string pswdStrength = userAccount.Password.StrengthText + " (" + userAccount.Password.StrengthNum + "%)";
            Console.Write("\n" +
                          "+-----------------------------------------------------------------------------+\n" +
                          "| " + idx + ". " + userAccount.Description.PadRight(73) + "|\n" +
                          "+-----------------------------------------------------------------------------+\n" +
                          "| User ID:           " + userAccount.UserId.PadRight(57) +                   "|\n" +
                          "| Password:          " + userAccount.Password.Value.PadRight(57) +           "|\n" +
                          "| Password Strength: " + pswdStrength.PadRight(57) +                         "|\n" +
                          "| Password Reset:    " + userAccount.Password.LastReset.PadRight(57) +       "|\n" +
                          "| Login url:         " + userAccount.LoginUrl.PadRight(57) +                 "|\n" +
                          "| Account #:         " + userAccount.AccountNum.PadRight(57) +               "|\n");
            Console.Write("+-----------------------------------------------------------------------------+\n" +
                          "|    Press P to change this password.                                         |\n" +
                          "|    Press D to delete this entry.                                            |\n" +
                          "|    Press M to return to the main menu.                                      |\n" +
                          "+-----------------------------------------------------------------------------+\n\n");

            Console.Write("Enter a command:\t");

            string userEnter = Console.ReadLine();

            switch (userEnter)
            {
                case "P": // change the password 
                    Console.Write("New Password:   \t");
                    i = 1;
                    foreach (var a in accounts)
                    {
                        if (i == idx)
                        {
                            a.Password.Value = Console.ReadLine();
                            a.Password.LastReset = DateTime.Now.ToShortDateString();
                            PasswordTester pt = new PasswordTester(a.Password.Value);
                            a.Password.StrengthText = pt.StrengthLabel;
                            a.Password.StrengthNum = pt.StrengthPercent;
                            if (!ValidateAccount(a, schema))
                            {
                                Console.WriteLine("Error: Password is invalid.");
                            }
                                break;
                        }
                        i++;
                    }
                    break;
                case "D": // delete the account
                    Console.Write("Delete? (Y/N):  \t");
                    if (Console.ReadLine() == "Y")
                        accounts.RemoveAt(--idx);
                    break;
                case "M": // return to the main menu
                    Console.Write("Return to the main menu.\n");
                    break;
                default: 
                    Console.Write("There is no such command.\n");
                    break;
            }

        } // end showMenuOptions()

        private static bool ValidateAccount(Account ac, string schema_file)
        {
            bool flag = JObject.Parse(JsonConvert.SerializeObject(ac)).IsValid(JSchema.Parse(schema_file));
            return flag;
        }

        private static void setAccountList(string json)
        {
            try
            {
                if (json != null)
                {
                    var jssAccounts = new JavaScriptSerializer().Deserialize<List<Account>>(json);
                    foreach (var a in jssAccounts)
                        accounts.Add(a);
                }
            }
            catch (JsonException ex)
            {
                Console.WriteLine(ex.Message);
            }
        } // end WriteAccountEntries()

        private static void showAccountEntries(List<Account> accounts)
        {
            Console.Write("\n" +
                          "+-----------------------------------------------------------------------------+\n" +
                          "|                               Account Entries                               |\n" +
                          "+-----------------------------------------------------------------------------+\n");

            if (accounts.Count == 0)
                Console.Write("|                             There is no account                             |\n");
            else
            {
                int i = 0;
                foreach (var a in accounts)
                    Console.WriteLine("| " + (++i) + ". " + a.Description.PadRight(73) + "|");
            }

            Console.Write("+-----------------------------------------------------------------------------+\n" +
                          "|    Press # from the above list to select an entry.                          |\n" +
                          "|    Press A to add a new entry.                                              |\n" +
                          "|    Press X to quit.                                                         |\n" +
                          "+-----------------------------------------------------------------------------+\n\n");
        } // end showAccountEntries()

    } // end class
}
