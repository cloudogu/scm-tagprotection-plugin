package sonia.scm.tagprotection;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sonia.scm.EagerSingleton;
import sonia.scm.event.Subscriber;
import sonia.scm.plugin.ext.Extension;
import sonia.scm.repository.PreReceiveRepositoryHookEvent;
import sonia.scm.repository.Repository;
import sonia.scm.repository.Tag;
import sonia.scm.user.User;

import java.util.List;

/**
 * @author Oliver Milke
 */
@Extension
@EagerSingleton
@Subscriber(async = false)
public class TagProtectionPreReceiveRepositoryHook {

    private static final Logger logger = LoggerFactory.getLogger(TagProtectionPreReceiveRepositoryHook.class);

    private final TagProtectionValidator tagProtectionValidator;

    @Inject
    public TagProtectionPreReceiveRepositoryHook(TagProtectionValidator tagProtectionValidator) {

        this.tagProtectionValidator = tagProtectionValidator;
    }

    @Subscribe
    public void onEvent(PreReceiveRepositoryHookEvent event) {

        Repository repository = event.getRepository();
        if (repository != null) {

            Subject subject = SecurityUtils.getSubject();
            List<Tag> deletedTags = event.getContext().getTagProvider().getDeletedTags();

            boolean tagsMustBeProtected = tagProtectionValidator.tagsMustBeProtected(repository, deletedTags);
            if (tagsMustBeProtected) {

                User user = subject.getPrincipals().oneByType(User.class);
                String message = String.format("Deleting tags not allowed for user %s in repository %s", user.getName(), repository.getName());
                logger.info(message);
                throw new TagProtectionException(message);
            } else {

                logger.trace("Tag Protection does not need to be enforced.");
            }
        } else {
            logger.warn("received hook without repository");

        }
    }

}
