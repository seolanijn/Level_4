using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentBuilderLibrary.Xml
{
    public class LeafXML : DocumentBuilderLibrary.Interfaces.IComposite
    {
        string _name;
        string _content;
        public int _depth;
        public LeafXML(string name, string content)
        {
            _name = name;
            _content = content;
        }
        public void AddChild(DocumentBuilderLibrary.Interfaces.IComposite child)
        {

        }

        public string Print(int depth)
        {
            string _print = "";
            _print += new string(' ', depth * 4);
            for (int i = 0; i < _depth; i++)
            {
                _print += new string(' ', depth * 4);
                _print += "   ";
            }
            _print += $"<{_name}>{_content}</{_name}>\n";
            return _print;
        }
    }
}
