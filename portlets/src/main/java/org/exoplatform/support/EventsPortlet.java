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

    List eventlist;


    public void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException
    {
       eventlist=repoService.getAllEvents();
       request.setAttribute("eventList",eventlist);
        initialView.include(request, response);
    }


    public void init(PortletConfig config) throws PortletException
    {
        super.init(config);
        repoService = new EventRepoService();
        initialView = config.getPortletContext().getRequestDispatcher(INITIAL_VIEW);
    }



    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {

        String name = request.getParameter("event-name");
        String date = request.getParameter("event-date");


        repoService.createEvent(name, date);

        response.setPortletMode(PortletMode.VIEW);
    }

    public void destroy()
    {
        super.destroy();
    }

}
