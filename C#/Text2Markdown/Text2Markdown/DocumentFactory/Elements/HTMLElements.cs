using DocumentFactory.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentFactory.Elements
{
    public class HTMLElements
    {
        public class HTMLHeader : IElement
        {
            string properties;

            //constructor
            public HTMLHeader(string props)
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
                        return "<h1>" + temp[1] + "</h1>";
                    case "2":
                        return "<h2>" + temp[1] + "</h2>";
                    case "3":
                        return "<h3>" + temp[1] + "</h3>";
                    default:
                        return "";
                }
            }
        }
        public class HTMLImage : IElement
        {
            string properties;

            //constructor
            public HTMLImage(string props)
            {
                properties = props;
            }

            //ToString
            public override string ToString()
            {
                string[] temp = properties.Split(';');
                return "<img alt='" + temp[1] + "' title='" + temp[2] + "' src='" + temp[0] + "' />";
            }
        }
        public class HTMLList : IElement
        {
            string properties;

            //constructor
            public HTMLList(string props)
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
                        result = "<ul>";
                        for (var i = 1; i < temp.Length; i++)
                        {
                            result += "<li>" + temp[i] + "</li>";
                        }
                        result += "</ul>";
                        break;
                    case "Ordered":
                        result = "<ol>";
                        for (var i = 1; i < temp.Length; i++)
                        {
                            result += "<li>" + temp[i] + "</li>";
                        }
                        result += "</ol>";
                        break;
                }
                return result;
            }
        }
        public class HTMLTable : IElement
        {
            string properties;

            //constructor
            public HTMLTable(string props)
            {
                properties = props;
            }

            //ToString
            public override string ToString()
            {
                string[] temp = properties.Split(';');
                string result = "<table>";

                for (var i = 0; i < temp.Length; i++)
                {
                    string[] tr = temp[i].Split('$'); 
                    switch (tr[0])
                    {
                        case "Head":
                            result += "<thead><tr>";
                            for (int j = 1; j < tr.Length; j++)
                                result += "<th>" + tr[j] + "</th>";
                            result += "</tr></thead>";
                            break;
                        case "Row":
                            result += "<tr>";
                            for (int j = 1; j < tr.Length; j++)
                                result += "<td>" + tr[j] + "</td>";
                            result += "</tr>";
                            break;
                        default:
                            break;
                    }
                }
                result += "</table>";
                return result;
            }
        }
    }
    
}
