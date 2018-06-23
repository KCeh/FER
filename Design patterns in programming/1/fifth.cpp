#include<iostream>
using namespace std;
typedef int (*pfun) ();

class B{
public:
  virtual int prva()=0;
  virtual int druga()=0;
};

class D: public B{
public:
  virtual int prva(){return 0;}
  virtual int druga(){return 42;}
};

void test(B* pb){


    pfun* vptr =  *(pfun**)pb;
    cout<<"Output of prva(): "<<vptr[0]()<<endl;
    cout<<"Output of druga(): "<<(*(vptr+1))()<<endl;
}

int main(){
  B* pb=new D();
  test(pb);
}
