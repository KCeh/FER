using System;
using System.Collections.Generic;
using System.Threading;

namespace fifth
{
    public class SlijedBrojeva
    {
        private readonly List<int> _brojevi;
        private readonly IIzvorBrojeva _izvorBrojeva;
        private readonly List<Promatrac> _promatraci;

        public SlijedBrojeva(IIzvorBrojeva izvorBrojeva)
        {
            _brojevi = new List<int>();
            _izvorBrojeva = izvorBrojeva;
            _promatraci=new List<Promatrac>();
        }

        public void Kreni()
        {
            while (true)
            {
                int i = _izvorBrojeva.Ucitaj();
                if (i == -1)
                {
                    Console.WriteLine("Zavrsen unos!");
                    break;
                }
                _brojevi.Add(i);
                Obavijesti();
                Thread.Sleep(1000);
            }
        }

        private void Obavijesti()
        {
            foreach (var promatrac in _promatraci)
            {
                promatrac.Azuriraj();
            }
        }

        public void Povezi(Promatrac promatrac)
        {
            _promatraci.Add(promatrac);
        }

        public void Otpoji(Promatrac promatrac)
        {
            _promatraci.Remove(promatrac);
        }

        public List<int> DohvatiBrojeve()
        {
            return _brojevi;
        }
    }
}