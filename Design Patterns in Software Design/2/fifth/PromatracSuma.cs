using System;

namespace fifth
{
    public class PromatracSuma:Promatrac
    {
        public PromatracSuma(SlijedBrojeva slijedBrojeva)
        {
            this.slijedBrojeva = slijedBrojeva;
            this.slijedBrojeva.Povezi(this);
        }

        public override void Azuriraj()
        {
            var brojevi = slijedBrojeva.DohvatiBrojeve();
            int suma = 0;
            foreach (var broj in brojevi)
            {
                suma += broj;
            }
            Console.WriteLine("Suma: {0}",suma);
        }
    }
}