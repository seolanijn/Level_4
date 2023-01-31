using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentBuilderLibrary.Interfaces
{
    public interface IComposite
    {
        void AddChild(IComposite child);
        string Print(int depth);
    }
}
