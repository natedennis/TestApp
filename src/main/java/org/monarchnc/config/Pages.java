package org.monarchnc.config;
import org.jboss.seam.faces.event.PhaseIdType;
import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.security.RestrictAtPhase;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
import org.monarchnc.account.Authenticated;
import org.monarchnc.security.annotations.EhrUser;

/**
*
* @author Nathan Dennis
*
*/
@ViewConfig
public interface Pages {

    static enum Pages0 {

        @FacesRedirect
    	@RestrictAtPhase({PhaseIdType.RESTORE_VIEW, PhaseIdType.INVOKE_APPLICATION})
        @ViewPattern("/protected/*")
        @AccessDeniedView("/denied.xhtml")
        @LoginView("/login.xhtml")
        @EhrUser
        EHRUSER,
        
        @FacesRedirect
    	@RestrictAtPhase({PhaseIdType.RESTORE_VIEW, PhaseIdType.INVOKE_APPLICATION})
        @ViewPattern("/mobile/*")
        @AccessDeniedView("/denied.xhtml")
        @LoginView("/login.xhtml")
        @EhrUser
        EHRUSER1;
        
    }
}