using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;

namespace fifth
{
    public class PromatracMedijan:Promatrac
    {
        public PromatracMedijan(SlijedBrojeva slijedBrojeva)
        {
            this.slijedBrojeva = slijedBrojeva;
            this.slijedBrojeva.Povezi(this);
        }

        //https://www.mathsisfun.com/median.html
        public override void Azuriraj()
        {
            List<int> brojevi = slijedBrojeva.DohvatiBrojeve();
            List<int> brojeviSortirani = new List<int>(brojevi);
            brojeviSortirani.Sort();
            int velicina = brojeviSortirani.Count;
            double medijan;
            if (velicina == 1) medijan = brojeviSortirani.ElementAt(0);
            if (velicina % 2 != 0)
            {
                medijan = brojeviSortirani.ElementAt(velicina / 2);
            }
            else
            {
                medijan = (double)(brojeviSortirani.ElementAt(velicina / 2) + brojeviSortirani.ElementAt(velicina / 2-1)) /2;
            }
            Console.WriteLine("Medijan: {0}",medijan);
        }
    }
}