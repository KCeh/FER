def mymax(iterable, key=lambda x:x):
    #incijaliziraj maksimalni element i maksimalni kljuc
    max_x=max_key=None

    # obidi sve elemente
    for x in iterable:
        #ako je max_key none???
        if key(x)>max_key:
            max_key=key(x)
            max_x=x
        #ako je key(x) najveci -> azuriraj max_x i max_key

    # vrati rezultat
    return max_x


def main():
    #old code
    f=lambda x:x
    g=lambda x:ord(x)
    h=lambda x:len(x) #for longest string

    maxint = mymax([1, 3, 5, 7, 4, 6, 9, 2, 0])
    print "maxint: ",maxint
    maxchar = mymax("Suncana strana ulice")
    print "maxchar: ", maxchar
    maxstring = mymax([
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"])
    print "maxstring: ", maxstring

    D = {'burek': 8, 'buhtla': 5}
    maxdic=mymax(D,D.get)
    print "max value in dictionary: ",maxdic

    people=[("Elon", "Musk"),("Bill", "Gates"),("Tony","Stark"), ("Tony", "Parker")]
    maxpeople=mymax(people)
    print "max in people: ",maxpeople


if __name__ == "__main__":
    main()