using System;
using System.Collections.Generic;

namespace sixth
{
    public class Cell
    {
        private string exp;
        private int? value;
        private readonly List<Cell> references;

        public Cell()
        {
            exp=null;
            references=new List<Cell>();
        }

        public List<Cell> GetReferences()
        {
            return references;
        }

        public void SetExp(string content)
        {
            exp = content;
        }

        public void SetValue(int value)
        {
            this.value = value;
        }

        public int? GetValue()
        {
            return value;
        }

        public string GetExp()
        {
            return exp;
        }
    }
}