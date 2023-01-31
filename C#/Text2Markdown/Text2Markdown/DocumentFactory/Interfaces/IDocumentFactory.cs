using System;
using System.Collections.Generic;
using System.Text;

namespace DocumentFactory.Interfaces
{
    public interface IDocumentFactory
    {
        IDocument CreateDocument(string fileName);
        IElement CreateElement(string elementType, string props);
    }
}
