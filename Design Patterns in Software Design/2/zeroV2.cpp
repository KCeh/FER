#include <iostream>
#include <STDLIB.H>
#include <assert.h>
#include <list>

  struct Point{
    int x; int y;
  };

  class Shape{
    public:
    Point center_;
    virtual void draw()=0;
    virtual void moveShape(int x, int y)=0;
  };

  class Circle:public Shape{
    public:
    double radius_;
    virtual void draw(){
        std::cerr <<"in drawCircle\n";
    }
    virtual void moveShape(int x, int y){
        center_.x+=x;
        center_.y+=y;
    }
    Circle(double radius){
        radius_=radius;
        center_.x=0;
        center_.y=0;
    }
  };

  class Square:public Shape{
    public:
    double side_;
    virtual void draw(){
        std::cerr <<"in drawSquare\n";
    }
    virtual void moveShape(int x, int y){
        center_.x+=x;
        center_.y+=y;
    }
    Square(double side){
        side_=side;
        center_.x=0;
        center_.y=0;
    }
  };

  class Rhomb:public Shape{
    public:
    double side_;
    virtual void draw(){
        std::cerr <<"in drawRhomb\n";
    }
    virtual void moveShape(int x, int y){
        center_.x+=x;
        center_.y+=y;
    }
    Rhomb(double side){
        side_=side;
        center_.x=0;
        center_.y=0;
    }
  };


  void drawShapes(const std::list<Shape*>& fig){
    std::list<Shape*>::const_iterator it;
    for(it=fig.begin();it!=fig.end();++it){
        (*it)->draw();
    }
  }

  void moveShapes(const std::list<Shape*>& fig,int x, int y){
    std::list<Shape*>::const_iterator it;
    for(it=fig.begin();it!=fig.end();++it){
        (*it)->moveShape(x,y);
    }
  }

  int main(){
    std::list<Shape*> fig;
    Shape* s1= new Circle(3.5);
    Shape* s2= new Square(2.1);
    Shape* s3= new Square(2.8);
    Shape* s4= new Circle(3.6);
    Shape* s5= new Rhomb(3.3);
    fig.push_back(s1);
    fig.push_back(s2);
    fig.push_back(s3);
    fig.push_back(s4);
    fig.push_back(s5);
    drawShapes(fig);
    std::cout<<"Old value of x for s2:"<<s2->center_.x<<"\n";
    std::cout<<"Old value of y for s2:"<<s2->center_.y<<"\n";
    moveShapes(fig, 5, 12);
    std::cout<<"New value of x for s2:"<<s2->center_.x<<"\n";
    std::cout<<"New value of y for s2:"<<s2->center_.y<<"\n";
  }

