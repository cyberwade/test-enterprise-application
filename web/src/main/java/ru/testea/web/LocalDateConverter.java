package ru.testea.web;

import com.google.common.base.Strings;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 * Faces converter for {@link LocalDate}.
 *
 * @author Vadim Kuznetskikh
 */
@FacesConverter("localDateConverter")
public class LocalDateConverter
extends DateTimeConverter
{
    public static final String DATE_PATTERN = "dd.MM.yyyy";

    /**
     * Creates new converter.
     */
    public LocalDateConverter()
    {
    }

    @Override
    public Object getAsObject(
        FacesContext facesContext,
        UIComponent uiComponent,
        String s)
    {
        String value = Strings.nullToEmpty(s).trim();
        if (value.isEmpty())
        {
            return null;
        }

        try
        {
            return LocalDate.parse(value,
                DateTimeFormat.forPattern(DATE_PATTERN));
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }

    @Override
    public String getAsString(
        FacesContext facesContext,
        UIComponent uiComponent,
        Object o)
    {
        return o == null ? "" : ((LocalDate) o).toString(DATE_PATTERN);
    }
}
