using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentBuilderLibrary.Xml
{
    public class BranchXML : DocumentBuilderLibrary.Interfaces.IComposite
    {
        private List<DocumentBuilderLibrary.Interfaces.IComposite> _children = new List<DocumentBuilderLibrary.Interfaces.IComposite>();
        string _name;
        public DocumentBuilderLibrary.Interfaces.IComposite _parent = null;
        public int _depth = 0;
        public bool _isRoot = false;
        public BranchXML(string name)
        {
            _name = name;
        }
        public void AddChild(DocumentBuilderLibrary.Interfaces.IComposite child)
        {
            _children.Add(child);
        }

        public string Print(int depth)
        {
            string _print = "";
            if (_isRoot)
            {
                _print += new string(' ', depth * 4);
                _print += "<root>\n";

                foreach (var child in _children)
                    _print += child.Print(depth + 1);
            }
            else
            {
                _print += new string(' ', depth * 4);
                _print += $"<{_name}>\n";

                foreach (var child in _children)
                {
                    _print += child.Print(depth + 1);
                }
            }
            _print += new string(' ', depth * 4);
            _print += $"</{_name}>\n";
            return _print;
        }
    }
}
