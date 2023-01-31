using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DocumentBuilderLibrary.Json
{
    public class JSONBuilder : DocumentBuilderLibrary.Interfaces.IBuilder
    {
        DocumentBuilderLibrary.Interfaces.IComposite root;
        BranchJSON _currentBranch;
        public JSONBuilder()
        {
            root = new BranchJSON("");
            ((BranchJSON)root)._isRoot = true;
            _currentBranch = (BranchJSON)root;
        }
        public void BuildBranch(string name)
        {
            var temp = new BranchJSON(name);
            _currentBranch.AddChild(temp);
            var temp2 = _currentBranch;
            _currentBranch = temp;
            _currentBranch._parent = temp2;
        }

        public void BuildLeaf(string name, string content)
        {
            LeafJSON tempLeaf = new LeafJSON(name, content);
            _currentBranch.AddChild(tempLeaf);
        }

        public void CloseBranch()
        {
            _currentBranch = (BranchJSON)_currentBranch._parent;
        }

        public DocumentBuilderLibrary.Interfaces.IComposite GetDocument()
        {
            return root;
        }
    }
}
