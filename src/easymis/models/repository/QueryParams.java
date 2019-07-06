package easymis.models.repository;

import java.sql.Date;

/**
 *
 * @author RashidKP
 */
public class QueryParams {
    private String paramName;
    private Object paramValue;
    private Date paramDateValue;
    
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }

    public Date getParamDateValue() {
        return paramDateValue;
    }

    public void setParamDateValue(Date paramDateValue) {
        this.paramDateValue = paramDateValue;
    }    
}
