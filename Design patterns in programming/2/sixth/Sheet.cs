using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Data;
using System.Runtime.InteropServices;
using System.Text.RegularExpressions;

namespace sixth
{
    public class Sheet
    {
        private Cell[,] _cells;
        private int sheetWidth, sheetHeight;

        public Sheet(int width, int height)
        {
            sheetWidth = width;
            sheetHeight = height;
            _cells = new Cell[width, height];
            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    _cells[i,j]=new Cell();
                }
            }
        }

        public void Print()
        {
            for (int i = 0; i < sheetWidth; i++)
            {
                int name = 65 + i;
                for (int j = 1; j <= sheetHeight; j++)
                {
                    string reference = new string((char)name,1);
                    reference = reference + j;
                    Cell cell = GetCell(reference);
                    int? value = cell.GetValue();
                    if (value.HasValue)
                    {
                        Console.Write("{0}: {1}", reference, value);
                        Console.WriteLine();
                    }
                }
            }
        }

        public Cell GetCell(string reference)
        {
            int w=0, h=0;
            Regex regexLetter = new Regex(@"[A-Z]");
            Match matchLetter = regexLetter.Match(reference);
            if(!matchLetter.Success) throw new Exception("Krivo imenovana celija");

            Regex regexNumber = new Regex(@"\d+");
            Match matchNumber = regexNumber.Match(reference);
            if(!matchNumber.Success) throw new Exception("Krivo imenovana celija");

            try
            {
                char c = matchLetter.Value[0];
                w = c-65;
                h = Int32.Parse(matchNumber.Value)-1;
            }
            catch (Exception e)
            {
                Console.WriteLine("Krivo imenovana celija");
            }

            try
            {
                return _cells[w, h];
            }
            catch (Exception ex)
            {
                throw new Exception("Celija izvan raspona!");
            }
        }

        public void SetContent(string reference, string content)
        {
            try
            {
                var cell = GetCell(reference);
                cell.SetExp(content);
                Evaluate(cell);
                Notify();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

        }

        private void Notify()
        {
            foreach (var cell in _cells)
            {
                Evaluate(cell);
            }
        }

        public List<Cell> GetRefs(Cell cell)
        {
            return cell.GetReferences();
        }

        public void Evaluate(Cell cell)
        {
            String exp = cell.GetExp();
            int value=0;

            if (exp==null) return;
            try
            {
                value=Int32.Parse(exp);
                cell.SetValue(value);
            }
            catch (Exception e)//nije broj nego izraz
            {
                string[] otherCells = exp.Split('+');
                List<Cell> references = cell.GetReferences();
                references.Clear();

                foreach (var otherCellRef in otherCells)
                {
                    Cell otherCell = GetCell(otherCellRef);
                    List<Cell> otherRefrences = otherCell.GetReferences();
                    if(otherRefrences.Contains(cell)) throw new Exception("Recursive references! Old value will be saved");
                    references.Add(otherCell);
                }

                foreach (var otherCell in references)
                {
                    int? otherValue = otherCell.GetValue();
                    if(otherValue.HasValue)
                        value += otherValue.Value;
                }
                cell.SetValue(value);
            }
           
        }
    }
}