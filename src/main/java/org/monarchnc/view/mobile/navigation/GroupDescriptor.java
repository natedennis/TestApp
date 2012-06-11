package org.monarchnc.view.mobile.navigation;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
@Deprecated
public class GroupDescriptor extends BaseDescriptor {
    private static final long serialVersionUID = -3481702232804120885L;
    private List<Descriptor> demos;

    private boolean containsNewDemos() {
        for (Descriptor demo : demos) {
            if (demo.isNewItems()) {
                return true;
            }
        }
        return false;
    }

    public boolean isNewItems() {
        return isNewItem() || containsNewDemos();
    }

    @XmlElementWrapper(name = "demos")
    @XmlElement(name = "demo")
    public List<Descriptor> getDemos() {
        return demos;
    }

    public void setDemos(List<Descriptor> demos) {
        this.demos = demos;
    }
}