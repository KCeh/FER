#include<stdio.h>
#include<malloc.h>

typedef char const* (*PTRFUN)();

///////////////////////////////////////////////////////////////
typedef struct {
    char* name;
    PTRFUN* vtable;
} Animal;

PTRFUN animalVtb[2]={NULL, NULL};

void constructAnimal(Animal* animal, char* name){
    animal->name=name;
    animal->vtable=animalVtb;
}

void animalPrintGreeting(Animal* animal){
    printf("%s pozdravlja: %s\n",animal->name, animal->vtable[0]());
}

void animalPrintMenu(Animal* animal){
    printf("%s voli %s\n",animal->name, animal->vtable[1]());
}
///////////////////////////////////////////////////////////////////////////

char const* dogGreet(void){
  return "vau!";
}
char const* dogMenu(void){
  return "kuhanu govedinu";
}
char const* catGreet(void){
  return "mijau!";
}
char const* catMenu(void){
  return "konzerviranu tunjevinu";
}

PTRFUN dogVtb[2]={dogGreet, dogMenu};
PTRFUN catVtb[2]={catGreet, catMenu};

void constructDog(Animal* dog, char* name){
    constructAnimal(dog, name);
    dog->vtable=dogVtb;
}

Animal* createDog(char* name){
    Animal* dog=(Animal*)malloc(sizeof(Animal));
    constructDog(dog,name);
    return dog;
}

void constructCat(Animal* cat, char* name){
    constructAnimal(cat, name);
    cat->vtable=catVtb;
}

Animal* createCat(char* name){
    Animal* cat=(Animal*)malloc(sizeof(Animal));
    constructCat(cat,name);
    return cat;
}
////////////////////////////////////////////

char const* horseGreet(void){
  return "sdsdf";
}
char const* horseMenu(void){
  return "kuhansdfsdfsdfu govedinu";
}


PTRFUN horseVtb[2]={horseGreet, horseMenu};

void counstructHorse(Animal* horse, char* name){
	constructAnimal(horse, name);
	horse->vtable=horseVtb;
}



void testAnimals(void){
  struct Animal* p1=createDog("Hamlet");
  struct Animal* p2=createCat("Ofelija");
  struct Animal* p3=createDog("Polonije");

  int a;
  Animal an;
  counstructHorse(&an, "KONJ");

  animalPrintGreeting(&an);

  animalPrintGreeting(p1);
  animalPrintGreeting(p2);
  animalPrintGreeting(p3);

  animalPrintMenu(p1);
  animalPrintMenu(p2);
  animalPrintMenu(p3);

  free(p1); free(p2); free(p3);
}

int main(){
    testAnimals();
    return 0;
}
