package org.openmrs.module.freeshr.terminology.advice;

import org.ict4h.atomfeed.server.repository.jdbc.AllEventRecordsJdbcImpl;
import org.ict4h.atomfeed.server.service.Event;
import org.ict4h.atomfeed.server.service.EventService;
import org.ict4h.atomfeed.server.service.EventServiceImpl;
import org.ict4h.atomfeed.transaction.AFTransactionWorkWithoutResult;
import org.openmrs.api.context.Context;
import org.openmrs.module.atomfeed.transaction.support.AtomFeedSpringTransactionManager;
import org.openmrs.module.freeshr.terminology.model.ConceptOperation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Method;
import java.util.List;

public class ConceptServiceEventInterceptor implements AfterReturningAdvice {

    private static final String UPDATE_METHOD = "updateConcept";
    private static final String SAVE_METHOD = "saveConcept";

    private AtomFeedSpringTransactionManager atomFeedSpringTransactionManager;
    private EventService eventService;

    public ConceptServiceEventInterceptor() {
        atomFeedSpringTransactionManager = createTransactionManager();
        this.eventService = createService(atomFeedSpringTransactionManager);
    }

    public ConceptServiceEventInterceptor(AtomFeedSpringTransactionManager atomFeedSpringTransactionManager, EventService eventService) {
        this.atomFeedSpringTransactionManager = atomFeedSpringTransactionManager;
        this.eventService = eventService;
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
        ConceptOperation operation = new ConceptOperation(method);
        if (operation.shouldPublishEventToFeed()) {
            final Event event = operation.asEvent(arguments);
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
