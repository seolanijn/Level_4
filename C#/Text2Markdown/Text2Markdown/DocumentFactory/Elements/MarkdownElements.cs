using DocumentFactory.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentFactory.Elements
{
    public class MarkdownElements
    {
        public class MarkdownHeader : IElement
        {
            string properties;

            //constructor
            public MarkdownHeader(string props)
            {
                properties = props;
            }

            //ToString
            public override string ToString()
            {
                string[] temp = properties.Split(';');

                switch (temp[0])
                {
                    case "1":
                        return "# " + temp[1];
                    case "2":
                        return "## " + temp[1];
                    case "3":
                        return "### " + temp[1];
                    default:
                        return "";
                }
            }
        }
        public class MarkdownImage : IElement
        {
            string properties;

            //constructor
            public MarkdownImage(string props)
            {
                properties = props;
            }

            //ToString
            public override string ToString()
            {
                string[] temp = properties.Split(';');
                return "![" + temp[1] + "](" + temp[0] + " \"" + temp[2] + "\")";
            }
        }
        public class MarkdownList : IElement
        {
            string properties;

            //constructor
            public MarkdownList(string props)
            {
                properties = props;
            }

            //ToString
            public override string ToString()
            {
                string[] temp = properties.Split(';');
                string result = "";
                switch (temp[0])
                {
                    case "Unordered":
                        for (var i = 1; i < temp.Length; i++)
                            result += "* " + temp[i] + "\n";
                        break;
                    case "Ordered":
                        for (var i = 1; i < temp.Length; i++)
                            result += i + ". " + temp[i] + "\n";
                        break;
                    default:
                        break;
                }
                return result;
            }
        }
        public class MarkdownTable : IElement
        {
            string properties;

            //constructor
            public MarkdownTable(string props)
            {
                properties = props;
            }

            //ToString
            public override string ToString()
            {
                string[] temp = properties.Split(';');
                string result = "";

                for (var i = 0; i < temp.Length; i++)
                {
                    string[] tr = temp[i].Split('$'); 
                    switch (tr[0])
                    {
                        case "Head":
                            result += "|";
                            for (int j = 1; j < tr.Length; j++)
                                result += tr[j] + "|";
                            result += "\n|";
                            for (int j = 1; j < tr.Length; j++)
                                result += " --- |";
                            result += "\n";
                            break;
                        case "Row":
                            result += "|";
                            for (int j = 1; j < tr.Length; j++)
                                result += tr[j] + "|";
                            result += "\n";
                            break;
                        default:
                            break;
                    }
                }
                return result;
            }
        }

    }
}
