using System;

namespace fifth
{
    public class TipkovnickiIzvor:IIzvorBrojeva
    {
        public int Ucitaj()
        {
            Console.WriteLine("Unesi broj: ");
            try
            {
                string ulaz = Console.ReadLine();
                int i = Int32.Parse(ulaz);
                return i;
            }
            catch (Exception ex)
            {
                return -1;
            }

        }
    }
}