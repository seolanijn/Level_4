using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentBuilderLibrary.Xml
{
    public class XMLBuilder : DocumentBuilderLibrary.Interfaces.IBuilder
    {
        DocumentBuilderLibrary.Interfaces.IComposite root;
        BranchXML _currentBranch;
        public XMLBuilder()
        {
            root = new BranchXML("root");
            ((BranchXML)root)._isRoot = true;
            _currentBranch = (BranchXML)root;
        }
        public void BuildBranch(string name)
        {
            var temp = new BranchXML(name);
            _currentBranch.AddChild(temp);
            var temp2 = _currentBranch;
            _currentBranch = temp;
            _currentBranch._parent = temp2;
        }

        public void BuildLeaf(string name, string content)
        {
            _currentBranch.AddChild(new LeafXML(name, content));
        }

        public void CloseBranch()
        {
            _currentBranch = (BranchXML)_currentBranch._parent;
        }

        public DocumentBuilderLibrary.Interfaces.IComposite GetDocument()
        {
            return root;
        }
    }
}
