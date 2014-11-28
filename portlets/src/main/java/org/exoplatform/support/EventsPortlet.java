package org.exoplatform.support;

/**
 * Created by eXo Platform MEA on 25/11/14.
 *
 * @author <a href="mailto:marwen.trabelsi.insat@gmail.com">Marwen Trabelsi</a>
 */

import javax.portlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsPortlet extends GenericPortlet
{

    private static final String INITIAL_VIEW = "/view.jsp";

    private PortletRequestDispatcher initialView;

    private EventRepoService repoService;

    List<EventBean> eventList = new ArrayList<EventBean>();

    public void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException
    {
        request.setAttribute("eventList",eventList);
        initialView.include(request, response);
    }


    public void init(PortletConfig config) throws PortletException
    {
        super.init(config);
        initialView = config.getPortletContext().getRequestDispatcher(INITIAL_VIEW);
    }



    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {

        String name = request.getParameter("event-name");
        String date = request.getParameter("event-date");
//        eventList.add(new EventBean(name,date));

        repoService = new EventRepoService();
        repoService.createEvent(name, date);



        /*PortletPreferences prefs=request.getPreferences();
        prefs.setValue("event-name",name);
        prefs.setValue("event-date",date);
        prefs.store();*/
        //request.getPortletSession().setAttribute(" eventList", eventList);

        response.setPortletMode(PortletMode.VIEW);
    }

    public void destroy()
    {
        super.destroy();
    }

}
