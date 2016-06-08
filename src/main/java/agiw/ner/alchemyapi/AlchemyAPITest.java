package agiw.ner.alchemyapi;

import java.io.IOException;

import org.apache.commons.lang.builder.ToStringStyle;

import com.likethecolor.alchemy.api.*;
import com.likethecolor.alchemy.api.Client;
import com.likethecolor.alchemy.api.call.AbstractCall;
import com.likethecolor.alchemy.api.call.AuthorCall;
import com.likethecolor.alchemy.api.call.LanguageCall;
import com.likethecolor.alchemy.api.call.type.CallTypeUrl;
import com.likethecolor.alchemy.api.entity.AlchemyEntity;
import com.likethecolor.alchemy.api.entity.LanguageAlchemyEntity;
import com.likethecolor.alchemy.api.entity.Response;

public class AlchemyAPITest {
	public static void main(String[] args) {
		final String apiKey = "d2f20d1fb2b74be0a0ab12a75dfe9b731981eaeb";
		final Client client = new Client();

		client.setAPIKey(apiKey);
		
		
		String urlString = "http://www.cnn.com/2009/CRIME/01/13/missing.pilot/index.html";
	    final AbstractCall<LanguageAlchemyEntity> languageCall = new LanguageCall(new CallTypeUrl(urlString));
	    
	    try {
	    	Response<LanguageAlchemyEntity> response = client.call(languageCall);
	    	String responseString = response.toString(ToStringStyle.MULTI_LINE_STYLE);
			System.out.println(responseString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//			    final AbstractCall<AuthorAlchemyEntity> authorCall = new AuthorCall(new CallTypeUrl("http://www.politico.com/blogs/media/2012/02/detroit-news-ed-upset-over-romney-edit-115247.html"));
//			    final Response response = client.call(authorCall);
		//	    System.out.println(response);
		//	    System.out.println();
	}
}
