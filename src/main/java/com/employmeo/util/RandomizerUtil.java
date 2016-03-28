package com.employmeo.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.employmeo.objects.Person;
import com.employmeo.objects.User;

public class RandomizerUtil {
	
	static Random rand = new Random();
	static List<String> fnames = Arrays.asList(
			"Ramon",
			"Jared",
			"Rosetta",
			"Kassie",
			"Charita",
			"Wilbert",
			"Emilee",
			"Tameka",
			"Wyatt",
			"Meredith",
			"Isaias",
			"Ada",
			"Kristan",
			"Bobby",
			"Keith",
			"Bao",
			"Elva",
			"Sharyl",
			"Season",
			"Leola",
			"Sherryl",
			"Bree",
			"Coletta",
			"Lucio",
			"Ivey",
			"Maxima",
			"Ophelia",
			"Justina",
			"Kasha",
			"Apryl",
			"Anna",
			"Deborah",
			"Clarisa",
			"Dorla",
			"Maxine",
			"Cindi",
			"Gita",
			"Marleen",
			"Henry",
			"Fidela",
			"Carlyn",
			"Carmella",
			"Dalene",
			"Maximo",
			"Trina",
			"Ezra",
			"Andre",
			"Yoshiko",
			"Candra",
			"Alan");
	static List<String> lnames = Arrays.asList(
			"Bryant",
			"Haynes",
			"Moreno",
			"Bowen",
			"Wilkinson",
			"Stein",
			"Liu",
			"Fischer",
			"Myers",
			"Galvan",
			"Pineda",
			"Mckenzie",
			"Andersen",
			"Harper",
			"Wise",
			"Cummings",
			"Pearson",
			"Case",
			"Ryan",
			"Fields",
			"Melendez",
			"Cannon",
			"Mosley",
			"Schneider",
			"Meadows",
			"Franklin",
			"Byrd",
			"Graham",
			"Werner",
			"Burgess",
			"Pennington",
			"Chapman",
			"Collins",
			"Vargas",
			"Washington",
			"Mcdowell",
			"Hughes",
			"Farmer",
			"Lara",
			"Mason",
			"Leon",
			"Fletcher",
			"Silva",
			"Diaz",
			"Bolton",
			"Gallagher",
			"Brandt",
			"Choi",
			"Flores",
			"Weaver");	
	static List<String> domains = Arrays.asList(
			"company.com",
			"gmail.com",
			"yahoo.com",
			"aol.com",
			"hotmail.com",
			"rocketmail.com",
			"facebook.com"
			);
	
	public RandomizerUtil() {}
	
	public static String randomFname() {
		int i = rand.nextInt(50);
		return fnames.get(i);
	}
	
	public static String randomLname() {
		int i = rand.nextInt(50);
		return lnames.get(i);
	}
	
	public static String randomEmail(User user) {
		int i = rand.nextInt(7);
		String email = user.getUserFname()+"_"+user.getUserLname()+"@"+domains.get(i);
		return email;
	}
	
	public static String randomEmail(Person person) {
		int i = rand.nextInt(7);
		String email = person.getPersonFname()+"_"+person.getPersonLname()+"@"+domains.get(i);
		return email;
	}

}