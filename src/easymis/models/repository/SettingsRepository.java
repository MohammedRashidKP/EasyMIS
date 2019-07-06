package easymis.models.repository;

import easymis.models.entity.Settings;
import easymis.models.entity.TransactionStatus;
import easymis.models.utils.QueryConstants;
import easymis.models.utils.SettingsConstants;
import easymis.utils.StringUtils;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class SettingsRepository extends AbstractRepository{
    
    private static SettingsRepository uniqueInstance = new SettingsRepository();

    private SettingsRepository() {
    }

    public static SettingsRepository getUniqueInstance() {
        return uniqueInstance;
    }

    
    public TransactionStatus update(List<Settings> settings) {
        
        TransactionStatus status = null;
            try {
                for(Settings setting: settings){
                   status = merge(setting);
                }
                return status;
            } catch (Exception ex) {
                status = fillTransactionStatus(ex);
            }
        return status;
    }
    
    public List<Settings> getAllSettings(){
        return retrieve(QueryConstants.FETCH_ALL_SETTINGS, null, Settings.class);
    }
    
    public List<Settings> getSettingsValues(List<String> attributes){
        QueryParams param = new QueryParams();
        param.setParamName("attributes");
        param.setParamValue(attributes);
        return retrieve(QueryConstants.FETCH_MULTIPLE_SETTINGS_FOR_ATTRIBUTES, Collections.singletonList(param), Settings.class);
    }
    
    public String getUserPin(){
        QueryParams param = new QueryParams();
        param.setParamName("attributes");
        param.setParamValue(SettingsConstants.PIN);
        List<Settings> settings = retrieve(QueryConstants.FETCH_SETTINGS_FOR_ATTRIBUTES, Collections.singletonList(param), Settings.class);
        return settings != null && !settings.isEmpty() ? settings.get(0).getAttributeValue() : null;
    }
    
    public String getAdminPin(){
        QueryParams param = new QueryParams();
        param.setParamName("attributes");
        param.setParamValue(SettingsConstants.ADMIN_PIN);
        List<Settings> settings = retrieve(QueryConstants.FETCH_SETTINGS_FOR_ATTRIBUTES, Collections.singletonList(param), Settings.class);
        return settings != null && !settings.isEmpty() ? settings.get(0).getAttributeValue() : null;
    }
    
    public String getPinNmber(){
        String pin = getUserPin();
        if(!StringUtils.isNotNullCheckSpace(pin)){
            pin = getAdminPin();
        } 
        return pin;
    }
}
