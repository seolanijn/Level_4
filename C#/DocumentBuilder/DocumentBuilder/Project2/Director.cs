using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Project2
{
    public class Director : DocumentBuilderLibrary.Interfaces.IDirector
    {
        private DocumentBuilderLibrary.Interfaces.IBuilder _builder;
        public string _name;
        public string _content;
        public Director(DocumentBuilderLibrary.Interfaces.IBuilder builder)
        {
            _builder = builder;
        }
        public void BuildBranch()
        {
            if (_name != null)
                _builder.BuildBranch(_name);
            _name = null;
        }


        public void BuildLeaf()
        {
            if (_name != null && _content != null)
                _builder.BuildLeaf(_name, _content);
            _name = null;
            _content = null;
        }

        public void CloseBranch()
        {
            _builder.CloseBranch();
        }

        public DocumentBuilderLibrary.Interfaces.IComposite GetDocument()
        {
            return _builder.GetDocument();
        }
    }
}
