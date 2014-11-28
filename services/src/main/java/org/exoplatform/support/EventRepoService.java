package org.exoplatform.support;

import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;


import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by exo on 11/27/14.
 */
public class EventRepoService {

    private static final Log LOG = ExoLogger.getExoLogger("org.exoplatform.support.EventRepoService");
    private static final String WORKSPACE = "collaboration";
    private static final String EVENT_HOME = "eventHome";
    private static final String EVENT_NODE_TYPE = "exo:event";
    private static final String EVENT_NAME_PROP = "exo:eventName";
    private static final String EVENT_DATE_PROP = "exo:eventDate";

    public void createEvent(String name, String date)
    {
        Session session = initSession();
        try {
            Node rootNode = session.getRootNode();
            Node eventHomeNode = null;

            if (rootNode.hasNode(EVENT_HOME))
            {
                eventHomeNode = rootNode.getNode(EVENT_HOME);
            }
            else
            {
                eventHomeNode = rootNode.addNode(EVENT_HOME);
            }

            Node eventNode = eventHomeNode.addNode(name, EVENT_NODE_TYPE);
            eventNode.setProperty(EVENT_NAME_PROP, name);
            eventNode.setProperty(EVENT_DATE_PROP, date);
            session.save();

        } catch (RepositoryException e) {
            LOG.error("An error occurred while opening the session ", e);
        }
    }

    public  List<EventBean> getAllEvents() {

        Session session = initSession();

        List<EventBean> events= new ArrayList();

        try {
            Node rootNode = session.getRootNode();
            Node eventHomeNode = null;

            if (rootNode.hasNode(EVENT_HOME)) {
                eventHomeNode = rootNode.getNode(EVENT_HOME);
            } else {
                eventHomeNode = rootNode.addNode(EVENT_HOME);
            }

            NodeIterator eventNodesIterator = eventHomeNode.getNodes();

            while (eventNodesIterator.hasNext())
            {
                Node node = eventNodesIterator.nextNode();
                String name = node.getProperty(EVENT_NAME_PROP).getString();
                String date = node.getProperty(EVENT_DATE_PROP).getString();
                EventBean eventBean = new EventBean(name, date);
                events.add(eventBean);
            }


        } catch (RepositoryException e) {
            LOG.error("An error occurred while opening the session ", e);
        }

        return events;
    }



    private Session initSession() {
        RepositoryService repositoryService = ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(RepositoryService.class);
        ManageableRepository manageableRepository = null;
        Session session = null;
        try {
            manageableRepository = repositoryService.getCurrentRepository();
//            manageableRepository = repositoryService.getRepository("repository");

            session = manageableRepository.getSystemSession(WORKSPACE);
        } catch (RepositoryException e) {
            LOG.error("An error occurred while retrieving the repository ", e);
        }
        return session;
    }

}
