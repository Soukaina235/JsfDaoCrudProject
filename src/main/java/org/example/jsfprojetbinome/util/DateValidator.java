package org.example.jsfprojetbinome.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@FacesValidator(value = "dateValidator")
public class DateValidator implements Validator{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {

        ResourceBundle bundle = ResourceBundle.getBundle("i18n.labels");

        if (!(value instanceof Date)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid date format", null));
        }

        Date dateValue = (Date) value;
        String dateString = DATE_FORMAT.format(dateValue);
        if (!dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            String message = MessageFormat.format(bundle.getString("dateValidatorMessage"), dateString);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        }
    }

}

