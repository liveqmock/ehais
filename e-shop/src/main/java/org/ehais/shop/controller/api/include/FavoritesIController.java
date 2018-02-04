package org.ehais.shop.controller.api.include;

import org.ehais.controller.CommonController;
import org.ehais.shop.service.FavoritesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FavoritesIController extends CommonController{
	protected static Logger log = LoggerFactory.getLogger(FavoritesIController.class);

	@Autowired
	protected FavoritesService favoritesService;
	
	
	
}
