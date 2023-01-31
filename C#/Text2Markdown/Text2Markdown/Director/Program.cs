using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

using DocumentFactory.Factories;
using DocumentFactory.Interfaces;

namespace Director
{
    class Program
    {

        static void Main(string[] args)
        {
            string[] commands;
            var list = File.ReadAllText("CreateDocumentScript.txt");
            commands = list.Split('#');

            IDocumentFactory factory = null;
            IDocument document = null;
            IElement element;

            string htmlFileName = "";
            string mdFileName = "";

            foreach (var command in commands)
            {
                var strippedCommand = Regex.Replace(command, @"\t|\n|\r", ""); 
                var commandList = strippedCommand.Split(':');
                switch (commandList[0])
                {
                    case "Document":
                        if (commandList[1].Split(';')[0] == "Html")
                        {
                            factory = HTMLFactory.GetInstance();
                            document = factory.CreateDocument(htmlFileName = commandList[1].Split(';')[1]);
                        } 
                        else if (commandList[1].Split(';')[0] == "Markdown")
                        {
                            factory = MarkdownFactory.GetInstance();
                            document = factory.CreateDocument(mdFileName = commandList[1].Split(';')[1]);
                        }
                        break;
                    case "Element":
                            element = factory.CreateElement(commandList[1], commandList[2]);
                            document.AddElement(element);
                        break;
                    case "Run":
                        document.RunDocument();
                        break;
                    default:
                        break;
                }
            }

            System.Diagnostics.Process.Start("chrome.exe", Path.Combine(Directory.GetCurrentDirectory(), htmlFileName));
            System.Diagnostics.Process.Start("chrome.exe", Path.Combine(Directory.GetCurrentDirectory(), mdFileName));
        }
    }
}
