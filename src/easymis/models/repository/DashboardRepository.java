/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easymis.models.repository;

import easymis.models.entity.EventDetails;
import easymis.models.entity.utils.QueryConstants;
import easymis.utils.DateHelper;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author RashidKP
 */
public class DashboardRepository extends AbstractRepository{
    private static DashboardRepository uniqueInstance = new DashboardRepository();

    private DashboardRepository() {
    }

    public static DashboardRepository getUniqueInstance() {
        return uniqueInstance;
    }
    
    public List<EventDetails> fetchAllEventOfCurrentYear(){
         QueryParams param = new QueryParams();
        param.setParamName("fistDateOfYear");
        param.setParamDateValue(DateHelper.getFirstDayOfTheYear());
        return retrieve(QueryConstants.FETCH_ALL_EVENTS_OF_CURRENT_YEAR, Collections.singletonList(param), EventDetails.class);
    }
    
}
