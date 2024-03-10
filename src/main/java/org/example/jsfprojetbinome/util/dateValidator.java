package org.example.jsfprojetbinome.util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FacesValidator(value = "dateValidator")
public class dateValidator implements Validator{
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.labels");

        String componentValue = value.toString();
        pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        matcher = pattern.matcher(componentValue);
        if (!matcher.find()) {
            String message = MessageFormat.format(bundle.getString("dateValidatorMessage"), componentValue);
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message,
                    message);

            System.out.println(bundle.getString("dateValidatorMessage"));

            throw new ValidatorException(facesMessage);
        }
    }

}

