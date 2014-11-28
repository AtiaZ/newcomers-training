package org.exoplatform.support;

/**
 * Created by eXo Platform MEA on 25/11/14.
 *
 * @author <a href="mailto:marwen.trabelsi.insat@gmail.com">Marwen Trabelsi</a>
 */

import org.exoplatform.container.ExoContainerContext;

import javax.portlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsPortlet extends GenericPortlet
{

    private static final String INITIAL_VIEW = "/view.jsp";

    private PortletRequestDispatcher initialView;

    private EventRepoService repoService;

    private EventCRUDService eventCRUDService;

    List eventlist;


    public void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException
    {
       eventlist=eventCRUDService.list();
       request.setAttribute("eventList",eventlist);
        initialView.include(request, response);
    }


    public void init(PortletConfig config) throws PortletException
    {
        super.init(config);
//        repoService = new EventRepoService();
        eventCRUDService = ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(EventCRUDService.class);
        initialView = config.getPortletContext().getRequestDispatcher(INITIAL_VIEW);
    }



    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {

        String name = request.getParameter("event-name");
        String date = request.getParameter("event-date");

        eventCRUDService.create(name, date);

        response.setPortletMode(PortletMode.VIEW);
    }

    public void destroy()
    {
        super.destroy();
    }

}
