package br.com.jera.gasosa;

import android.text.TextUtils;

public class Calculator {

	private double gasolinePrice;
	private double ethanolPrice;

	private final static double FACTOR = 70;

	public enum Fuel {

		GASOLINE("Gasolina"), ETHANOL("Etanol");

		private String name;

		Fuel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public Fuel evaluatePrice() {

		double finalValue = gasolinePrice / 100 * FACTOR;
		if (finalValue <= ethanolPrice) {
			return Fuel.GASOLINE;
		} else {
			return Fuel.ETHANOL;
		}
	}
	 public double ratio(){
		 return (ethanolPrice/gasolinePrice)*100;
	 }

	public void setGasolinePriceFromText(String text) {
		this.gasolinePrice = parsePrice(text);
	}

	public void setEthanolPriceFromText(String text) {
		this.ethanolPrice = parsePrice(text);
	}

	private double parsePrice(String text) {
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public double getGasolinePrice() {
		return gasolinePrice;
	}

	public double getEthanolPrice() {
		return ethanolPrice;
	}

}