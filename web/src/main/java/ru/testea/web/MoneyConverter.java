package ru.testea.web;

import com.google.common.base.Strings;
import org.joda.money.Money;
import ru.testea.api.Constants;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.math.BigDecimal;

/**
 * Faces converter for {@link Money}.
 *
 * @author Vadim Kuznetskikh
 */
@FacesConverter("moneyConverter")
public class MoneyConverter
implements Converter
{
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
            return Money.of(Constants.CURRENCY, new BigDecimal(value));
        }
        catch (NumberFormatException e)
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
        return o == null ? "" : ((Money) o).getAmount().toString();
    }
}
