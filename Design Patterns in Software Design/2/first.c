#include <stdio.h>
#include <stdlib.h>
#include <string.h>


const void* mymax(
  const void *base, size_t nmemb, size_t size,
  int (*compar)(const void *, const void *)){
    if(nmemb==0) return NULL;
    void * max=base;

    size_t i=0;
    for(i;i<nmemb;i++){
        if(compar((base+i*size), max)>0) max=(base+i*size);
    }
    return max;
}

int gt_int(const void * first, const void * second){
    int * a = (int *) first;
    int * b = (int *) second;

    if(*a>*b) return 1;
    else return 0;
}

int gt_char(const void *first, const void *second){
    char * a = (char *) first;
    char * b = (char *) second;

    if(*a>*b) return 1;
    else return 0;
}

int gt_str (const void *first, const void *second){
    const char **a = (const char **)first;
    const char **b = (const char **)second;

    return strcmp(*a, *b);
}

int main(){
    int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
    char arr_char[]="Suncana strana ulice";
    const char* arr_str[] = {
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"
    };

    const int * maxint = (int *) mymax(arr_int,sizeof(arr_int)/sizeof(int),sizeof(int),gt_int);
    printf("Max int: %d\n",*maxint);
    char * maxchar = (char *) mymax(arr_char,sizeof(arr_char)/sizeof(char), sizeof(char),gt_char);
    printf("Max char: %c\n",*maxchar);

    const char ** maxstring = (const char **)mymax(arr_str, sizeof(arr_str)/sizeof(char*), sizeof(char*),gt_str);
    printf("Max string: %s\n",*maxstring);

    return 0;
}
