package info.ryusukeblog.myblogapi.controller.validator;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ArticleControllerValidator {

    private final MessageSource messageSource;

    public ArticleControllerValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void validateRequestFields(Class<?> clazz, List<String> fields) throws ResponseStatusException {
        if (fields.isEmpty()) {
            return;
        }
        List<String> classPropertyNames = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            classPropertyNames.add(field.getName());
        }
        if (classPropertyNames.containsAll(fields)) {
            return;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageSource.getMessage("ERROR.FIELDS_ARE_WRONG", null, Locale.JAPAN));
    }
}
