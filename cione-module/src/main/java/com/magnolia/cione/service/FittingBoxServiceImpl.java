package com.magnolia.cione.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.dto.FittingBoxDTO;
import com.magnolia.cione.setup.CioneEcommerceConectionProvider;

public class FittingBoxServiceImpl implements FittingBoxService {
	
	private static final Logger log = LoggerFactory.getLogger(FittingBoxServiceImpl.class);
	
	@Inject
	private ConfigService configService;
	
	@Override
	public Map<String, Boolean> isInFittingBox(List<String> skuList) {
		
		Map<String, Boolean> res = new HashMap<>();
		//comentada llamada al servicio hasta que se valide que lo quitamos
		/*ResteasyWebTarget target = null;
		try {
			
			//String apiKey = "9BjDfZvHu0VOJ6FHASzVSRmObGfFDquQ2NMjiED8"; //configService.getConfig().getApiFittingBoxApiKey();
			String apiKey = configService.getConfig().getApiFittingBoxApiKey();
			String uidList = StringUtils.join(skuList, ",");
			String apiUrl = configService.getConfig().getApiFittingBoxPath() + "/glasses-metadata/availability";
			
			/*ResteasyWebTarget target = CioneEcommerceConectionProvider.restClientFit
					.target(configService.getConfig().getApiFittingBoxPath() + 
							"/glasses-metadata/availability?apiKey=" + apiKey +
							"&uidList=" + uidList);/
			
			target = CioneEcommerceConectionProvider.restClientFit.target(apiUrl)
                    .queryParam("apiKey", apiKey)
                    .queryParam("uidList", uidList);
			
			List<FittingBoxDTO> metadataList = target.request().get(new GenericType<List<FittingBoxDTO>>() {});
			
			for (FittingBoxDTO dataFittingBox: metadataList) {
				res.put(dataFittingBox.getUid(), dataFittingBox.isAvailable());
			}

		
		} catch (Exception e) {
			if (target != null) 
				log.error("ERROR al consultar FittingBox. Timeout." + target.getUri().toString());
			else 
				log.error("ERROR al consultar FittingBox. Timeout.");
			//log.error(e.getMessage(), e);
		} */
		
		return res;
		
	}
	
}
