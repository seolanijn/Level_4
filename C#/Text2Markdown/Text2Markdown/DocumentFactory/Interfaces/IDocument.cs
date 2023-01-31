using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentFactory.Interfaces
{
    public interface IDocument
    {
        void RunDocument();
        void AddElement(IElement E);
    }
}
