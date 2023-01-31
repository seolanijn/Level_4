using DocumentFactory.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentFactory.Factories
{
    public class HTMLFactory : IDocumentFactory
    {
        private static HTMLFactory instance;

        //constructor
        private HTMLFactory()
        {
            
        }
        
        //Singleton method
        public static HTMLFactory GetInstance()
        {
            if (instance == null)
                instance = new HTMLFactory();
            return instance;
        }

        public IDocument CreateDocument(string fileName)
        {
            return new Documents.HTMLDocument(fileName);
        }

        public IElement CreateElement(string elementType, string props)
        {
            switch (elementType)
            {
                case "Header":
                    return new Elements.HTMLElements.HTMLHeader(props);
                case "Image":
                    return new Elements.HTMLElements.HTMLImage(props);
                case "List":
                    return new Elements.HTMLElements.HTMLList(props);
                case "Table":
                    return new Elements.HTMLElements.HTMLTable(props);
                default:
                    return null;
            }
        }
    }
}
