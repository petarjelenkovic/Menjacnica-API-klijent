package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.LinkedList;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import domen.Valuta;
import util.AzuriranjeKursneListe;

public class Main {

	public static void main(String[] args) {
		AzuriranjeKursneListe azu = new AzuriranjeKursneListe();
		azu.azurirajValute();
	}

}
