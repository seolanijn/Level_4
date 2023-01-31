using DocumentFactory.Interfaces;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentFactory.Documents
{
    class MarkdownDocument : IDocument
    {
        string filename;
        List<IElement> md_elm = new List<IElement>();

        //constructor
        public MarkdownDocument(string filename)
        {
            this.filename = filename;
        }
        public void AddElement(IElement E)
        {
            md_elm.Add(E);
        }

        public void RunDocument()
        {
            string result = "";
            foreach (IElement E in md_elm)
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
