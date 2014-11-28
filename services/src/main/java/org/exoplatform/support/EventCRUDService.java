package org.exoplatform.support;

import java.util.List;

/**
 * Created by exo on 11/28/14.
 */
public interface EventCRUDService {

    String WORKSPACE = "collaboration";
    String EVENT_HOME = "eventHome";
    String EVENT_NODE_TYPE = "exo:event";
    String EVENT_NAME_PROP = "exo:eventName";
    String EVENT_DATE_PROP = "exo:eventDate";

    void create(String name, String date);
    List<EventBean> list();

}
