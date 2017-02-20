package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.OrderPayModel;
import org.ehais.epublic.model.paybill.PayBillRequest;
import org.ehais.tools.ReturnObject;

public interface PayBillService {

	public ReturnObject<PayBillRequest> PayBillRequestForm(
			HttpServletRequest request,
			PayBillRequest model,
			OrderPayModel orderPayModel) throws Exception;
}
