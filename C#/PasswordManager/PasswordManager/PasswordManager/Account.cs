/*
 * File:            Account.cs
 * Date:            June 5, 2022
 * Course:          INFO-3138
 * Description:     Defines the Account and Password classes 
 */

namespace PasswordManager
{
    class Account
    {
        public string Description { get; set; }
        public string UserId { get; set; }
        public string LoginUrl { get; set; }
        public string AccountNum { get; set; }
        public Password Password { get; set; }
    } // end class Account

    class Password
    {
        public string Value { get; set; }
        public int StrengthNum { get; set; }
        public string StrengthText { get; set; }
        public string LastReset { get; set; }
    } // end class Password
}