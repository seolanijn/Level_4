using DocumentFactory.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentFactory.Factories
{
    public class MarkdownFactory : IDocumentFactory
    {
        private static MarkdownFactory instance;

        //constructor
        private MarkdownFactory()
        {
            
        }

        //Singleton method
        public static MarkdownFactory GetInstance()
        {
            if (instance == null)
                instance = new MarkdownFactory();
            return instance;
        }
        public IDocument CreateDocument(string fileName)
        {
            return new Documents.MarkdownDocument(fileName);
        }

        public IElement CreateElement(string elementType, string props)
        {
            switch (elementType)
            {
                case "Header":
                    return new Elements.MarkdownElements.MarkdownHeader(props);
                case "Image":
                    return new Elements.MarkdownElements.MarkdownImage(props);
                case "List":
                    return new Elements.MarkdownElements.MarkdownList(props);
                case "Table":
                    return new Elements.MarkdownElements.MarkdownTable(props);
                default:
                    return null;
            }
        }
    }
}
