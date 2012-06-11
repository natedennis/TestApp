package org.monarchnc.view.mobile;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


@Named("userAgent")
@SessionScoped
public class UserAgentProcessor implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private UAgentInfo uAgentInfo;
 
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String userAgentStr = request.getHeader("user-agent");
        String httpAccept = request.getHeader("Accept");
        uAgentInfo = new UAgentInfo(userAgentStr, httpAccept);
    }
 
    public boolean isPhone() {
        //Detects a whole tier of phones that support similar functionality as the iphone
        return uAgentInfo.detectTierIphone();
    }
 
    public boolean isTablet() {
        // Will detect iPads, Xooms, Blackberry tablets, but not Galaxy - they use a strange user-agent
        return uAgentInfo.detectTierTablet();
    }
 
    public boolean isMobile() {
        return isPhone() || isTablet();
    }
}