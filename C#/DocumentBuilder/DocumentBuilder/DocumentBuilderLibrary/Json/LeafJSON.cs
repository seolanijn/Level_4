using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentBuilderLibrary.Json
{
    public class LeafJSON : DocumentBuilderLibrary.Interfaces.IComposite
    {
        string _name;
        string _content;
        public int _depth;
        public LeafJSON(string name, string content)
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
            _print += $"\'{_name}\' : \'{_content}\'\n";
            return _print;
        }

        public string getName() { return _name; }
        public string getContent() { return _content; }
    }
}
