package com.eisc.football;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator("com.eisc.football.FileValidator")
public class FileValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Part file = (Part) value;

		if (file == null) {
			msgs.add(new FacesMessage("Warning! Select file."));
		} else {

			if (!"text/plain".equals(file.getContentType()) && !"application/octet-stream".equals(file.getContentType())) {
				msgs.add(new FacesMessage("Warning! File selected is not a text file!"));
			}
		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
		
	}

}
