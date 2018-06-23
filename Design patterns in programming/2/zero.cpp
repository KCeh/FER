#include <iostream>
#include <STDLIB.H>
#include <assert.h>

  struct Point{
    int x; int y;
  };
  struct Shape{
    enum EType {circle, square, rhomb};
    EType type_;
  };
  struct Circle{
     Shape::EType type_;
     double radius_;
     Point center_;
  };
  struct Square{
     Shape::EType type_;
     double side_;
     Point center_;
  };
  struct Rhomb{
     Shape::EType type_;
     double side_;
     Point center_;
  };
  void drawSquare(struct Square*){
    std::cerr <<"in drawSquare\n";
  }
  void drawCircle(struct Circle*){
    std::cerr <<"in drawCircle\n";
  }
  void drawRhomb(struct Rhomb*){
    std::cerr <<"in drawRhomb\n";
  }
  void moveShapes(Shape** shapes, int n, int x, int y ){
    for (int i=0; i<n; ++i){
      struct Shape* s = shapes[i];
      switch (s->type_){
      case Shape::square:
        struct Square* a;
        a=(struct Square*)s;
        a->center_.x+=x;
        a->center_.y+=y;
        break;
      case Shape::circle:
        struct Circle* b;
        b=(struct Circle*)s;
        b->center_.x+=x;
        b->center_.y+=y;
        break;
      default:
        assert(0);
        exit(0);
      }
    }
  }
  void drawShapes(Shape** shapes, int n){
    for (int i=0; i<n; ++i){
      struct Shape* s = shapes[i];
      switch (s->type_){
      case Shape::square:
        drawSquare((struct Square*)s);
        break;
      case Shape::circle:
        drawCircle((struct Circle*)s);
        break;
      case Shape::rhomb:
        drawRhomb((struct Rhomb*)s);
        break;
      default:
        assert(0);
        exit(0);
      }
    }
  }
  int main(){
    Shape* shapes[4];
    shapes[0]=(Shape*)new Circle;
    shapes[0]->type_=Shape::circle;

    struct Circle* c=(struct Circle*)shapes[0];
    std::cout<<"Old value of x:"<<c->center_.x<<"\n";
    std::cout<<"Old value of y:"<<c->center_.y<<"\n";
    moveShapes(shapes, 1, 7, 5);
    std::cout<<"New value of x:"<<c->center_.x<<"\n";
    std::cout<<"New value of y:"<<c->center_.y<<"\n";

    shapes[1]=(Shape*)new Square;
    shapes[1]->type_=Shape::square;
    shapes[2]=(Shape*)new Square;
    shapes[2]->type_=Shape::square;
    shapes[3]=(Shape*)new Circle;
    shapes[3]->type_=Shape::circle;
    shapes[4]=(Shape*)new Rhomb;
    shapes[4]->type_=Shape::rhomb;

    drawShapes(shapes, 5);
  }
