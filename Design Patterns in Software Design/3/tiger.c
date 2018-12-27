#include <stdlib.h>

typedef char const* (*PTRFUN)();

struct Tiger {
	PTRFUN *vtable;
	char const* name;
};

char const *name(void *this) {
	return ((struct Tiger*)this)->name;
}

char const *greet() {
	return "Mijau!";
}

char const *menu() {
	return "mlako mlijeko";
}

PTRFUN VTable[3] = {
	(PTRFUN) name,
	(PTRFUN) greet,
	(PTRFUN) menu
};

void *create(char const *name) {
	struct Tiger *tiger = malloc(sizeof(struct Tiger));
	tiger->name = name;
	tiger->vtable = VTable;
	return tiger;
}
