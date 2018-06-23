#include "myfactory.h"
#include <windows.h>
#include <stdio.h>
//https://msdn.microsoft.com/en-us/library/windows/desktop/ms686944(v=vs.85).aspx
typedef struct Animal* (*MYPROC)(char const*);

void* myfactory(char const* libname, char const* ctorarg){
    struct Animal* animal;
    HINSTANCE hinstLib;
    MYPROC procedure;

    hinstLib = LoadLibrary(libname);

    if (hinstLib != NULL)
    {
        procedure = (MYPROC) GetProcAddress(hinstLib, "create");

        if (NULL != procedure)
        {
            animal=procedure(ctorarg);
        }
    }
    return animal;
}
