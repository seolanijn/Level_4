using DocumentFactory.Interfaces;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentFactory.Documents
{
    public class HTMLDocument : IDocument
    {
        string filename;
        List<IElement> html_elm = new List<IElement>();

        //constructor
        public HTMLDocument(string filename)
        {
            this.filename = filename;   
        }
        public void AddElement(IElement E)
        {
            html_elm.Add(E);
        }

        public void RunDocument()
        {
            string result = "<!DOCTYPE html><html><head></head><body>\n";
            foreach (IElement E in html_elm)
            {
                result += E.ToString() + "\n\n";
            }

            try
            {
                File.WriteAllText(filename, result);
                Console.WriteLine($"'{filename}' has been created");
            }
            catch (IOException ex)
            {
                Console.WriteLine($"\n\nERROR: {ex.Message}.\n");
            }

        }
    }
}
