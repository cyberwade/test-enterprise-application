package ru.testea.web;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 * Faces converter for {@link DateTime}.
 *
 * @author Vadim Kuznetskikh
 */
@FacesConverter("dateTimeConverter")
public class DateTimeConverter
extends javax.faces.convert.DateTimeConverter
{
    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";

    /**
     * Creates new converter.
     */
    public DateTimeConverter()
    {
    }

    @Override
    public Object getAsObject(
        FacesContext facesContext,
        UIComponent uiComponent,
        String s)
    {
        String value = Strings.nullToEmpty(s).trim();
        if(value.isEmpty())
        {
            return null;
        }

        try
        {
            return DateTime.parse(value,
                DateTimeFormat.forPattern(DATE_TIME_PATTERN));
        }
        catch(IllegalArgumentException e)
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
        return o == null ? "" : ((DateTime) o).toString(DATE_TIME_PATTERN);
    }
}
