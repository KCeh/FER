using System;

namespace fifth
{
    public class PromatracProsjek:Promatrac
    {
        public PromatracProsjek(SlijedBrojeva slijedBrojeva)
        {
            this.slijedBrojeva = slijedBrojeva;
            this.slijedBrojeva.Povezi(this);
        }

        public override void Azuriraj()
        {
            var brojevi = slijedBrojeva.DohvatiBrojeve();
            int suma=0;
            foreach (var broj in brojevi)
            {
                suma += broj;
            }

            var prosjek = (double) suma / brojevi.Count;
            Console.WriteLine("Prosjek: {0}", prosjek);
        }
    }
}