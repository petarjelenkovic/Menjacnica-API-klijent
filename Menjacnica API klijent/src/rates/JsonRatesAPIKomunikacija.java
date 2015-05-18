package rates;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import domen.Valuta;

public class JsonRatesAPIKomunikacija {
	
	public LinkedList<Valuta> vratiIznosKurseva(String[] valute){
		URL url;
		LinkedList<Valuta> val = new LinkedList<Valuta>();
		try {
			for(int i=0;i<valute.length;i++){
			String adr = "http://jsonrates.com/get/?from="+valute[i]+"&to=RSD&apiKey=jr-ba8999934fc5a7ab64a4872fb4ed9af7";	
			url = new URL(adr);
			String data = url.toString();
			FileReader reader = new FileReader(data); 
			Gson gson = new GsonBuilder().create();
			Valuta valuta = new Valuta();
			JsonObject kurs = gson.fromJson(reader, JsonObject.class);
			valuta.setNaziv(valute[i]);
			valuta.setKurs(kurs.get("rate").getAsDouble());
			val.add(valuta);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
		
	}

}
