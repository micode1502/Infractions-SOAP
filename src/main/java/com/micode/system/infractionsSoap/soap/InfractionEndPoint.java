package com.micode.system.infractionsSoap.soap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.micode.system.infractionsSoap.entity.Infraction;
import com.micode.system.infractionsSoap.service.InfractionService;
import com.micodee.infractions_soap.DeleteInfractionRequest;
import com.micodee.infractions_soap.DeleteInfractionResponse;
import com.micodee.infractions_soap.GetAllInfractionsRequest;
import com.micodee.infractions_soap.GetAllInfractionsResponse;
import com.micodee.infractions_soap.InfractionDetail;
import com.micodee.infractions_soap.InfractionShowDetail;
import com.micodee.infractions_soap.SaveInfractionRequest;
import com.micodee.infractions_soap.SaveInfractionResponse;
import com.micodee.infractions_soap.UpdateInfractionRequest;
import com.micodee.infractions_soap.UpdateInfractionResponse;

@Endpoint
public class InfractionEndPoint {
	@Autowired
	InfractionService service;
	
	@PayloadRoot(namespace = "http://www.micodee.com/infractions-soap", localPart = "GetAllInfractionsRequest")
	@ResponsePayload
	public GetAllInfractionsResponse findAll(@RequestPayload GetAllInfractionsRequest request) {
		GetAllInfractionsResponse response = new GetAllInfractionsResponse();
	    Pageable page = PageRequest.of(request.getOffset(), request.getLimit());
	    List<Infraction> infractions = service.findAll(page);
	    List<InfractionShowDetail> infractionsResponse = new ArrayList<>();
	    for (Infraction infraction : infractions) {
	    	InfractionShowDetail infractionDetail = new InfractionShowDetail();
	        BeanUtils.copyProperties(infraction, infractionDetail);
	        infractionsResponse.add(infractionDetail);
	    }
	    if (infractionsResponse.isEmpty()) {
	        response.setMessage("No se encontraron infracciones registrados.");
	    } else {
	        response.getInfractionShowDetail().addAll(infractionsResponse);
	    }
	    return response;
	}
	
	@PayloadRoot(namespace = "http://www.micodee.com/infractions-soap", localPart = "SaveInfractionRequest")
	@ResponsePayload
	public SaveInfractionResponse saveProduct(@RequestPayload SaveInfractionRequest request) {
		SaveInfractionResponse response = new SaveInfractionResponse();
	    try {
	        Infraction infraction = new Infraction();
	        BeanUtils.copyProperties(request.getInfractionDetail(), infraction);
	        service.create(infraction);

	        response.setMessage("Infraccion registrado exitosamente.");
	    } catch (RuntimeException e) {
	        response.setMessage(e.getMessage());
	    }
	    return response;
	}
	
	@PayloadRoot(namespace = "http://www.micodee.com/infractions-soap", localPart = "UpdateInfractionRequest")
    @ResponsePayload
    public UpdateInfractionResponse updateProduct(@RequestPayload UpdateInfractionRequest request) {
		UpdateInfractionResponse response = new UpdateInfractionResponse();
        try {
            Infraction infraction = new Infraction();
            BeanUtils.copyProperties(request.getInfractionStateDetail(), infraction);
            service.update(infraction);
            response.setMessage("Infraccion actualizada exitosamente.");
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
	
	@PayloadRoot(namespace = "http://www.micodee.com/infractions-soap", localPart = "DeleteInfractionRequest")
    @ResponsePayload
    public DeleteInfractionResponse deleteProduct(@RequestPayload DeleteInfractionRequest request) {
		DeleteInfractionResponse response = new DeleteInfractionResponse();
        try {
        	Integer infractionId = request.getInfractionDetail().getId();
            service.deleteById(infractionId);
            response.setMessage("Infraccion eliminada exitosamente.");
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
