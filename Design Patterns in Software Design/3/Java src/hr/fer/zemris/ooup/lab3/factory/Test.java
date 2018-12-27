package hr.fer.zemris.ooup.lab3.factory;

import hr.fer.zemris.ooup.lab3.model.Animal;

public class Test {
	public static void main(String[] args) {
		try {
			Animal parrot = AnimalFactory.newInstance("Parrot", "Modrobradi");
			Animal tiger = AnimalFactory.newInstance("Tiger", "Strasko");
			parrot.animalPrintGreeting();
			parrot.animalPrintMenu();
			tiger.animalPrintGreeting();
			tiger.animalPrintMenu();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
