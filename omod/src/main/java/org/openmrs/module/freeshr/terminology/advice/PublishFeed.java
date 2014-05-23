package org.openmrs.module.freeshr.terminology.advice;

import org.apache.commons.beanutils.PropertyUtils;
import org.ict4h.atomfeed.server.repository.jdbc.AllEventRecordsJdbcImpl;
import org.ict4h.atomfeed.server.service.Event;
import org.ict4h.atomfeed.server.service.EventService;
import org.ict4h.atomfeed.server.service.EventServiceImpl;
import org.ict4h.atomfeed.transaction.AFTransactionWorkWithoutResult;
import org.joda.time.DateTime;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.atomfeed.transaction.support.AtomFeedSpringTransactionManager;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

public class PublishFeed implements AfterReturningAdvice {

    private static final String UPDATE_METHOD = "updateConcept";
    private static final String CREATE_METHOD = "createConcept";

    private static final String CONCEPT_REST_URL = "/openmrs/ws/rest/v1/concept/%s?v=full";
    private static final String TITLE = "Concept";
    private static final String CATEGORY = "Concept";

    private AtomFeedSpringTransactionManager atomFeedSpringTransactionManager;
    private EventService eventService;

    public PublishFeed() {
        atomFeedSpringTransactionManager = createTransactionManager();
        this.eventService = createService(atomFeedSpringTransactionManager);
    }

    private AtomFeedSpringTransactionManager createTransactionManager() {
        PlatformTransactionManager platformTransactionManager = getSpringPlatformTransactionManager();
        return new AtomFeedSpringTransactionManager(platformTransactionManager);
    }

    private EventServiceImpl createService(AtomFeedSpringTransactionManager atomFeedSpringTransactionManager) {
        AllEventRecordsJdbcImpl records = new AllEventRecordsJdbcImpl(atomFeedSpringTransactionManager);
        return new EventServiceImpl(records);
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] arguments, Object conceptService) throws Throwable {
        if (UPDATE_METHOD.equals(method.getName()) || CREATE_METHOD.equals(method.getName())) {
            Concept concept = (Concept) arguments[0];
            Object encounterUuid = PropertyUtils.getProperty(concept, "getConceptId");
            String url = String.format(CONCEPT_REST_URL, encounterUuid);
            final Event event = new Event(UUID.randomUUID().toString(), TITLE, DateTime.now(), url, "", CATEGORY);

            atomFeedSpringTransactionManager.executeWithTransaction(
                    new AFTransactionWorkWithoutResult() {
                        @Override
                        protected void doInTransaction() {
                            eventService.notify(event);
                        }

                        @Override
                        public PropagationDefinition getTxPropagationDefinition() {
                            return PropagationDefinition.PROPAGATION_REQUIRED;
                        }
                    }
            );
        }
    }

    private PlatformTransactionManager getSpringPlatformTransactionManager() {
        List<PlatformTransactionManager> platformTransactionManagers = Context.getRegisteredComponents(PlatformTransactionManager.class);
        return platformTransactionManagers.get(0);
    }
}
