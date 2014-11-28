package org.exoplatform.support;

import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

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
            e.printStackTrace(); //TODO
        }
    }

    private Session initSession() {
        RepositoryService repositoryService = ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(RepositoryService.class);
        SessionProvider sessionProvider = ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(SessionProvider.class);
        ManageableRepository manageableRepository = null;
        try {
            manageableRepository = repositoryService.getCurrentRepository();
//            manageableRepository = repositoryService.getRepository("repository");
        } catch (RepositoryException e) {
            LOG.error("An error occurred while retrieving the repository ", e);
        }

        Session session = null;
        try {
            session = sessionProvider.getSession(WORKSPACE, manageableRepository);
        } catch (RepositoryException e) {
            LOG.error("An error occurred while retrieving the repository ", e);
        }
        return session;
    }

}
