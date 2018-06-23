using System;

namespace sixth
{
    class Program
    {
        static void Main(string[] args)
        {
            Sheet s = new Sheet(5,5);
            s.Print();
            Console.WriteLine();

            s.SetContent("A1","2");
            s.SetContent("A2","5");
            s.SetContent("A3","A1+A2");
            s.Print();
            Console.WriteLine();

            s.SetContent("A1","3");
            s.Print();
            Console.WriteLine();

            s.SetContent("A4","1");
            s.SetContent("A3", "A1+A4");
            s.Print();
            Console.WriteLine();

            try
            {
                s.SetContent("A1","A3");
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }

            s.Print();
            Console.WriteLine();

            s.SetContent("A1", "8");
            s.Print();
            Console.WriteLine();

            s.SetContent("A5", "2");
            s.Print();
            Console.WriteLine();

            s.SetContent("A5", "A1");
            s.Print();
            Console.WriteLine();

            s.SetContent("C3", "A1+A2+A3");
            s.Print();
            Console.WriteLine();

            try
            {
                s.SetContent("A6", "3");
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            Console.WriteLine();

            s=new Sheet(6,6);
            s.SetContent("A5","2");
            s.SetContent("A1", "A5");
            s.SetContent("A5","1");
            s.Print();
            Console.WriteLine();

            Console.ReadLine();
        }
    }
}
