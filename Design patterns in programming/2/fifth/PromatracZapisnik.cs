using System;
using System.IO;

namespace fifth
{
    public class PromatracZapisnik:Promatrac
    {
        private readonly StreamWriter _streamWriter;

        public PromatracZapisnik(SlijedBrojeva slijedBrojeva)
        {
            this.slijedBrojeva = slijedBrojeva;
            _streamWriter = new StreamWriter("zapis.txt", true);
            this.slijedBrojeva.Povezi(this);
        }

        public override void Azuriraj()
        {
            var brojevi = slijedBrojeva.DohvatiBrojeve();
            foreach (var broj in brojevi)
            {
                _streamWriter.Write("{0} ",broj);
            }
            _streamWriter.Write(DateTime.UtcNow);
            _streamWriter.WriteLine("");
            _streamWriter.Flush();
        }
    }
}