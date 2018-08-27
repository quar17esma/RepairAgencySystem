package com.quar17esma.controller.action;

import com.quar17esma.controller.action.impl.*;

/**
 * Possible Actions.
 */
public enum ActionEnum {
    LOGIN {
        {
            this.action = new Login();
        }
    },
    LOGOUT {
        {
            this.action = new Logout();
        }
    },
    ADD_USER {
        {
            this.action = new AddUser();
        }
    },
    EDIT_USER {
        {
            this.action = new EditUser();
        }
    },
    EDIT_APPLICATION {
        {
            this.action = new EditApplication();
        }
    },
    ADD_APPLICATION {
        {
            this.action = new AddApplication();
        }
    },
    ACCEPT_APPLICATION {
        {
            this.action = new AcceptApplication();
        }
    },
    DECLINE_APPLICATION {
        {
            this.action = new DeclineApplication();
        }
    },
    COMPLETE_APPLICATION {
        {
            this.action = new CompleteApplication();
        }
    },

    SHOW_ALL_APPLICATIONS {
        {
            this.action = new ShowAllApplications();
        }
    },
    SHOW_ACCEPTED_APPLICATIONS {
        {
            this.action = new ShowAcceptedApplications();
        }
    },
    SHOW_MY_APPLICATIONS {
        {
            this.action = new ShowMyApplications();
        }
    },
    SHOW_FEEDBACKS {
        {
            this.action = new ShowFeedbacks();
        }
    },
    CHANGE_LOCALE {
        {
            this.action = new ChangeLocale();
        }
    };

    Action action;

    public Action getCurrentAction() {
        return action;
    }
}
