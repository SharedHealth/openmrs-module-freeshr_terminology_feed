package org.openmrs.module.freeshr.terminology.advice;

import org.ict4h.atomfeed.server.repository.jdbc.AllEventRecordsJdbcImpl;
import org.ict4h.atomfeed.server.service.Event;
import org.ict4h.atomfeed.server.service.EventService;
import org.ict4h.atomfeed.server.service.EventServiceImpl;
import org.ict4h.atomfeed.transaction.AFTransactionWorkWithoutResult;
import org.openmrs.api.context.Context;
import org.openmrs.module.atomfeed.transaction.support.AtomFeedSpringTransactionManager;
import org.openmrs.module.freeshr.terminology.model.Operation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Method;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

public class TREventInterceptor implements AfterReturningAdvice {

    private AtomFeedSpringTransactionManager atomFeedSpringTransactionManager;
    private EventService eventService;

    public TREventInterceptor() {
        atomFeedSpringTransactionManager = createTransactionManager();
        this.eventService = createService(atomFeedSpringTransactionManager);
    }

    public TREventInterceptor(AtomFeedSpringTransactionManager atomFeedSpringTransactionManager, EventService eventService) {
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
        Operation operation = new Operation(method);
        final List<Event> events = operation.apply(arguments);
        if (isNotEmpty(events)) {
            atomFeedSpringTransactionManager.executeWithTransaction(
                    new AFTransactionWorkWithoutResult() {
                        @Override
                        protected void doInTransaction() {
                            for (Event event : events) {
                                eventService.notify(event);
                            }
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
