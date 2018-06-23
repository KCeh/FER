#include<stdio.h>
#include<malloc.h>

////////////////////////////////////////////////////////////////
typedef void* (*PTR)(void);

typedef struct{
    int lower_bound;
    int upper_bound;
    PTR* vtable;
} Unary_function;

typedef double (*PTRFUN)(Unary_function* ,double);

double negative_value_at(Unary_function* unary, double x) {
      PTRFUN fun = (PTRFUN)unary->vtable[0];
      return -fun(unary,x);
}

void tabulate(Unary_function* unary) {
    int x=unary->lower_bound;
    int upper_bound=unary->upper_bound;
    PTRFUN func=(PTRFUN)unary->vtable[0];
    for(x; x <= upper_bound; x++) {
        printf("f(%d)=%lf\n", x, func(unary,x));
    }
}

PTRFUN unary_vtable[2]={
    (PTRFUN)NULL,
    (PTRFUN)negative_value_at
};

void initUnary(Unary_function* unary, int lb, int ub){
    unary->lower_bound=lb;
    unary->upper_bound=ub;
    unary->vtable=unary_vtable; //provjeri za cast itd
}

Unary_function* newUnaryFunction(int lb, int ub){
    Unary_function* unary=(Unary_function*)malloc(sizeof(Unary_function));
    initUnary(unary, lb, ub);
    return unary;
}


int same_functions_for_ints(Unary_function *f1, Unary_function *f2, double tolerance) {
    if(f1->lower_bound != f2->lower_bound) return 0;
    if(f1->upper_bound != f2->upper_bound) return 0;

    int x=f1->lower_bound;
    int upper_bound=f1->upper_bound;
    PTRFUN fun1=(PTRFUN)f1->vtable[0];
    PTRFUN fun2=(PTRFUN)f2->vtable[0];
    for(x; x <= upper_bound; x++) {
        double delta = fun1(f1,x)-fun2(f2,x);
        if(delta < 0) delta = -delta;
        if(delta > tolerance) return 0;
    }
    return 1;
}

/////////////////////////////////////////////////////////////
typedef struct{
    int lower_bound;
    int upper_bound;
    PTRFUN* vtable;
}Square;

double square_value_at(Unary_function* unary,double x) {
      return x*x;
}

PTRFUN square_vtable[2]={
    square_value_at,
    negative_value_at
};

void initSquare(Square* square, int lb, int ub){
    initUnary((Unary_function*)square, lb, ub);
    square->vtable=square_vtable;
}

Square* newSquare(int lb, int ub){
    Square* square=(Square*)malloc(sizeof(Square));
    initSquare(square, lb, ub);
    return square;
}

///////////////////////////////////////////////////////////////
typedef struct{
    int lower_bound;
    int upper_bound;
    PTRFUN* vtable;
    double a;
    double b;
}Linear;

double linear_value_at(Unary_function* unary,double x) {
      Linear* linear=(Linear*) unary;
      return linear->a*x+linear->b;
}

PTRFUN linear_vtable[2]={
    linear_value_at,
    negative_value_at
};

void initLinear(Linear* linear, int lb, int ub, double a, double b){
    initUnary((Unary_function*)linear, lb, ub);
    linear->vtable=linear_vtable;
    linear->a=a;
    linear->b=b;
}

Linear* newLinear(int lb, int ub, double a, double b){
    Linear* linear=(Linear*)malloc(sizeof(Linear));
    initLinear(linear, lb, ub, a, b);
    return linear;
}

//////////////////////////////////////////////////////////////
int main() {
  Unary_function *f1 = (Unary_function*)newSquare(-2, 2);
  tabulate(f1);
  Unary_function *f2 = (Unary_function*)newLinear(-2, 2, 5, -2);
  tabulate(f2);
  printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");

  PTRFUN fun=f2->vtable[1];
  printf("neg_val f2(1) = %lf\n", fun(f2,1.0));
  free(f1);
  free(f2);
  return 0;
}
