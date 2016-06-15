package agiw.ner.alchemyapi;

import com.likethecolor.alchemy.api.Client;

import utils.PropertyFactor;

public class AlchemyAPIBalance {
	private Client client;

	public AlchemyAPIBalance() {
		this.client = new Client();
		this.initialize();
	}

	public static void main(String[] args) throws Exception {
		AlchemyAPIBalance aat = new AlchemyAPIBalance();
		aat.printRemainingBalance();
	}

	public void initialize() {
		PropertyFactor pf = new PropertyFactor();
		String apiKey = pf.getAlchemyApiKey();
		client.setAPIKey(apiKey);
	}

	public void printRemainingBalance() {

	}

}
