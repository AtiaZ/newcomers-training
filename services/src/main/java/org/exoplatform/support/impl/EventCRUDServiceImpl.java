package org.exoplatform.support.impl;

import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.PropertiesParam;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.support.EventBean;
import org.exoplatform.support.EventCRUDService;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by exo on 11/28/14.
 */
public class EventCRUDServiceImpl implements EventCRUDService {

    private RepositoryService repositoryService;
    String eventHomePath;

    public EventCRUDServiceImpl(RepositoryService repositoryService, InitParams initParams) {
        this.repositoryService = repositoryService;
        PropertiesParam propertiesParam = initParams.getPropertiesParam("event.service.config");
        if (!(propertiesParam == null))
        {
            eventHomePath = propertiesParam.getProperty("eventHomePath");
        }
    }

    @Override
    public void create(String name, String date) {
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
//            LOG.error("An error occurred while opening the session ", e);
        }
    }

    @Override
    public List<EventBean> list() {
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
//            LOG.error("An error occurred while opening the session ", e);
        }

        return events;
    }

    private Session initSession() {
        ManageableRepository manageableRepository = null;
        Session session = null;
        try {
            manageableRepository = repositoryService.getCurrentRepository();
//            manageableRepository = repositoryService.getRepository("repository");

            session = manageableRepository.getSystemSession(WORKSPACE);
        } catch (RepositoryException e) {
//            LOG.error("An error occurred while retrieving the repository ", e);
        }
        return session;
    }
}
