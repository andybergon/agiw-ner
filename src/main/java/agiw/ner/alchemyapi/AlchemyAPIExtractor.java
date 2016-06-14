package agiw.ner.alchemyapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringStyle;

import com.likethecolor.alchemy.api.Client;
import com.likethecolor.alchemy.api.call.AbstractCall;
import com.likethecolor.alchemy.api.call.RankedNamedEntitiesCall;
import com.likethecolor.alchemy.api.call.type.CallTypeText;
import com.likethecolor.alchemy.api.call.type.CallTypeUrl;
import com.likethecolor.alchemy.api.entity.NamedEntityAlchemyEntity;
import com.likethecolor.alchemy.api.entity.Response;

import agiw.ner.alchemyapi.exception.AlchemyException;
import agiw.ner.objects.NamedEntity;
import utils.PropertyFactor;

public class AlchemyAPIExtractor {
	private Client client;

	public AlchemyAPIExtractor() {
		this.client = new Client();
		this.initialize();
	}

	public static void main(String[] args) throws Exception {
		String url = "http://www.lastampa.it/2015/03/20/cronaca/anna-abbagnale-fuori-pericolo-tornano-i-trenta-torinesi-dalla-tunisia-NldddrrapGzIdLUz7N2ABL/pagina.html";

		AlchemyAPIExtractor aat = new AlchemyAPIExtractor();
		aat.printEntitiesFromUrl(url);
		aat.getEntitiesFromUrl(url);

	}

	public void initialize() {
		PropertyFactor pf = new PropertyFactor();
		String apiKey = pf.getAlchemyApiKey();
		client.setAPIKey(apiKey);
	}

	public void printEntitiesFromUrl(String url) {
		final AbstractCall<NamedEntityAlchemyEntity> entityCall = new RankedNamedEntitiesCall(new CallTypeUrl(url));

		try {
			Response<NamedEntityAlchemyEntity> response = this.client.call(entityCall);

			System.out.println("Language: " + response.getLanguage());
			System.out.println("Status: " + response.getStatus());
			System.out.println("Status Info: " + response.getStatusInfo());
			System.out.println("Text: " + response.getText());
			System.out.println("Usage: " + response.getUsage());
			System.out.println("URL: " + response.getURL());

			String responseString = response.toString(ToStringStyle.MULTI_LINE_STYLE);
			System.out.println(responseString);

			NamedEntityAlchemyEntity entity;
			final Iterator<NamedEntityAlchemyEntity> iter = response.iterator();

			while (iter.hasNext()) {
				entity = iter.next();
				System.out.println("Text: " + entity.getText());
				System.out.println("Type: " + entity.getType());
				System.out.println("Score: " + entity.getScore());

				System.out.println(entity.toString(ToStringStyle.MULTI_LINE_STYLE));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<NamedEntity> getEntitiesFromUrl(String url) throws AlchemyException {
		final AbstractCall<NamedEntityAlchemyEntity> entityCall = new RankedNamedEntitiesCall(new CallTypeUrl(url));
		List<NamedEntity> entities = new ArrayList<NamedEntity>();

		try {
			Response<NamedEntityAlchemyEntity> response;
			NamedEntityAlchemyEntity alchemyEntity;
			NamedEntity namedEntity;
			String entityName;
			String entityType;

			response = this.client.call(entityCall);

			final Iterator<NamedEntityAlchemyEntity> iter = response.iterator();

			while (iter.hasNext()) {
				alchemyEntity = iter.next();

				//				DisambiguatedAlchemyEntity disambiguatedEntity = alchemyEntity.getDisambiguatedAlchemyEntity(); //TODO: check
				//				disambiguatedEntity.getSubtypeSize(); // serve al for per iterare

				entityName = alchemyEntity.getText();
				entityType = alchemyEntity.getType(); // main entity type
				namedEntity = new NamedEntity(entityName, entityType);
				entities.add(namedEntity);
			}

		} catch (IOException e) {
			//e.printStackTrace();
			System.err.println("Cannot call AlchemyAPI on this URL.");
			throw new AlchemyException();
		}

		return entities;
	}

	public List<NamedEntity> getEntitiesFromText(String body) {
		List<NamedEntity> entities = new ArrayList<NamedEntity>();

		try {
			Response<NamedEntityAlchemyEntity> response;
			NamedEntityAlchemyEntity alchemyEntity;
			NamedEntity namedEntity;
			String entityName;
			String entityType;

			final AbstractCall<NamedEntityAlchemyEntity> entityCall = new RankedNamedEntitiesCall(
					new CallTypeText(body));

			response = this.client.call(entityCall);

			final Iterator<NamedEntityAlchemyEntity> iter = response.iterator();

			while (iter.hasNext()) {
				alchemyEntity = iter.next();

				entityName = alchemyEntity.getText();
				entityType = alchemyEntity.getType(); // main entity type
				namedEntity = new NamedEntity(entityName, entityType);

				entities.add(namedEntity);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("Body cannot be null or less than 1 character. Setting null NER from Alchemy!");
			
			if (body == null) {
				System.err.println("Body NULL!");
			} else {
				System.err.println("Body: " + body);
			}

			e.printStackTrace();
		}

		return entities;

	}

}
