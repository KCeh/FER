using System;
using System.IO;

namespace fifth
{
    public class DatotecniIzvor:IIzvorBrojeva
    {
        private readonly StreamReader _streamReader;

        public DatotecniIzvor(string staza)
        {
            var fileStream = File.OpenRead(staza);
            _streamReader = new StreamReader(fileStream);
        }

        public int Ucitaj()
        {

            string linija = _streamReader.ReadLine();
            try
            {
                int i = Int32.Parse(linija);
                return i;
            }
            catch (Exception ex)
            {
                return -1;
            }

        }
    }
}