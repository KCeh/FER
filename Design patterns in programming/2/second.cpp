#include<iostream>
#include<stdlib.h>
#include<string.h>
#include <vector>
#include <list>

template <typename Iterator, typename Predicate>
Iterator mymax(
  Iterator cur, Iterator last, Predicate pred){
    Iterator max=cur;
    while(cur != last){
        if (pred(cur, max)>0) max=cur;
        cur++;
    }
    return max;
}

int gt_int(int * first, int * second){
    if(*first>*second) {
        return 1;
    }
    else {
        return 0;
    }
}

int gt_str (const void *first, const void *second){
    const char **a = (const char **)first;
    const char **b = (const char **)second;

    return strcmp(*a, *b);
}

int gt_ite(std::vector<double>::iterator first, std::vector<double>::iterator second){

    if(*first>*second){
       return 1;
    }
    else {
        return 0;
    }
}

int gt_list(std::list<double>::iterator first, std::list<double>::iterator second){

    if(*first>*second){
       return 1;
    }
    else {
        return 0;
    }
}


int main(){
    int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
    const char* arr_str[] = {
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"
    };
    std::vector<double> myvector;
    myvector.push_back(10.3);
    myvector.push_back(25.8);
    myvector.push_back(400.5);
    myvector.push_back(10/3);

    std::list<double> mylist;
    mylist.push_back(44.5);
    mylist.push_back(66.5*3);
    mylist.push_back(100/9);
    mylist.push_back(3.14);


    const int* maxint = mymax( &arr_int[0],
        &arr_int[sizeof(arr_int)/sizeof(*arr_int)], gt_int);
    std::cout <<"Max int: "<<*maxint <<"\n";

    const char ** maxstring = mymax(&arr_str[0], &arr_str[sizeof(arr_str)/sizeof(*arr_str)],gt_str);
    std::cout<<"Max string: "<<*maxstring<<"\n";

    std::vector<double>::iterator it = mymax(myvector.begin(), myvector.end(), gt_ite);
    std::cout<<"Max double in myvector: "<<*it<<"\n";

    std::list<double>::iterator iter = mymax(mylist.begin(), mylist.end(), gt_list);
    std::cout<<"Max double in mylist: "<<*iter<<"\n";
}
