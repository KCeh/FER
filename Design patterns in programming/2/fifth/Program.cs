using System;

namespace fifth
{
    class Program
    {
        static void Main(string[] args)
        {
            SlijedBrojeva slijed1=new SlijedBrojeva(new TipkovnickiIzvor());
            PromatracSuma promatrac1 = new PromatracSuma(slijed1);
            PromatracProsjek promatrac2= new PromatracProsjek(slijed1);
            slijed1.Kreni();

            SlijedBrojeva slijed2 = new SlijedBrojeva(new DatotecniIzvor("proba.txt"));
            PromatracSuma promatrac3 = new PromatracSuma(slijed2);
            PromatracProsjek promatrac4 = new PromatracProsjek(slijed2);
            PromatracMedijan promatrac5 = new PromatracMedijan(slijed2);
            PromatracZapisnik promatrac6 = new PromatracZapisnik(slijed2);
            slijed2.Kreni();

            Console.ReadLine();
        }
    }
}
