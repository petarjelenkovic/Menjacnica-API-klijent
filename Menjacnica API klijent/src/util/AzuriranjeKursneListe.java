package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;







import rates.JsonRatesAPIKomunikacija;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import domen.Valuta;

public class AzuriranjeKursneListe {
	
	final String putanjaDoFajlaKursnaLista = "data/kursnaLista.json";
	
	public LinkedList<Valuta> ucitajValute(){
		
		try {
			FileReader reader = new FileReader(putanjaDoFajlaKursnaLista);
			Gson gson = new GsonBuilder().create();
			JsonObject jobj = gson.fromJson(reader, JsonObject.class);
			JsonArray jarr = jobj.get("valute").getAsJsonArray();
			return parseValute(jarr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	public LinkedList<Valuta> parseValute(JsonArray valuteJA) {
		LinkedList<Valuta> valute = new LinkedList<Valuta>();
		
		for (int i = 0; i < valuteJA.size(); i++) {
			JsonObject valutaJ = (JsonObject) valuteJA.get(i);
			
			Valuta v = new Valuta();
			v.setNaziv(valutaJ.get("naziv").getAsString());
			v.setKurs(valutaJ.get("kurs").getAsDouble());
			
			valute.add(v);
		}
		
		return valute;
	}
	
	public void upisiValute(LinkedList<Valuta> vl, GregorianCalendar greg){
		
		try {
			Gson gson = new GsonBuilder().create();
			FileWriter fw = new FileWriter(putanjaDoFajlaKursnaLista);
			fw.write(gson.toJson(spremiObject(vl, greg)));
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JsonObject spremiObject(LinkedList<Valuta> vl, GregorianCalendar greg){
		String dat = ""+greg.get(GregorianCalendar.DAY_OF_MONTH)+"."
			    +(greg.get(GregorianCalendar.MONTH)+1)+"."+
			    greg.get(GregorianCalendar.YEAR)+".";
	JsonObject kurseviJson = new JsonObject();
	kurseviJson.addProperty("datum", dat);
	JsonArray jarr = new JsonArray();
	for(int i=0;i<vl.size();i++){
		JsonObject job = new JsonObject();
		job.addProperty("naziv", vl.get(i).getNaziv());
		job.addProperty("kurs", Double.toString(vl.get(i).getKurs()));
		jarr.add(job);
	}
	kurseviJson.add("valute", jarr);
	return kurseviJson;
	}
	
	public void azurirajValute(){
		LinkedList<Valuta> val = ucitajValute();
		String[] str = new String[168];
		for (int i = 0; i < val.size(); i++) {
			str[i] = val.get(i).getNaziv();
		}
		val = JsonRatesAPIKomunikacija.vratiIznosKurseva(str);
		upisiValute(val, new GregorianCalendar());
	}

}
